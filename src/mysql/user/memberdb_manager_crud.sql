##########################################################
# 2. 관리자 전용 회원관리 기능
##########################################################
-- 창고관리자/총관리자 전용 회원조회: 자신 이외 다른 회원의 정보를 조회할 수 있음

-- 회원 상세조회
-- 현재 관리자 자신의 정보 조회
DELIMITER $$
CREATE PROCEDURE current_manager_info(IN currentID varchar(15))
BEGIN
    SET @loginID = currentID;
    SET @managerInfo = 'SELECT * FROM managers WHERE manager_id = ? and manager_login = true;';

    -- 확인한 회원가입유형에 해당하는 회원 자신의 정보를 일반회원/관리자 테이블에서 찾아 조회한다.
    prepare searchQuery from @managerInfo;
    execute searchQuery using @loginID;

    deallocate prepare searchQuery;
END $$
DELIMITER ;

call current_manager_info('wmscargoman');
call current_manager_info('wmsAdmin');

-- 현재 로그인한 관리자 자신의 정보를 갱신(구현, 단위테스트 완료)
DROP PROCEDURE IF EXISTS manager_update;
DELIMITER $$
CREATE PROCEDURE manager_update(
	IN currentID varchar(15),
    IN newPwd varchar(20),
    IN newName varchar(10),
    IN newPhone varchar(13),
    IN newEmail varchar(30)
)
BEGIN
	SET @loginID = currentID;
    SET @pwd = newPwd;
    SET @name = newName;
    SET @phone = newPhone;
    SET @email = newEmail;
    
    -- 관리자 테이블에서, 현재 로그인 중인 관리자 자신의 정보를 갱신
    -- 관리자 테이블의 갱신이 이루어지면 회원정보 테이블의 정보도 갱신
    SET @updateManager = 'update managers
            set manager_pwd = ?, manager_name = ?, manager_phone = ?, manager_email = ?
            where manager_id = ? and manager_login = true;';
            
	prepare updateQuery from @updateManager;
    execute updateQuery using @pwd, @name, @phone, @email, @loginID;
    
    SET @updateInfo = 'update users 
			set user_pwd = ?, user_name = ?, user_phone = ?, user_email = ? 
            where user_id = ? and user_approval = \'승인완료\';';

    prepare updateQuery from @updateManager;
    execute updateQuery using @pwd, @name, @phone, @email, @loginID;
    
    deallocate prepare updateQuery;
END $$
DELIMITER ;

-- 현재 로그인한 관리자 자신의 회원정보 갱신 테스트
select * from users where user_id = 'wmscargoman';
select * from managers where manager_id = 'wmscargoman';

call manager_update('wmscargoman', 'wms5678', '김철수', '010-4567-8910', 'wmscargoman@sample.com');

select * from managers where manager_id = 'wmscargoman';
select * from users where user_id = 'wmscargoman';

DROP PROCEDURE IF EXISTS manager_delete;
DELIMITER $$
CREATE PROCEDURE manager_delete(IN currentID varchar(15), OUT deleteCount int)
BEGIN
    -- 회원 정보 삭제: 삭제 시 아이디 중복으로 인해 미승인된 건까지 모두 삭제
    SET @loginID = currentID;
    SET @beforeDelete = 0;
    SET @deleteCount = 0;

    select count(*) into @beforeDelete from managers where manager_login is null;
    
    SET @deleteManager = 'update managers set manager_login = null where manager_id = ? and manager_login = true';
    PREPARE deleteQuery FROM @deleteManager;
    EXECUTE deleteQuery USING @loginID;
    
    SET @deleteInfo = 'update from users set user_approval = \'삭제됨\',  where user_id = ? and user_approval = \'승인완료\'';
    PREPARE deleteQuery FROM @deleteInfo;
    EXECUTE deleteQuery USING @loginID;
    
    DEALLOCATE PREPARE deleteQuery;
    
    select count(manager_id) into @deleteCount from managers where manager_id = currentID and manager_login is null;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS other_user_type;
DELIMITER $$
CREATE PROCEDURE other_user_type(IN targetID varchar(15), OUT userType varchar(10))
BEGIN
	select user_type into userType
    from users
    where user_id = targetID and user_approval = '승인완료';
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS search_other_member;
DELIMITER $$
CREATE PROCEDURE search_other_member(IN targetID varchar(15))
BEGIN
	set @targetID = targetID;
    set @searchMember = 'select * from members where member_id = ?;';
    
    prepare selectQuery from @searchMember;
    execute selectQuery using @targetID;
    
    deallocate prepare selectQuery;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS search_other_manager;
DELIMITER $$
CREATE PROCEDURE search_other_manager(IN targetID varchar(15))
BEGIN
	set @targetID = targetID;
    set @searchManager = 'select * from managers where manager_id = ?;';
    
    prepare selectQuery from @searchManager;
    execute selectQuery using @targetID;
    
    deallocate prepare selectQuery;
END $$
DELIMITER ;
commit;

-- 승인된 모든 가입자의 정보 조회(관리자, 회원이 공통으로 보유한 정보만 간략히 출력)
DROP PROCEDURE IF EXISTS search_all_users;
DELIMITER $$
CREATE PROCEDURE search_all_users()
BEGIN
	SET @approval = '승인완료';
	SET @selectAll = 'select * from users where user_approval = ?';
    
    PREPARE selectAllQuery FROM @selectAll;
    EXECUTE selectAllQuery USING @approval;
    
    DEALLOCATE PREPARE selectAllQuery;
END $$
DELIMITER ;

call search_all_users();

DROP PROCEDURE IF EXISTS search_by_role;
DELIMITER $$
CREATE PROCEDURE search_by_role(IN tableName varchar(10))
BEGIN
    SET @searchByRole = concat('select * from ', tableName);

    PREPARE searchByRole FROM @searchByRole;
    EXECUTE searchByRole;

    DEALLOCATE PREPARE searchByRole;
END $$
DELIMITER ;

-- 권한 수정은 아무 권한이 없는 회원을 대상으로 진행하며, 총관리자와 창고관리자 모두 수행할 수 있다.
-- 즉, 회원의 권한을 다시 복구한다.
-- 창고관리자는 일반회원 권한만 부여할 수 있다.
DROP PROCEDURE IF EXISTS update_to_another_role;
DELIMITER $$
CREATE PROCEDURE update_to_another_role(IN targetID varchar(15), IN newRole varchar(10))
BEGIN
    SET @targetID = targetID;
    SET @newRole = newRole;
    SET @updateRole = 'update users set user_type = ? where user_id = ? and user_approval = \'승인완료\' and user_type is null';

    PREPARE updateRole from @updateRole;
    EXECUTE updateRole USING @newRole, @targetID, @previousRole;
    DEALLOCATE PREPARE updateRole;

    IF (newRole = '창고관리자') THEN
        IF EXISTS (select manager_id from managers where manager_id = targetID) THEN
            update managers set manager_position = newRole where manager_id = targetID;
        ELSE
            insert into managers
            select user_id, user_pwd, user_name,
                   user_phone, user_email, false, now(), newRole
                       from users where user_id = targetID and user_approval = '승인완료';
        END IF;
    ELSEIF (newRole = '일반회원') THEN
        IF EXISTS (select member_id from members where member_id = targetID) THEN
            update managers set manager_position = newRole where manager_id = targetID;
        ELSE
            insert into members
            select user_id, user_pwd, user_name,
                   user_phone, user_email, user_company_code,
                   user_address, false, now(), date_add(now(), interval 1 year)
            from users where user_id = targetID and user_approval = '승인완료';
        END IF;
    END IF;
END $$
DELIMITER ;

# 여기서부터 작업 수행 필요
DROP PROCEDURE IF EXISTS delete_role;
DELIMITER $$
CREATE PROCEDURE delete_member_role(IN targetID varchar(15))
BEGIN
    SET @targetID = targetID;
    SET @deleteRole = 'update members set member_login = null where member_id = ? and member_login = false';

    PREPARE deleteRole FROM @deleteRole;
    EXECUTE deleteRole USING @targetID;

    SET @deleteRole = 'update users set user_type = null where user_id = ? and user_approval = \'승인완료\' and user_type = \'일반회원\'';

    PREPARE deleteRole FROM @deleteRole;
    EXECUTE deleteRole USING @targetID;

    DEALLOCATE PREPARE deleteRole;
END $$
DELIMITER ;
