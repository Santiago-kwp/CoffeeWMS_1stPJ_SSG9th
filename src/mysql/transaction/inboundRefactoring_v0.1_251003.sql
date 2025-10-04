
use railway;

drop table if exists inbound_request_items;
drop table if exists inbound_request;

drop table if exists outbound_request_items;
drop table if exists outbound_request;

-- 입고/출고 요청 테이블 생성문 수정 : 25.10.03
/*
 1. 입/출고 요청 테이블의 기본키를 정수형으로 변경 : 고유성 관리의 복잡성을 제거, DB 성능을 향상 목적
 2. 참조관계를 (기존) 회원과 관리자 테이블과의 식별 관계 => (변경) 비식별 관계로 설정
 3. 입/출고 요청 상태를 정의하는 상태 속성 추가(status) : '대기', '승인완료', '승인거절', '입고완료' 등
 4. 논리 삭제를 위한 입/출고 삭제 여부 및 삭제 날짜 추가 (is_deleted, is_deleted_at)
 5. 입/출고의 회원 요청 및 관리자 승인 날짜를 표시하는 승인 날짜 속성 추가 (request_date, approval_date)
 */
--
CREATE TABLE inbound_request
(
    inbound_request_id   BIGINT      AUTO_INCREMENT NOT NULL, -- BIGINT Auto_Increment로 변경
    member_id            varchar(15) NOT NULL,
    manager_id           varchar(15) NULL,     -- 승인/거절한 관리자 ID (초기 NULL 허용)
    request_date         date        NOT NULL, -- 입고 요청 날짜 (대부분의 커머스/물류 시스템에서는 요청 단위로 한 건의 입고 요청 날짜를 받는 방식이 일반적)
    approval_date        date        NULL,     -- 승인/거절 처리 날짜 (처리 시 업데이트, NULL 가능)
    status               varchar(10) DEFAULT '대기' NOT NULL,
    inbound_receipt      text        NULL,
    is_deleted           tinyint     DEFAULT 0 NULL,
    is_deleted_at        date        NULL,
    CONSTRAINT PK_INBOUND_REQUEST PRIMARY KEY (inbound_request_id)
);

-- 입고 요청 상세 테이블
DROP TABLE IF EXISTS inbound_request_items;
CREATE TABLE inbound_request_items
(
    inbound_request_item_id  BIGINT Auto_Increment NOT NULL,
    inbound_request_id       BIGINT      NOT NULL, -- 타입 변경
    coffee_id                char(12)    NOT NULL,
    inbound_request_quantity integer     NULL,
    CONSTRAINT PK_INBOUND_REQUEST_ITEMS PRIMARY KEY (inbound_request_item_id)
);

ALTER TABLE inbound_request_items
    ADD CONSTRAINT FK_inbound_request_TO_inbound_request_items_1 FOREIGN KEY (inbound_request_id) REFERENCES inbound_request (inbound_request_id);
ALTER TABLE inbound_request_items
    ADD CONSTRAINT FK_coffees_TO_inbound_request_items_1 FOREIGN KEY (coffee_id) REFERENCES coffees (coffee_id);


-- 출고 요청 테이블 수정
CREATE TABLE outbound_request
(
    outbound_request_id  BIGINT      AUTO_INCREMENT NOT NULL, -- BIGINT Auto_Increment로 변경
    member_id            varchar(15) NOT NULL,
    manager_id           varchar(15) NULL,     -- 승인/거절한 관리자 ID (초기 NULL 허용)
    request_date         date        NOT NULL,
    approval_date        date        NULL,     -- 승인/거절 처리 날짜 (처리 시 업데이트, NULL 가능)
    status               varchar(10) DEFAULT '대기' NOT NULL,
    outbound_receipt     text        NULL,
    is_deleted           tinyint     DEFAULT 0 NULL,
    is_deleted_at        date        NULL,
    CONSTRAINT PK_INBOUND_REQUEST PRIMARY KEY (outbound_request_id)
);

-- 출고 요청 상세 테이블
DROP TABLE IF EXISTS outbound_request_items;
CREATE TABLE outbound_request_items
(
    outbound_request_item_id  BIGINT      Auto_Increment NOT NULL,
    outbound_request_id       BIGINT      NOT NULL, -- 타입 변경
    inventory_id              char(12)    NOT NULL,
    outbound_request_quantity integer     NULL,
    CONSTRAINT PK_INBOUND_REQUEST_ITEMS PRIMARY KEY (outbound_request_item_id)
);

ALTER TABLE outbound_request_items
    ADD CONSTRAINT FK_outbound_request_TO_outbound_request_items_1 FOREIGN KEY (outbound_request_id) REFERENCES outbound_request (outbound_request_id);
ALTER TABLE outbound_request_items
    ADD CONSTRAINT FK_inventory_TO_outbound_request_items_1 FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id);



-- 그 외 테이블

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
