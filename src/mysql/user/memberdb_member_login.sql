use railway;
# use testdb1;

#########################################################
# 1. 로그인 메뉴의 주요 기능 구현
#########################################################
# 1-1. 로그인 프로시저 작성
DROP PROCEDURE IF EXISTS get_user_type;
DELIMITER $$
CREATE PROCEDURE get_user_type(IN login_id varchar(15), IN login_pwd varchar(20), OUT userType varchar(10))
BEGIN
    select user_type into userType
    from users
    where user_id = login_id and user_pwd = login_pwd and user_approval = '승인완료';
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS login_member;
DELIMITER $$
CREATE PROCEDURE login_member(IN login_id varchar(15), IN login_pwd varchar(20), IN userType varchar(10))
BEGIN
	declare found_count int;			-- 찾은 회원 수
    declare already_login boolean;		-- 이미 로그인한 상태인지 확인
    
    -- 입력한 아이디와 비밀번호와 일치하는 회원의 수를 구한다.
    select count(*) into found_count
    from users
    where user_id = login_id and user_pwd = login_pwd and user_approval = '승인완료';
    
    if (userType = '일반회원') then
		-- 현재 회원의 로그인 여부를 확인한다.
        select member_login into already_login from members where member_id = login_id;
        
        -- 로그인한 상태가 아니면 로그인한다.
        if (found_count = 1 and already_login = false) then
			update members set member_login = true where member_id = login_id;
			select * from members where member_id = login_id;
        end if;
	end if;
    commit;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS login_manager;
DELIMITER $$
CREATE PROCEDURE login_manager(IN login_id varchar(15), IN login_pwd varchar(20), IN userType varchar(10))
BEGIN
    declare found_count int;			-- 찾은 회원 수
    declare already_login boolean;		-- 이미 로그인한 상태인지 확인

    -- 입력한 아이디와 비밀번호와 일치하는 회원의 수, 회원유형을 구한다.
    select count(*) into found_count
    from users
    where user_id = login_id and user_pwd = login_pwd and user_approval = '승인완료';

    if (userType like '%관리자') then
        -- 현재 회원의 로그인 여부를 확인한다.
        select manager_login into already_login from managers where manager_id = login_id;

        -- 로그인한 상태가 아니면 로그인한다.
        if (found_count = 1 and already_login = false) then
            update managers set manager_login = true where manager_id = login_id;
            select * from managers where manager_id = login_id;
        end if;
    end if;
    commit;
END $$
DELIMITER ;

call login_member('wmsmember', 'wms123456', @userType);
call login_manager('wmsAdmin', 'admin1234', @userType);
select @userType;
call login_manager('wmscargoman', 'wms123456', @userType);

#####################################################
# 1-2. 회원등록(회원가입) 프로시저 & 트리거 작성

DROP PROCEDURE IF EXISTS register;
DELIMITER $$
CREATE PROCEDURE register(
    IN id varchar(15),
    IN pwd varchar(20),
    IN name varchar(10),
    IN phone varchar(13),
    IN email varchar(30),
    IN company_code char(12),
    IN address varchar(255),
    IN register_type varchar(10)
)
BEGIN
    DECLARE approval varchar(10);

    -- 중복된 아이디가 있는지 확인
    IF EXISTS(select users.user_id from users where user_approval = '승인완료' and (user_id = id or user_type = '총관리자')) THEN
        SET approval = '미승인';
    ELSE
        SET approval = '승인완료';
    END IF;

    -- 회원정보 추가
    insert into users
    values(id, approval, pwd, name, phone,
           email, company_code, address, now(), register_type)
    on duplicate key update
                         user_pwd = pwd, user_name = name, user_phone = phone, user_email = email,
                         user_company_code = company_code, user_address = address, user_join_date = now(), user_type = register_type;
END $$
DELIMITER ;

-- 회원가입용 트리거 추가: 새로운 회원정보 추가 시 자동으로 일반회원, 관리자 권한 중 하나를 부여
DROP TRIGGER IF EXISTS authorize;
DELIMITER $$
CREATE TRIGGER authorize
	AFTER INSERT ON users FOR EACH ROW
BEGIN
	IF (new.user_approval = '승인완료' and new.user_type = '일반회원') then
		insert into members
		values(
			new.user_id, new.user_pwd, new.user_name, new.user_phone, 
			new.user_email, new.user_company_code, new.user_address,
			false, now(), date_add(now(), interval 1 year))
		on duplicate key update
		    member_pwd = NEW.user_pwd, member_company_name = NEW.user_name, member_phone = NEW.user_phone,
		    member_email = new.user_email, member_company_code = NEW.user_email, member_address = NEW.user_address,
		    member_login = false, member_start_date = now(), member_expired_date = date_add(now(), interval 1 year);
	-- 가입승인된 회원의 가입유형이 창고관리자 또는 총관리자면 관리자 권한을 부여
	ELSEIF (new.user_approval = '승인완료' and new.user_type like '%관리자') then
		insert into managers
		values(
			new.user_id, new.user_pwd, new.user_name, new.user_phone, 
			new.user_email, false, now(), new.user_type
		)
		on duplicate key update
             manager_pwd = NEW.user_pwd, manager_name = NEW.user_name, manager_phone = NEW.user_phone,
             manager_email = new.user_email, manager_login = false, manager_hire_date = now(), manager_position = NEW.user_type;
	END IF;
END $$
DELIMITER ;
commit;

-- 회원 등록 테스트
call register('wmsmember', 'wms123456', '스타벅스', null, 'wmsmember@test.com', '000-00-00000', '서울시 강남구', '일반회원');
select * from users;
select * from members;

call register('wmsAdmin', 'admin1234', '홍길동', '010-1234-5678', 'wmsAdmin@test.com', null, '서울시 강남구', '총관리자');
select * from users;
select * from managers;

call register('wmsadmin', 'admin1234', '홍길동', '010-1234-5678', 'wmsAdmin@test.com', null, '서울시 강남구', '총관리자');
select * from users;
select * from managers;


call register('wmscargoman', 'wms123456', '김철수', null, 'wmscargoman@test.com', null, '서울시 강남구', '창고관리자');
select * from users;
select * from managers;

delete from users;
delete from members;
delete from managers;

############################################################
# 1-3. 아이디 찾기

DROP PROCEDURE IF EXISTS find_userID;
DELIMITER $$
CREATE PROCEDURE find_userID(IN email varchar(30), OUT found_ID varchar(15))
BEGIN
    declare userType varchar(10);
    
    select user_type into userType from users where user_email = email and user_approval = '승인완료';
    
    if (userType like '%관리자') then
		select manager_id into found_ID from managers where manager_email = email;
	elseif (userType = '일반회원') then
		select member_id into found_ID from members where member_email = email;
    end if;
END $$
DELIMITER ;

-- 아이디 찾기 테스트
call find_userID('wmsAdmin@test.com', @found_ID, @result);
select @found_ID, @result;
call find_userID('wmsmember@test.com', @found_ID, @result);
select @found_ID, @result;
call find_userID('notmember', @found_ID, @result);
select @found_ID, @result;

##############################################################
# 1-4 비밀번호 찾기

-- 비밀번호를 변경할 아이디가 존재하는지 확인
DROP PROCEDURE IF EXISTS has_userID;
DELIMITER $$
CREATE PROCEDURE has_userID(IN targetID varchar(15), OUT isExist boolean)
BEGIN
	declare member_count int default 0;
    declare manager_count int default 0;
    
    select count(*) into member_count 
    from users 
    where user_approval = '승인완료' and user_id = (
		select member_id
        from members
        where member_id = targetID
    );
    
    select count(*) into manager_count
    from users
    where user_approval = '승인완료' and user_id = (
		select manager_id
        from managers
        where manager_id = targetID
    );
    
    if (member_count + manager_count = 1) then
		set isExist = true;
    else
		set isExist = false;
    end if;
END $$
DELIMITER ;

call has_userID('wmsAdmin', @result);
select @result;

-- 비밀번호 변경: users 테이블의 비밀번호 변경
DROP PROCEDURE IF EXISTS update_pwd;
DELIMITER $$
CREATE PROCEDURE update_pwd(IN targetID varchar(15), IN newPwd varchar(20), OUT affected INT)
BEGIN
	set @targetID = targetID;
    set @newPwd = newPwd;
    
    set @updatePwd = 'update users set user_pwd = ? where user_id = ? and user_approval = \'승인완료\'';
    prepare updateQuery from @updatePwd;
    execute updateQuery using @newPwd, @targetID;
    
    deallocate prepare updateQuery;

	SELECT COUNT(user_id) INTO affected FROM users WHERE user_id = targetID AND user_pwd = newPwd;
    commit;
END $$
DELIMITER ;

-- 변경된 비밀번호를 members, managers 테이블에도 적용
DROP TRIGGER IF EXISTS update_pwd_trigger;
DELIMITER $$
CREATE TRIGGER update_pwd_trigger
	AFTER UPDATE ON users
    FOR EACH ROW
BEGIN
	if (old.user_pwd != new.user_pwd) then
		if (new.user_type = '%관리자') then
			update managers set manager_pwd = new.user_pwd where manager_id = new.user_id;
        else 
			update members set member_pwd = new.user_pwd where member_id = new.user_id;
        end if;
    end if;
END $$
DELIMITER ;

select * from users where user_id = 'wmsmember';

call update_pwd('wmsmember', 'wms1234');
select * from users where user_id = 'wmsmember';
select * from members where member_id = 'wmsmember';

#########################################
# 1-5. 로그아웃 기능 구현

DROP PROCEDURE IF EXISTS logout;
DELIMITER $$
CREATE PROCEDURE logout(IN logout_id varchar(15), OUT message varchar(50))
BEGIN
	declare found_count int;			-- 찾은 회원 수
    declare register_type varchar(10);	-- 회원가입유형
    declare already_login boolean;		-- 이미 로그인한 상태인지 확인
    
    -- 입력한 아이디와 비밀번호와 일치하는 회원의 수, 회원유형을 구한다.
    select user_type, count(*) into register_type, found_count
    from users
    where user_id = logout_id and user_approval = '승인완료'
    group by user_type;
    
    if (register_type = '일반회원') then
		-- 현재 회원의 로그인 여부를 확인한다.
        select member_login into already_login from members where member_id = logout_id;
        
        -- 로그인한 상태면 로그아웃한다.
        if (found_count = 1 and already_login = true) then
			update members set member_login = false where member_id = logout_id;
            set message = '로그아웃 완료';
        end if;
	elseif (register_type like '%관리자') then
		-- 현재 회원의 로그인 여부를 확인한다.
		select manager_login into already_login from managers where manager_id = logout_id;
        
        -- 로그인한 상태면 로그아웃한다.
		if (found_count = 1 and already_login = true) then
			update managers set manager_login = false where manager_id = logout_id;
            set message = '로그아웃 완료';
        end if;
	end if;
    commit;
END $$
DELIMITER ;

call login_manager('wmscargoman', 'wms123456', '창고관리자');
call logout('manager2', @result);
call logout('wmsAdmin', @result);
call logout('member12350', @result);
select @result;
commit;