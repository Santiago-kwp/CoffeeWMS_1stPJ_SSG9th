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
    SET @deleteCount = 0;
    
    SET @deleteManager = 'update managers set manager_id = concat(\'del_\', ?), manager_login = false where manager_id = ? and manager_login = true';
    PREPARE deleteQuery FROM @deleteManager;
    EXECUTE deleteQuery USING @loginID, @loginID;
    
    SET @deleteInfo = 'delete from users where user_id = ?';
    PREPARE deleteQuery FROM @deleteInfo;
    EXECUTE deleteQuery USING @loginID;
    
    DEALLOCATE PREPARE deleteQuery;
    
    select count(manager_id) into deleteCount from managers where manager_id = concat('del_', @loginID);
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS cargo_manager_search;
DELIMITER $$
CREATE PROCEDURE cargo_manager_search(IN targetID varchar(15), IN userType varchar(10))
BEGIN
	-- 
	if (userType like '창고관리자') then
		select * from members;
    end if;
END $$
DELIMITER ;