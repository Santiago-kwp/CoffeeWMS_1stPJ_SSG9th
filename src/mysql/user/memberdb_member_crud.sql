use railway;

-- 권한에 따른 회원관리 기능의 허용 범위는 회원관리 유스케이스를 참조할 것

######################################################################
# 1. 일반회원/관리자 공통의 회원관리 기능 - 로그인한 회원 자신에 관한 조회, 수정, 삭제
######################################################################

-- 현재 로그인한 일반회원 자신의 정보만 조회(구현, 단위테스트 완료)
DROP PROCEDURE IF EXISTS current_member_info;
DELIMITER $$
CREATE PROCEDURE current_member_info(IN currentID varchar(15))
BEGIN
	SET @loginID = currentID;
	SET @memberInfo = 'SELECT * FROM members WHERE member_id = ? and member_login = true;';

    -- 확인한 회원가입유형에 해당하는 회원 자신의 정보를 일반회원/관리자 테이블에서 찾아 조회한다.
    prepare searchQuery from @memberInfo;
	execute searchQuery using @loginID;

	deallocate prepare searchQuery;
END $$
DELIMITER ;

call current_member_info('wmsmember');

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
    
    -- 현재 로그인 중인 일반회원의 정보를 갱신
    -- 일반회원 테이블의 정보가 갱신되면 회원정보 테이블의 정보도 갱신
    SET @updateMember = 'update members 
            set member_pwd = ?, member_company_name = ?, member_phone = ?, member_email = ?, member_company_code = ? , member_address = ?
            where member_id = ? and member_login = true;';
	prepare updateQuery from @updateMember;
    execute updateQuery using @pwd, @name, @phone, @email, @companyCode, @address, @loginID;
    
    SET @updateInfo = 'update users set user_pwd = ?, user_name = ?, user_phone = ?, user_email = ?,
            user_company_code = ?, user_address = ? where user_id = ? and user_approval = \'승인완료\';';
    prepare updateQuery from @updateInfo;
    execute updateQuery using @pwd, @name, @phone, @email, @companyCode, @address, @loginID;
    
    deallocate prepare updateQuery;
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


-- 회원탈퇴: 총관리자를 제외한, 현재 로그인 중인 회원이 더 이상 로그인하지 못하게 하는 작업
-- 
-- 회원 정보가 삭제되면 해당 회원이 보유한 권한도 삭제된다.
DROP PROCEDURE IF EXISTS member_delete;
DELIMITER $$
CREATE PROCEDURE member_delete(IN currentID varchar(15), OUT deleteCount int)
BEGIN
    -- 회원 정보 삭제: 삭제 시 해당 아이디로는 더 이상 로그인 불가
    SET @loginID = currentID;
    
    SET @deleteMember = 'update members set member_login = null where member_id = ? and member_login = true';
    PREPARE deleteQuery FROM @deleteMember;
    EXECUTE deleteQuery USING @loginID, @loginID;
    
    SET @deleteInfo = 'update users set user_approval = \'삭제됨\' where user_id = ? and user_approval = \'승인완료\'';
    PREPARE deleteQuery FROM @deleteInfo;
    EXECUTE deleteQuery USING @loginID;
    
    DEALLOCATE PREPARE deleteQuery;
    
    select count(member_id) into deleteCount
    from members
    where member_id = currentID and member_login is null;
END $$
DELIMITER ;

select * from users where user_id = 'member12349';
select * from members where member_id = 'member12349';

call member_delete('member12348');

select * from users where user_id = 'member12348';
select * from members where member_id = 'del_member12348';

select * from users;
select * from members;
select * from managers;