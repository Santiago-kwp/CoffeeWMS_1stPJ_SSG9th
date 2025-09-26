use railway;
-- 회원정보(권한요청) 테이블 생성
-- user_type 속성값이 null이면 회원의 권한이 삭제된 것으로 취급
-- user_approval 속성값에 '삭제됨' 추가
DROP TABLE IF EXISTS users;
CREATE TABLE users (
     user_id	        varchar(15)	NOT NULL,
     user_approval	    varchar(4)  DEFAULT '미승인' NOT NULL,
     user_pwd	        varchar(20)	NOT NULL,
     user_name	        varchar(10)	NOT NULL,
     user_phone	        varchar(13)	NULL,
     user_email	        varchar(30)	NOT NULL,
     user_company_code	char(12)	NULL,
     user_address	    varchar(255)	NULL,
     user_join_date	    date	NOT NULL,
     user_type	        varchar(10)	NULL,

    constraint chk_user_id check (length(user_id) between 8 and 15),
    constraint chk_user_pwd check (length(user_pwd) between 7 and 20),
    constraint chk_user_approval check (user_approval in ('승인완료', '미승인', '삭제됨')),
    constraint chk_user_type check (user_type in ('일반회원', '창고관리자', '총관리자'))
);
-- 회원정보(권한요청) 테이블 기본키 설정
ALTER TABLE users ADD CONSTRAINT PK_USERS PRIMARY KEY (user_id, user_approval);
desc users;

-- 일반회원 테이블 생성
-- member_login 속성값이 null이면 권한이 삭제된 것으로 간주
DROP TABLE IF EXISTS members;
CREATE TABLE members (
   member_id	        varchar(15)	NOT NULL,
   member_pwd	        varchar(20)	NOT NULL,
   member_company_name	varchar(10)	NOT NULL,
   member_phone	        varchar(13)	NULL,
   member_email	        varchar(30)	NOT NULL,
   member_company_code	char(12)	NOT NULL,
   member_address	    varchar(255)	NOT NULL,
   member_login	        boolean	DEFAULT FALSE NULL,
   member_start_date	date	NOT NULL,
   member_expired_date	date	NOT NULL,

    constraint chk_member_company_code check (member_company_code like '___-__-_____')
);
-- 일반회원(권한부여) 테이블 기본키 설정
ALTER TABLE members ADD CONSTRAINT PK_MEMBERS PRIMARY KEY (member_id);
desc members;

-- 관리자 테이블 생성
-- manager_login 속성값이 null이면 권한이 삭제된 것으로 간주
DROP TABLE IF EXISTS managers;
CREATE TABLE managers (
    manager_id	        varchar(15)	NOT NULL,
    manager_pwd	        varchar(20)	NOT NULL,
    manager_name	    varchar(10)	NOT NULL,
    manager_phone	    varchar(13)	NULL,
    manager_email	    varchar(30)	NOT NULL,
    manager_login	    boolean	DEFAULT FALSE NULL,
    manager_hire_date	date	NOT NULL,
    manager_position	varchar(10) DEFAULT '창고관리자' NULL,

    constraint chk_manager_position check (manager_position in ('창고관리자', '총관리자'))
);
-- 관리자(권한부여) 테이블 기본키 설정
ALTER TABLE managers ADD CONSTRAINT PK_MANAGERS PRIMARY KEY (manager_id);
desc managers;