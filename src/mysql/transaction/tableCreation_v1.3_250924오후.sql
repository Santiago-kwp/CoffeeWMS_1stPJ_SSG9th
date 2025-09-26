-- drop database testDB1;
create database testDB1;
use testdb1;

#################################
/* 테이블 생성 순서
-- 외래키가 없거나, 본 테이블 내 속성을 셀프 조인한 테이블
1. users, members, managers, cargoes, location_places, coffees, inquiry_category, faq_category 테이블

-- 1번의 테이블의 기본키를 외래키로 한 개만 갖는 테이블
2. notice

-- 1, 2번의 두 테이블의 기본키를 외래키로 갖거나 자신의 기본키와 같이 복합키로 가지는 테이블
3. faq, inbound_request, outbound_request, cargo_management, locations, inventory

-- 3번 테이블의 기본키를 외래키로 가지는 테이블
4. inbound_request_items, outbound_request_items, due_diligence, inquiry
*/
#################################

-- users 테이블
DROP TABLE IF EXISTS users;
CREATE TABLE Users (
     user_id	        varchar(15)	CHECK (length(user_id) between 8 and 15) NOT NULL,
     user_approval	    varchar(4) CHECK (user_approval in ('승인완료', '미승인')) DEFAULT '미승인' NOT NULL,
     user_pwd	        varchar(20)	CHECK (length(user_pwd) between 7 and 20) NOT NULL,
     user_name	        varchar(10)	NOT NULL,
     user_phone	        varchar(13)	NULL,
     user_email	        varchar(30)	NOT NULL,
     user_company_code	char(12)	NULL,
     user_address	    varchar(255)	NULL,
     user_join_date	    date	NOT NULL,
     user_type	        varchar(10)	CHECK (user_type in ('일반회원', '창고관리자', '총관리자')) NOT NULL
);
-- 회원정보(권한요청) 테이블 기본키 설정
ALTER TABLE Users ADD CONSTRAINT PK_USERS PRIMARY KEY (user_id, user_approval);


-- members 테이블
DROP TABLE IF EXISTS members;
CREATE TABLE Members (
   member_id	        varchar(15)	NOT NULL,
   member_pwd	        varchar(20)	NOT NULL,
   member_company_name	varchar(10)	NOT NULL,
   member_phone	        varchar(13)	NULL,
   member_email	        varchar(30)	NOT NULL,
   member_company_code	char(12)	NOT NULL,
   member_address	    varchar(255)	NOT NULL,
   member_login	        boolean	DEFAULT FALSE NOT NULL,
   member_start_date	date	NOT NULL,
   member_expired_date	date	NOT NULL
);
-- 일반회원(권한부여) 테이블 기본키 설정
ALTER TABLE Members ADD CONSTRAINT PK_MEMBERS PRIMARY KEY (member_id);

-- managers 테이블
DROP TABLE IF EXISTS managers;
CREATE TABLE managers (
    manager_id	        varchar(15)	NOT NULL,
    manager_pwd	        varchar(20)	NOT NULL,
    manager_name	    varchar(10)	NOT NULL,
    manager_phone	    varchar(13)	NULL,
    manager_email	    varchar(30)	NOT NULL,
    manager_login	    boolean	DEFAULT FALSE NOT NULL,
    manager_hire_date	date	NOT NULL,
    manager_position	varchar(10) CHECK (manager_position in ('창고관리자', '총관리자')) NOT NULL
);
-- 관리자(권한부여) 테이블 기본키 설정
ALTER TABLE managers ADD CONSTRAINT PK_MANAGERS PRIMARY KEY (manager_id);

-- cargoes
DROP TABLE IF EXISTS cargoes;
CREATE TABLE cargoes
(
    cargo_id         integer   Auto_Increment Primary Key ,
	cargo_code		 varchar(3)  Not Null,
    cargo_name       varchar(30) NOT NULL,
    cargo_address    varchar(30) NOT NULL,
    cargo_grade      varchar(30) NOT NULL,
    cargo_field      integer     NOT NULL,
    cargo_total_capa integer     NOT NULL,
    cargo_use_capa   double      NULL
);


-- location places
DROP TABLE IF EXISTS location_places;
CREATE TABLE location_places
(
    location_place_id char(12) NOT NULL,
    zone_id           char(12) NOT NULL,
    zone_name         char(12) NOT NULL,
    rack_id           char(12) NOT NULL,
    rack_name         char(12) NOT NULL,
    cell_id           char(12) NOT NULL,
    cell_name         char(12) NOT NULL
);
ALTER TABLE location_places
    ADD CONSTRAINT PK_LOCATION_PLACES PRIMARY KEY (location_place_id);

-- coffees
DROP TABLE IF EXISTS coffees;
CREATE TABLE coffees (
	coffee_id	char(12)	NOT NULL,
	coffee_name	varchar(20)	NOT NULL,
	coffee_origin	char(3)	NOT NULL,
	decaf	char(1)	NOT NULL,
	coffee_roasted char(2) NOT NULL,
	price	integer	NOT NULL,
	coffee_grade	varchar(15)	NOT NULL,
	coffee_type	varchar(15)	NOT NULL
);

ALTER TABLE coffees ADD CONSTRAINT PK_COFFEES PRIMARY KEY (
	coffee_id
);



-- inquiry category
DROP TABLE IF EXISTS inquiry_category;
CREATE TABLE inquiry_category
(
    inquiry_category_id   INTEGER AUTO_INCREMENT NOT NULL,
    inquiry_category_name VARCHAR(25)            NOT NULL UNIQUE,
    CONSTRAINT PK_inquiry_category PRIMARY KEY (inquiry_category_id)
);

-- faq category
DROP TABLE IF EXISTS faq_category;
CREATE TABLE faq_category
(
    faq_category_id   INTEGER AUTO_INCREMENT NOT NULL,
    faq_category_name VARCHAR(25)            NOT NULL UNIQUE,
    CONSTRAINT PK_faq_category PRIMARY KEY (faq_category_id)
);

-- notice
DROP TABLE IF EXISTS notice;
CREATE TABLE notice
(
    notice_id      INTEGER AUTO_INCREMENT NOT NULL,
    notice_title   VARCHAR(50)            NOT NULL,
    notice_content TEXT                   NOT NULL,
    notice_date    DATE                   NOT NULL,
    notice_fixed   BOOLEAN                NOT NULL,
    manager_id     VARCHAR(15)            NOT NULL,
    CONSTRAINT PK_notice PRIMARY KEY (notice_id)
);
ALTER TABLE notice
    ADD CONSTRAINT FK_managers_TO_notice_1 FOREIGN KEY (manager_id) REFERENCES managers (manager_id);

-- faq
DROP TABLE IF EXISTS faq;
CREATE TABLE faq
(
    faq_id          INTEGER AUTO_INCREMENT NOT NULL,
    faq_category_id INTEGER                NOT NULL,
    faq_question    TEXT                   NOT NULL,
    faq_reply       TEXT                   NOT NULL,
    faq_date        DATE                   NOT NULL,
    manager_id      VARCHAR(15)            NOT NULL,
    CONSTRAINT PK_faq PRIMARY KEY (faq_id)
);
ALTER TABLE faq
    ADD CONSTRAINT FK_faq_category_TO_faq_1 FOREIGN KEY (faq_category_id) REFERENCES faq_category (faq_category_id);
ALTER TABLE faq
    ADD CONSTRAINT FK_managers_TO_faq_1 FOREIGN KEY (manager_id) REFERENCES managers (manager_id);

-- inbound_request
DROP TABLE IF EXISTS inbound_request;
CREATE TABLE inbound_request
(
    inbound_request_id       char(12)    NOT NULL,
    member_id                varchar(15) NOT NULL,
    manager_id               varchar(15) NOT NULL,
    inbound_request_approved boolean     NULL,
    inbound_receipt          text        NULL
);
ALTER TABLE inbound_request
    ADD CONSTRAINT PK_INBOUND_REQUEST PRIMARY KEY (inbound_request_id, member_id, manager_id);
ALTER TABLE inbound_request
    ADD CONSTRAINT FK_members_TO_inbound_request_1 FOREIGN KEY (member_id) REFERENCES members (member_id);
ALTER TABLE inbound_request
    ADD CONSTRAINT FK_managers_TO_inbound_request_1 FOREIGN KEY (manager_id) REFERENCES managers (manager_id);

-- outbound_request
DROP TABLE IF EXISTS outbound_request;
CREATE TABLE outbound_request
(
    outbound_request_id       char(12)    NOT NULL,
    member_id                 varchar(15) NOT NULL,
    manager_id                varchar(15) NOT NULL,
    outbound_request_approved boolean     NULL,
    outbound_order            text        NULL
);
ALTER TABLE outbound_request
    ADD CONSTRAINT PK_OUTBOUND_REQUEST PRIMARY KEY (outbound_request_id, member_id, manager_id);
ALTER TABLE outbound_request
    ADD CONSTRAINT FK_members_TO_outbound_request_1 FOREIGN KEY (member_id) REFERENCES members (member_id);
ALTER TABLE outbound_request
    ADD CONSTRAINT FK_managers_TO_outbound_request_1 FOREIGN KEY (manager_id) REFERENCES managers (manager_id);

-- cargo management
DROP TABLE IF EXISTS cargo_management;
CREATE TABLE cargo_management
(
    manager_id varchar(15) NOT NULL,
    cargo_id   int Not Null
);
ALTER TABLE cargo_management
    ADD CONSTRAINT PK_CARGO_MANAGEMENT PRIMARY KEY (manager_id, cargo_id);
ALTER TABLE cargo_management
    ADD CONSTRAINT FK_managers_TO_cargo_management_1 FOREIGN KEY (manager_id) REFERENCES managers (manager_id);
ALTER TABLE cargo_management
    ADD CONSTRAINT FK_cargoes_TO_cargo_management_1 FOREIGN KEY (cargo_id) REFERENCES cargoes (cargo_id);

-- locations
DROP TABLE IF EXISTS locations;
CREATE TABLE locations
(
    location_id       char(12) NOT NULL,
    cargo_id          int NOT NULL,
    location_place_id char(12) NOT NULL
);
ALTER TABLE locations
    ADD CONSTRAINT PK_LOCATIONS PRIMARY KEY (location_id);
ALTER TABLE locations
    ADD CONSTRAINT FK_cargoes_TO_locations_1 FOREIGN KEY (cargo_id) REFERENCES cargoes (cargo_id);
ALTER TABLE locations
    ADD CONSTRAINT FK_location_places_TO_locations_1 FOREIGN KEY (location_place_id) REFERENCES location_places (location_place_id);

-- inventory
DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory
(
    inventory_id       char(12) NOT NULL,
    coffee_id          char(12) NOT NULL,
    location_place_id  char(12) NOT NULL,
    inventory_quantity integer  NOT NULL
);
ALTER TABLE inventory
    ADD CONSTRAINT PK_INVENTORY PRIMARY KEY (inventory_id);
ALTER TABLE inventory
    ADD CONSTRAINT FK_coffees_TO_inventory_1 FOREIGN KEY (coffee_id) REFERENCES coffees (coffee_id);
ALTER TABLE inventory
    ADD CONSTRAINT FK_location_places_TO_inventory_1 FOREIGN KEY (location_place_id) REFERENCES location_places (location_place_id);


-- inbound_request_items
DROP TABLE IF EXISTS inbound_request_items;
CREATE TABLE inbound_request_items
(
    inbound_request_item_id  bigint Auto_Increment NOT NULL,
    inbound_request_id       char(12) NOT NULL,
    member_id                varchar(15) NOT NULL,
    manager_id               varchar(15) NOT NULL,
    coffee_id                char(12)    NOT NULL,
    inbound_request_quantity integer     NULL,
    inbound_request_date     date        NULL,
	CONSTRAINT PK_INBOUND_REQUEST_ITEMS PRIMARY KEY (inbound_request_item_id)
);

ALTER TABLE inbound_request_items
    ADD CONSTRAINT FK_inbound_request_TO_inbound_request_items_1 FOREIGN KEY (inbound_request_id, member_id, manager_id) REFERENCES inbound_request (inbound_request_id, member_id, manager_id);
ALTER TABLE inbound_request_items
    ADD CONSTRAINT FK_coffees_TO_inbound_request_items_1 FOREIGN KEY (coffee_id) REFERENCES coffees (coffee_id);

-- outbound_request_items
DROP TABLE IF EXISTS outbound_request_items;
CREATE TABLE outbound_request_items
(
    outbound_request_item_id  bigint Auto_Increment NOT NULL,
    outbound_request_id       char(12)    NOT NULL,
    member_id                 varchar(15) NOT NULL,
    manager_id                varchar(15) NOT NULL,
    inventory_id              char(12)    NOT NULL,
    outbound_request_quantity integer     NULL,
    outbound_request_date     date	      NULL,
    CONSTRAINT PK_OUTBOUND_REQUEST_ITEMS PRIMARY KEY (outbound_request_item_id)
);
ALTER TABLE outbound_request_items
    ADD CONSTRAINT FK_outbound_request_TO_outbound_request_items_1 FOREIGN KEY (outbound_request_id, member_id, manager_id) REFERENCES outbound_request (outbound_request_id, member_id, manager_id);
ALTER TABLE outbound_request_items
    ADD CONSTRAINT FK_inventory_TO_outbound_request_items_1 FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id);

-- due diligence
DROP TABLE IF EXISTS due_diligence;
CREATE TABLE due_diligence
(
    due_diligence_id       char(12)     NOT NULL,
    inventory_id           char(12)     NOT NULL,
    due_diligence_log      varchar(255) NOT NULL,
    due_diligence_date     date         NOT NULL,
    due_diligence_approval char(4)      NOT NULL
);
ALTER TABLE due_diligence
    ADD CONSTRAINT PK_DUE_DILIGENCE PRIMARY KEY (due_diligence_id);
ALTER TABLE due_diligence
    ADD CONSTRAINT FK_inventory_TO_due_diligence_1 FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id);

-- inquiry
DROP TABLE IF EXISTS inquiry;
CREATE TABLE inquiry
(
    inquiry_id          INTEGER AUTO_INCREMENT  NOT NULL,
    inquiry_date        DATE                    NOT NULL,
    inquiry_category_id INTEGER                 NOT NULL,
    inquiry_content     TEXT                    NOT NULL,
    inquiry_status      ENUM ('PENDING','DONE') NOT NULL DEFAULT 'PENDING',
    reply_date          DATE                    NULL,
    reply_content       TEXT                    NULL,
    member_id           VARCHAR(15)             NOT NULL,
    manager_id          VARCHAR(15)             NULL,
    CONSTRAINT PK_inquiry PRIMARY KEY (inquiry_id)
);
ALTER TABLE inquiry
    ADD CONSTRAINT FK_inquiry_category_TO_inquiry_1 FOREIGN KEY (inquiry_category_id) REFERENCES inquiry_category (inquiry_category_id);
ALTER TABLE inquiry
    ADD CONSTRAINT FK_members_TO_inquiry_1 FOREIGN KEY (member_id) REFERENCES members (member_id);
ALTER TABLE inquiry
    ADD CONSTRAINT FK_managers_TO_inquiry_1 FOREIGN KEY (manager_id) REFERENCES managers (manager_id);