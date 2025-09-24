use testdb1;
-- 권한에 따른 회원관리 기능의 허용 범위는 회원관리 유스케이스를 참조할 것

######################################################################
# 1. 일반회원/관리자 공통의 회원관리 기능 - 로그인한 회원 자신에 관한 조회, 수정, 삭제
######################################################################

-- 현재 로그인한 일반회원/관리자 자신의 정보만 조회(구현, 단위테스트 완료)
DROP PROCEDURE IF EXISTS current_user_info;
DELIMITER $$
CREATE PROCEDURE current_user_info(IN currentID varchar(15))
BEGIN
	SET @loginID = currentID;
    
    -- 현재 로그인 중인 회원 자신의 회원유형을 확인.
    SELECT user_type INTO @userType FROM users WHERE user_id = currentID and user_approval = '승인완료';
    
    -- 확인한 회원가입유형에 해당하는 회원 자신의 정보를 일반회원/관리자 테이블에서 찾아 조회한다.
    IF (@userType = '일반회원') THEN
		SELECT * FROM members WHERE member_id = currentID and member_login = true;
    ELSEIF (@userType like '%관리자') THEN
		SELECT * FROM managers WHERE manager_id = currentID and manager_login = true;
    END IF;
END $$
DELIMITER ;

call current_user_info('wmsmember');
call current_user_info('wmscargoman');
call current_user_info('wmsAdmin');

-- 현재 로그인한 일반회원 자신의 정보를 갱신(구현, 단위테스트 완료)
DROP PROCEDURE IF EXISTS member_update;
DELIMITER $$
CREATE PROCEDURE member_update(
	IN currentID varchar(15),
    IN newPwd varchar(20),
    IN newName varchar(10),
    IN newPhone varchar(13),
    IN newEmail varchar(30),
    IN newCompanyCode varchar(12),
    IN newAddress varchar(255)
)
BEGIN
	SET @loginID = currentID;
    SET @pwd = newPwd;
    SET @name = newName;
    SET @phone = newPhone;
    SET @email = newEmail;
    SET @companyCode = newCompanyCode;
    SET @address = newAddress;
    
    -- 현재 로그인 중인 일반회원 자신의 정보를 갱신
    -- 일반회원 테이블의 정보가 갱신되면 트리거가 작동하여 회원정보 테이블의 정보도 갱신
    SET @updateMember = 'update members 
            set member_pwd = ?, member_company_name = ?, member_phone = ?, member_email = ?, member_company_code = ? , member_address = ?
            where member_id = ? and member_login = true;';
	prepare updateQuery from @updateMember;
    execute updateQuery using @pwd, @name, @phone, @email, @companyCode, @address, @loginID;
    
    deallocate prepare updateQuery;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS member_update_trigger;
DELIMITER $$
CREATE TRIGGER member_update_trigger
	AFTER UPDATE ON members
    FOR EACH ROW
BEGIN
	-- 현재 로그인 중인 일반회원의 정보가 갱신되면, 회원정보 테이블에도 해당 내용을 반영하는 트리거(구현, 테스트 완료)
	-- (로그인 중인 회원은 이미 승인 완료된 회원이다.)
    if (old.member_login = new.member_login and new.member_login = true) then
		update users 
		set user_pwd = new.member_pwd, user_name = new.member_company_name, 
            user_phone = new.member_phone, user_email = new.member_email,
            user_company_code = new.member_company_code, user_address = new.member_address
		where user_id = new.member_id and user_approval = '승인완료';
    end if;
END $$
DELIMITER ;

-- 현재 로그인한 일반회원 정보 변경 테스트 진행
-- 변경 이전 회원정보, 일반회원 테이블 확인
select * from members where member_id = 'wmsmember';
select * from users where user_id = 'wmsmember';

call member_update('wmsmember', 'wms1234', '스타벅스', '010-5678-1234', 'wmsmember@sample.com', '000-00-00000', '서울시 관악구');

-- 변경 이후 회원정보, 일반회원 테이블 확인
select * from members where member_id = 'wmsmember';
select * from users where user_id = 'wmsmember';


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
    -- 관리자 테이블의 갱신이 이루어지면 트리거가 작동하여 회원정보 테이블의 정보도 갱신
    SET @updateManager = 'update managers
            set manager_pwd = ?, manager_name = ?, manager_phone = ?, manager_email = ?
            where manager_id = ? and manager_login = true;';
	prepare updateQuery from @updateManager;
    execute updateQuery using @pwd, @name, @phone, @email, @loginID;
    
    deallocate prepare updateQuery;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS manager_update_trigger;
DELIMITER $$
CREATE TRIGGER manager_update_trigger
	AFTER UPDATE ON managers
    FOR EACH ROW
BEGIN
	-- 현재 로그인 중인 관리자의 정보가 갱신되면, 회원정보 테이블에도 해당 내용을 반영한다.
	-- (로그인 중인 관리자는 이미 승인완료된 회원이다.)
	IF (old.manager_login = new.manager_login and new.manager_login = true) THEN
		update users 
		set user_pwd = new.manager_pwd, user_name = new.manager_name, 
			user_phone = new.manager_phone, user_email = new.manager_email
		where user_id = new.manager_id and user_approval = '승인완료';
    END IF;
END $$
DELIMITER ;

-- 현재 로그인한 관리자 자신의 회원정보 갱신 테스트
select * from users where user_id = 'wmscargoman';
select * from managers where manager_id = 'wmscargoman';

call manager_update('wmscargoman', 'wms5678', '김철수', '010-4567-8910', 'wmscargoman@sample.com');

select * from managers where manager_id = 'wmscargoman';
select * from users where user_id = 'wmscargoman';


-- 총관리자를 제외한, 현재 로그인 중인 회원 자신을 삭제(회원탈퇴)
DROP PROCEDURE IF EXISTS user_delete;
DELIMITER $$
CREATE PROCEDURE user_delete(IN currentID varchar(15))
BEGIN
    -- 회원 정보 삭제: 삭제 시 아이디 중복으로 인해 미승인된 건까지 모두 삭제
    SET @loginID = currentID;
    
    SET @deleteMember = 'delete from users where user_id = ?';
    PREPARE deleteQuery FROM @deleteMember;
    EXECUTE deleteQuery USING @loginID;
    
    DEALLOCATE PREPARE deleteQuery;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS delete_user_info;
DELIMITER $$
CREATE TRIGGER delete_user_info
	AFTER DELETE ON users
    FOR EACH ROW
BEGIN
	-- 승인된 회원정보가 삭제되면, 해당하는 회원이 보유했던 권한도 함께 삭제
    -- 현재 로그인한 회원이 총관리자일 때는 회원탈퇴 불가
    IF (OLD.user_type = '일반회원') THEN
		delete from members where member_id = old.user_id;
    ELSEIF (OLD.user_type = '창고관리자') THEN
		delete from managers where manager_id = old.user_id;
    END IF;
END $$
DELIMITER ;

##########################################################
# 2. 관리자 전용 회원관리 기능
##########################################################
-- 창고관리자/총관리자 전용 회원조회: 다른 회원 정보도 조회 가능, 권한마다 조회 가능 범위는 상이
DROP PROCEDURE IF EXISTS manager_search;
DELIMITER $$
CREATE PROCEDURE manager_search(IN currentID varchar(15), IN userType varchar(10))
BEGIN
	
END $$
DELIMITER ;