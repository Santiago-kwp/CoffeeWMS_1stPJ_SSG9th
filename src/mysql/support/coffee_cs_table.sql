USE railway;
########################################################################################################################
/*
테이블
    1. 공지사항 테이블
    2. 1:1문의 테이블
    3. FAQ 테이블
프로시져
    1. 공지사항
        1-1. 생성 (총관리자)
        1-2. 조회 (ALL)
        1-3. 수정 (총관리자)
        1-4. 삭제 (총관리자)
    2. 1:1문의
        2-1. 생성 (회원)
        2-2. 조회 (회원_작성자, 총관리자)
        2-3. 수정 (회원_작성자, 총관리자)
            - 회원_작성자에게 작성 내용 수정
            - 총관리자에게 문의상태, 답변날짜 추가, 답변내용 추가
        2-3. 삭제 (회원_작성자, 총관리자)
    3. FAQ
        3-1. 생성 (총관리자)
        3-2. 조회 (회원, 모든 관리자)
        3-3. 수정 (총관리자)
        3-4. 삭제 (총관리자)
 */
########################################################################################################################
-- 테이블 생성
#공지사항
CREATE TABLE notice (
    notice_id INTEGER AUTO_INCREMENT NOT NULL,
    notice_title VARCHAR(50) NOT NULL,
    notice_content TEXT NOT NULL,
    notice_date	DATE NOT NULL,
    notice_fixed BOOLEAN NOT NULL,
    manager_id VARCHAR(15) NOT NULL,
    CONSTRAINT PK_notice PRIMARY KEY (notice_id)
);
#1:1문의
CREATE TABLE inquiry (
    inquiry_id INTEGER AUTO_INCREMENT NOT NULL,
    inquiry_date DATE NOT NULL,
    inquiry_category_id	INTEGER	NOT NULL,
    inquiry_content	TEXT NOT NULL,
    inquiry_status ENUM('PENDING','DONE') NOT NULL DEFAULT 'PENDING',
    reply_date DATE NULL,
    reply_content TEXT NULL,
    member_id VARCHAR(15) NOT NULL,
    manager_id VARCHAR(15) NULL,
    CONSTRAINT PK_inquiry PRIMARY KEY (inquiry_id)
);
#FAQ
CREATE TABLE faq (
    faq_id INTEGER AUTO_INCREMENT NOT NULL,
    faq_category_id	INTEGER	NOT NULL,
    faq_question TEXT NOT NULL,
    faq_reply TEXT NOT NULL ,
    faq_date DATE NOT NULL,
    manager_id VARCHAR(15) NOT NULL,
    CONSTRAINT PK_faq PRIMARY KEY (faq_id)
);
#1:1문의 카테고리
CREATE TABLE inquiry_category (
    inquiry_category_id	INTEGER AUTO_INCREMENT NOT NULL,
    inquiry_category_name VARCHAR(25) NOT NULL UNIQUE ,
    CONSTRAINT PK_inquiry_category PRIMARY KEY (inquiry_category_id)
);
#FAQ 카테고리
CREATE TABLE faq_category (
    faq_category_id	INTEGER AUTO_INCREMENT	NOT NULL,
    faq_category_name VARCHAR(25) NOT NULL UNIQUE ,
    CONSTRAINT PK_faq_category PRIMARY KEY (faq_category_id)
);
#보조키입력
ALTER TABLE inquiry ADD CONSTRAINT FK_inquiry FOREIGN KEY (inquiry_category_id) REFERENCES inquiry_category(inquiry_category_id);
ALTER TABLE faq ADD CONSTRAINT FK_FAQ FOREIGN KEY (faq_category_id) REFERENCES faq_category(faq_category_id);