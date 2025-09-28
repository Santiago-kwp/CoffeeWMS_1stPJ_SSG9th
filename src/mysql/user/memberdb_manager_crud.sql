use railway;
# use testdb1;
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
    -- 회원 정보 삭제: 삭제 시 해당 아이디로는 더 이상 로그인 불가
    SET @loginID = currentID;

    SET @deleteMember = 'update managers set manager_login = null where manager_id = ? and manager_login = true';
    PREPARE deleteQuery FROM @deleteMember;
    EXECUTE deleteQuery USING @loginID;

    SET @deleteInfo = 'update users set user_approval = \'삭제됨\', user_type = null where user_id = ? and user_approval = \'승인완료\'';
    PREPARE deleteQuery FROM @deleteInfo;
    EXECUTE deleteQuery USING @loginID;

    DEALLOCATE PREPARE deleteQuery;

    select count(manager_id) into deleteCount
    from managers
    where manager_id = currentID and manager_login is null;
    commit;
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

# 회원 권한 복구(완료)
-- 권한 복구는 아무 권한이 없는 회원을 대상으로 진행하며, 총관리자와 창고관리자 모두 수행할 수 있다.
-- 단, 창고관리자는 일반회원 권한만 부여할 수 있다.
DROP PROCEDURE IF EXISTS restore_role;
DELIMITER $$
CREATE PROCEDURE restore_role(IN targetID varchar(15), IN newRole varchar(10), OUT updateCount INT)
BEGIN
    SET @targetID = targetID;
    SET @newRole = newRole;
    SET @updateRole = 'update users set user_type = ? where user_id = ? and user_approval = \'승인완료\' and user_type is null';
    SET updateCount = 0;

    PREPARE updateRole from @updateRole;
    EXECUTE updateRole USING @newRole, @targetID;
    DEALLOCATE PREPARE updateRole;

    IF (newRole = '일반회원') THEN
        select count(member_id) into updateCount
        from members
        where member_id = (
            select user_id from users
            where user_id = targetID and user_approval = '승인완료' and user_type = newRole
        );
    ELSEIF (newRole = '창고관리자') THEN
        select count(manager_id) into updateCount
        from managers
        where manager_id = (
            select user_id from users
            where user_id = targetID and user_approval = '승인완료' and user_type = newRole
        );
    END IF;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS update_to_member_trigger;
DELIMITER $$
CREATE TRIGGER update_to_member_trigger
    AFTER UPDATE ON users
    FOR EACH ROW
BEGIN
    IF (OLD.user_type is null and NEW.user_type = '일반회원') THEN
        IF EXISTS(select members.member_id from members where members.member_id = NEW.user_id) THEN
            update members set member_login = false where member_id = NEW.user_id;
        END IF;
    END IF;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS update_to_manager_trigger;
DELIMITER $$
CREATE TRIGGER update_to_manager_trigger
    AFTER UPDATE ON users
    FOR EACH ROW
    FOLLOWS update_to_member_trigger
BEGIN
    IF (OLD.user_type is null and NEW.user_type = '창고관리자') THEN
        IF EXISTS(select managers.manager_id from managers where managers.manager_id = NEW.user_id) THEN
            update managers set manager_login = false, manager_position = NEW.user_type where manager_id = NEW.user_id;
        END IF;
    END IF;
END $$
DELIMITER ;

-- 회원 승인: 같은 아이디로 승인완료된 계정이 없으면, 미승인된 아이디를 승인한다.
DROP PROCEDURE IF EXISTS not_approved_user_type;
DELIMITER $$
CREATE PROCEDURE not_approved_user_type(IN targetID varchar(15), OUT userRole varchar(10))
BEGIN
    IF EXISTS(select user_id from users where user_id = targetID and user_approval = '미승인') THEN
        select user_type into userRole
        from users
        where user_id = targetID and user_approval = '미승인';
    END IF;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS approve_user;
DELIMITER $$
CREATE PROCEDURE approve_user(IN targetID varchar(15), OUT affected INT)
BEGIN
   IF NOT EXISTS(select user_id from users
                 where user_id = targetID and user_approval = '승인완료') THEN
       IF EXISTS(select user_id from users where user_id = targetID and user_approval = '미승인') THEN
           update users set user_approval = '승인완료' where user_id = targetID and user_approval = '미승인';
           SET affected = 1;
       ELSE
           SET affected = 0;
       END IF;
   ELSE
       SET affected = 0;
   END IF;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS grant_role_trigger;
DELIMITER $$
CREATE TRIGGER grant_role_trigger
    AFTER UPDATE ON users FOR EACH ROW
BEGIN
    IF (old.user_approval = '미승인' and new.user_approval = '승인완료') then
        IF (new.user_type = '일반회원') THEN
            insert into members
            values(
                      new.user_id, new.user_pwd, new.user_name, new.user_phone,
                      new.user_email, new.user_company_code, new.user_address,
                      false, now(), date_add(now(), interval 1 year)
            );
-- 가입승인된 회원의 가입유형이 창고관리자 또는 총관리자면 관리자 권한을 부여
        ELSEIF (new.user_type like '%관리자') then
            insert into managers
            values(
                      new.user_id, new.user_pwd, new.user_name, new.user_phone,
                      new.user_email, false, now(), new.user_type
            );
        END IF;
    END IF;
END $$
DELIMITER ;
commit;

-- 회원탈퇴 철회: 삭제된 회원을 승인완료 상태로 복구(단, 권한은 복구되지 않음)
DROP PROCEDURE IF EXISTS restore_user;
DELIMITER $$
CREATE PROCEDURE restore_user(IN targetID varchar(15), OUT affected INT)
BEGIN
    IF NOT EXISTS(select user_id
                  from users
                  where user_id = targetID and user_approval = '승인완료') THEN
        IF EXISTS(select user_id from users where user_id = targetID and user_approval = '삭제됨') THEN
            update users set user_approval = '승인완료' where user_id = targetID and user_approval = '삭제됨';
            SET affected = 1;
        ELSE
            SET affected = 0;
        END IF;
    ELSE
        SET affected = 0;
    END IF;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS delete_role;
DELIMITER $$
CREATE PROCEDURE delete_role(IN targetID varchar(15), IN targetType varchar(10), OUT affected INT)
BEGIN
    IF (targetType = '일반회원') THEN
        update members set member_login = null where member_id = targetID;
        update users set user_type = null where user_id = targetID;
        SET affected = 1;
    ELSEIF (targetType = '창고관리자') THEN
        update managers set manager_login = null where manager_id = targetID and manager_position = targetType;
        update users set user_type = null where user_id = targetID;
        SET affected = 1;
    ELSE
        SET affected = 0;
    END IF;
END $$
DELIMITER ;
