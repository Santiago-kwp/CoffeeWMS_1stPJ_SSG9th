-- railway 테이블 생성문 및 데이터 생성문

use railway;
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
    
    
    ########################################################################
    ################# 데이터 insert문 ########################################
    -- 총관리자 (1명)
INSERT INTO users (user_id, user_approval, user_pwd, user_name, user_phone, user_email, user_company_code, user_address, user_join_date, user_type)
VALUES ('wmsAdmin', '승인완료', 'admin1234', '총관리자', '010-0000-0000', 'admin@wms.com', NULL, '서울시 강남구', '2023-01-15', '총관리자');

-- 창고관리자 (9명)
INSERT INTO users (user_id, user_approval, user_pwd, user_name, user_phone, user_email, user_company_code, user_address, user_join_date, user_type)
VALUES
('manager4821', '승인완료', 'manager1342', '김창고', '010-1111-0001', 'manager01@wms.com', NULL, '부산시 해운대구', '2023-02-01', '창고관리자'),
('manager9104', '승인완료', 'manager2132', '이창고', '010-1111-0002', 'manager02@wms.com', NULL, '인천시 연수구', '2023-02-02', '창고관리자'),
('manager3375', '승인완료', 'manager1342', '박창고', '010-1111-0003', 'manager03@wms.com', NULL, '대구시 수성구', '2023-02-03', '창고관리자'),
('manager8192', '승인완료', 'manager2132', '최창고', '010-1111-0004', 'manager04@wms.com', NULL, '광주시 서구', '2023-02-04', '창고관리자'),
('manager2256', '승인완료', 'manager1342', '정창고', '010-1111-0005', 'manager05@wms.com', NULL, '대전시 유성구', '2023-02-05', '창고관리자'),
('manager7439', '승인완료', 'manager2132', '강창고', '010-1111-0006', 'manager06@wms.com', NULL, '울산시 남구', '2023-02-06', '창고관리자'),
('manager5018', '승인완료', 'manager1342', '조창고', '010-1111-0007', 'manager07@wms.com', NULL, '세종시 나성동', '2023-02-07', '창고관리자'),
('manager6623', '승인완료', 'manager2132', '윤창고', '010-1111-0008', 'manager08@wms.com', NULL, '경기도 수원시', '2023-02-08', '창고관리자'),
('manager1198', '미승인', 'manager1342', '임창고', '010-1111-0009', 'manager09@wms.com', NULL, '경기도 성남시', '2023-02-09', '창고관리자');

-- 일반회원 (스타벅스: 5명)
INSERT INTO users (user_id, user_approval, user_pwd, user_name, user_phone, user_email, user_company_code, user_address, user_join_date, user_type)
VALUES
('usera001', '승인완료', 'userpass01', '스타벅스', '010-2222-0001', 'usera01@starbucks.com', '111-11-11111', '서울시 마포구', '2023-03-10', '일반회원'),
('usera002', '승인완료', 'userpass02', '스타벅스', '010-2222-0002', 'usera02@starbucks.com', '111-11-11111', '서울시 서대문구', '2023-03-11', '일반회원'),
('usera003', '승인완료', 'userpass03', '스타벅스', '010-2222-0003', 'usera03@starbucks.com', '111-11-11111', '서울시 용산구', '2023-03-12', '일반회원'),
('usera004', '승인완료', 'userpass04', '스타벅스', '010-2222-0004', 'usera04@starbucks.com', '111-11-11111', '서울시 종로구', '2023-03-13', '일반회원'),
('usera005', '미승인', 'userpass05', '스타벅스', '010-2222-0005', 'usera05@starbucks.com', '111-11-11111', '서울시 중구', '2023-03-14', '일반회원');

-- 일반회원 (투썸플레이스: 5명)
INSERT INTO users (user_id, user_approval, user_pwd, user_name, user_phone, user_email, user_company_code, user_address, user_join_date, user_type)
VALUES
('userb001', '승인완료', 'userpass06', '투썸플레이스', '010-3333-0001', 'userb01@twosome.com', '222-22-22222', '부산시 동래구', '2023-04-01', '일반회원'),
('userb002', '승인완료', 'userpass07', '투썸플레이스', '010-3333-0002', 'userb02@twosome.com', '222-22-22222', '부산시 부산진구', '2023-04-02', '일반회원'),
('userb003', '승인완료', 'userpass08', '투썸플레이스', '010-3333-0003', 'userb03@twosome.com', '222-22-22222', '부산시 연제구', '2023-04-03', '일반회원'),
('userb004', '승인완료', 'userpass09', '투썸플레이스', '010-3333-0004', 'userb04@twosome.com', '222-22-22222', '부산시 금정구', '2023-04-04', '일반회원'),
('userb005', '미승인', 'userpass10', '투썸플레이스', '010-3333-0005', 'userb05@twosome.com', '222-22-22222', '부산시 사하구', '2023-04-05', '일반회원');


use railway; -- 창고 3개에 각각의 코드 부여
INSERT INTO cargoes (cargo_code, cargo_name, cargo_address, cargo_grade, cargo_field, cargo_total_capa, cargo_use_capa) VALUES
                                                                                                                            ('BU', '부산신항창고', '부산광역시 강서구', '메인', 8000, 26000, 21000),
                                                                                                                            ('GJ', '곤지암창고', '경기도 광주시', '서브', 5000, 16000, 12000),
                                                                                                                            ('DD', '대덕창고', '대전광역시 유성구', '서브', 3000, 5000, 3500);

-- 생두존
INSERT INTO location_places VALUES
                                ('BURAWSTA001', 'RAW', '생두존', 'RACK001', '에티오피아 G1', 'CELL001', '스타벅스'),
                                ('BURAWTWO002', 'RAW', '생두존', 'RACK002', '브라질 FN', 'CELL002', '투썸');

-- 원두존
INSERT INTO location_places VALUES
    ('BUBEASTA01', 'BEA', '원두존', 'RACK003', '콜롬비아 SC', 'CELL003', '스타벅스');

-- 로스팅존
INSERT INTO location_places VALUES
    ('BUROASTA01', 'ROA', '로스팅존', 'RACK004', '20200925', 'CELL004', '스타벅스');

-- 레스팅존
INSERT INTO location_places VALUES
    ('BURESTWO01', 'RES', '레스팅존', 'RACK005', '20200922', 'CELL005', '투썸');

-- 디카페인존
INSERT INTO location_places VALUES
    ('BUDECASTA01', 'DEC', '디카페인존', 'RACK006', '20200915', 'CELL006', '스타벅스');

-- 곤지암 창고
INSERT INTO location_places VALUES
    ('GJSTAETH01', 'STA', '스타벅스', 'RACK007', '블렌드', 'CELL007', 'ETH-G1');

-- 대덕 창고
INSERT INTO location_places VALUES
    ('DDTWOBRA01', 'TWO', '투썸플레이스', 'RACK008', '싱글오리진', 'CELL008', 'BRA-FN');
    
#1:1문의 카테고리
INSERT INTO inquiry_category (inquiry_category_name) VALUES
    ('주문/결제'),
    ('배송'),
    ('상품/이벤트'),
    ('영수증/증빙서류'),
    ('기타');

#FAQ 카테고리
INSERT INTO faq_category (faq_category_name) VALUES
    ('회원가입/정보'),
    ('결제/취소/배송'),
    ('교환/반품/환불'),
    ('마일리지 적립'),
    ('세금계산서'),
    ('기타');

#공지사항
INSERT INTO notice (notice_title, notice_content, notice_date, notice_fixed, manager_id) VALUES
    ('추석 특별 이벤트 안내', '한가위를 맞아 전 회원 대상 할인 이벤트를 진행합니다.', '2025-09-02', 1, 'wmsAdmin'),
    ('시스템 업그레이드 완료', '서비스 속도 개선을 위한 시스템 업그레이드가 완료되었습니다.', '2025-09-04', 0, 'wmsAdmin'),
    ('회원 혜택 확대', 'VIP 등급 혜택이 10월부터 확대 적용됩니다.', '2025-09-06', 1, 'wmsAdmin'),
    ('정기 점검 공지', '9/20 01:00~03:00 정기 점검이 진행됩니다.', '2025-09-08', 0, 'wmsAdmin'),
    ('가을 신상품 출시', '가을 신상 의류 및 문구류가 입고되었습니다.', '2025-09-11', 0, 'wmsAdmin'),
    ('배송 정책 변경 안내', '택배사 변경으로 일부 지역 배송기간이 단축됩니다.', '2025-09-13', 1, 'wmsAdmin'),
    ('앱 업데이트 안내', '새로운 UI/UX가 적용된 앱 업데이트 버전을 출시했습니다.', '2025-09-15', 0, 'wmsAdmin'),
    ('이벤트 쿠폰 발급', '9월 한정 전 회원에게 10% 할인 쿠폰이 발급되었습니다.', '2025-09-17', 1, 'wmsAdmin'),
    ('보안 강화 공지', '개인정보 보호 강화를 위해 2단계 인증이 적용됩니다.', '2025-09-19', 0, 'wmsAdmin'),
    ('CS센터 운영시간 변경', '9/30부터 고객센터 운영시간이 평일 18시까지 연장됩니다.', '2025-09-20', 0, 'wmsAdmin');


#1:1문의
SELECT member_id FROM members;
INSERT INTO inquiry (inquiry_date, inquiry_category_id, inquiry_content, inquiry_status, reply_date, reply_content, member_id, manager_id) VALUES
#     ('2025-09-05', 1, '결제 후 주문내역이 확인되지 않습니다.', 'DONE', '2025-09-06', '시스템 반영 지연으로 수정 완료했습니다.', 'usera001', 'wmsAdmin'),
    ('2025-09-06', 2, '배송지 변경을 요청합니다.', 'PENDING', NULL, NULL, 'userb002', NULL),
    ('2025-09-07', 3, '이벤트 쿠폰 적용이 안 돼요.', 'DONE', '2025-09-08', '쿠폰 기한 만료 확인 후 보상 쿠폰 발급했습니다.', 'usera003', 'wmsAdmin'),
    ('2025-09-08', 4, '현금영수증 발급 가능 여부 궁금합니다.', 'PENDING', NULL, NULL, 'userb004', NULL),
    ('2025-09-09', 5, '선물 포장 가능 여부 문의합니다.', 'DONE', '2025-09-10', '선물 포장 서비스는 현재 제공하지 않습니다.', 'usera002', 'wmsAdmin'),
    ('2025-09-10', 1, '주문 취소 요청합니다.', 'PENDING', NULL, NULL, 'userb001', NULL),
    ('2025-09-11', 2, '배송이 너무 지연되고 있어요.', 'DONE', '2025-09-12', '택배사 물량 증가로 지연, 보상 쿠폰 지급했습니다.', 'usera002', 'wmsAdmin'),
    ('2025-09-12', 3, '상품 불량으로 교환 요청합니다.', 'PENDING', NULL, NULL, 'userb003', NULL),
    ('2025-09-13', 4, '세금계산서 발급 가능한가요?', 'DONE', '2025-09-14', '사업자등록번호 확인 후 발급 완료했습니다.', 'usera004', 'wmsAdmin'),
    ('2025-09-14', 5, '기타 문의: 포인트 적립 여부 확인해주세요.', 'PENDING', NULL, NULL, 'userb003', NULL);

#FAQ
INSERT INTO faq (faq_category_id, faq_question, faq_reply, faq_date, manager_id) VALUES
    (1, '비밀번호를 잊어버렸습니다. 어떻게 재설정하나요?', '로그인 화면에서 비밀번호 찾기를 통해 재설정 가능합니다.', '2025-09-02', 'wmsAdmin'),
    (2, '결제 수단은 어떤 것이 있나요?', '신용카드, 계좌이체, 간편결제가 지원됩니다.', '2025-09-03', 'wmsAdmin'),
    (3, '상품 교환 시 배송비는 누가 부담하나요?', '단순 변심은 고객 부담, 불량은 회사 부담입니다.', '2025-09-04', 'wmsAdmin'),
    (4, '적립금은 언제 사용 가능한가요?', '구매 확정 후 적립되며 즉시 사용 가능합니다.', '2025-09-05', 'wmsAdmin'),
    (5, '세금계산서 발행 절차는 어떻게 되나요?', '사업자 정보 입력 후 고객센터로 요청하시면 발행됩니다.', '2025-09-06', 'wmsAdmin'),
    (6, '기타: 고객센터 운영 시간은 어떻게 되나요?', '평일 09:00~18:00 운영합니다.', '2025-09-07', 'wmsAdmin'),
    (2, '주문 취소는 언제까지 가능한가요?', '상품 출고 전까지 취소가 가능합니다.', '2025-09-08', 'wmsAdmin'),
    (3, '반품 환불은 며칠 걸리나요?', '상품 회수 후 3~5일 내 환불 처리됩니다.', '2025-09-09', 'wmsAdmin'),
    (4, '마일리지 적립 내역은 어디서 확인하나요?', '마이페이지 > 적립금 내역에서 확인 가능합니다.', '2025-09-10', 'wmsAdmin'),
    (1, '회원 탈퇴는 어떻게 하나요?', '마이페이지 > 회원정보 관리에서 탈퇴 신청 가능합니다.', '2025-09-11', 'wmsAdmin');
    
    -- coffees 테이블 데이터 INSERT
INSERT INTO coffees (coffee_id, coffee_name, coffee_origin, decaf, coffee_roasted, price, coffee_grade, coffee_type) VALUES
('ETHASDN-0001', '에티오피아 예가체프 G1', 'ETH', 'N', '생두', 25000, 'G1', 'Arabica'),
('ETHAWDN-0002', '에티오피아 예가체프 G1', 'ETH', 'N', '원두', 32000, 'G1', 'Arabica'),
('ETHASDN-0003', '에티오피아 시다모 G2', 'ETH', 'N', '생두', 22000, 'G2', 'Arabica'),
('COLAWDN-0004', '콜롬비아 수프리모', 'COL', 'N', '원두', 28000, 'Supremo', 'Arabica'),
('COLAWDY-0005', '콜롬비아 수프리모 디카페인', 'COL', 'Y', '원두', 31000, 'Supremo', 'Arabica'),
('BRAASDN-0006', '브라질 산토스 No.2', 'BRA', 'N', '생두', 18000, 'No.2', 'Arabica'),
('BRAAWDN-0007', '브라질 산토스 No.2', 'BRA', 'N', '원두', 24000, 'No.2', 'Arabica'),
('KENAWDN-0008', '케냐 AA', 'KEN', 'N', '원두', 35000, 'AA', 'Arabica'),
('KENASDN-0009', '케냐 AA', 'KEN', 'N', '생두', 28000, 'AA', 'Arabica'),
('GTMAWDN-0010', '과테말라 안티구아 SHB', 'GTM', 'N', '원두', 30000, 'SHB', 'Arabica'),
('CRIASDN-0011', '코스타리카 따라주 SHB', 'CRI', 'N', '생두', 26000, 'SHB', 'Arabica'),
('IDNAWDN-0012', '인도네시아 만델링 G1', 'IDN', 'N', '원두', 29000, 'G1', 'Arabica'),
('VNMRSDN-0013', '베트남 로부스타 G1', 'VNM', 'N', '생두', 15000, 'Grade 1', 'Robusta'),
('VNMRWDN-0014', '베트남 로부스타 G1', 'VNM', 'N', '원두', 20000, 'Grade 1', 'Robusta'),
('PHLLSDN-0015', '필리핀 리베리카', 'PHL', 'N', '생두', 38000, 'Premium', 'Liberica'),
('ETHASDY-0016', '에티오피아 예가체프 G1 디카페인', 'ETH', 'Y', '생두', 29000, 'G1', 'Arabica'),
('COLASDN-0017', '콜롬비아 엑셀소', 'COL', 'N', '생두', 24000, 'Excelso', 'Arabica'),
('BRAAWDN-0018', '브라질 세하도', 'BRA', 'N', '원두', 26000, 'No.2', 'Arabica'),
('GTMASDN-0019', '과테말라 우에우에테낭고', 'GTM', 'N', '생두', 27000, 'SHB', 'Arabica'),
('IDNAWDN-0020', '인도네시아 자바', 'IDN', 'N', '원두', 27500, 'G1', 'Arabica'),
('UGARSDN-0021', '우간다 로부스타', 'UGA', 'N', '생두', 16000, 'Screen 18', 'Robusta'),
('INDRWDN-0022', '인도 카피로얄', 'IND', 'N', '원두', 21000, 'AAA', 'Robusta'),
('MEXAWDN-0023', '멕시코 치아파스', 'MEX', 'N', '원두', 26500, 'SHG', 'Arabica'),
('PERASDN-0024', '페루 찬차마요', 'PER', 'N', '생두', 23000, 'G1', 'Arabica'),
('TZAAWDN-0025', '탄자니아 피베리', 'TZA', 'N', '원두', 33000, 'AA', 'Arabica'),
('BRAAWDY-0026', '브라질 산토스 디카페인', 'BRA', 'Y', '원두', 27000, 'No.2', 'Arabica'),
('COLAWDN-0027', '콜롬비아 엑셀소', 'COL', 'N', '원두', 27500, 'Excelso', 'Arabica'),
('GTMASDN-0028', '과테말라 안티구아 SHB', 'GTM', 'N', '생두', 26500, 'SHB', 'Arabica'),
('IDNASDN-0029', '인도네시아 만델링 G1', 'IDN', 'N', '생두', 25500, 'G1', 'Arabica'),
('KENAWDN-0030', '케냐 AB', 'KEN', 'N', '원두', 31000, 'AB', 'Arabica');

INSERT INTO inbound_request (inbound_request_id, member_id, manager_id, inbound_request_approved, inbound_receipt)
VALUES
('inbound00001', 'usera001', 'wmsAdmin', 0, NULL),
('inbound00002', 'usera002', 'manager4821', 0, NULL),
('inbound00003', 'usera003', 'manager9104', 0, NULL),
('inbound00004', 'usera004', 'manager3375', 0, NULL);

INSERT INTO outbound_request (outbound_request_id, member_id, manager_id, outbound_request_approved, outbound_order)
VALUES
('outbound0001', 'userb001', 'manager2256', 0, NULL),
('outbound0002', 'userb002', 'manager7439', 0, NULL),
('outbound0003', 'userb003', 'wmsAdmin', 0, NULL),
('outbound0004', 'userb004', 'manager4821', 0, NULL);

-- inventory 테이블 데이터 INSERT
INSERT INTO inventory (inventory_id, coffee_id, location_place_id, inventory_quantity) VALUES
-- 2025년 9월 20일 입고분
('250920000101', 'ETHASDN-0001', 'BURAWSTA001', 250),
('250920000301', 'ETHASDN-0003', 'BURAWSTA001', 400),
('250920000601', 'BRAASDN-0006', 'BURAWTWO002', 300),
('250920001101', 'CRIASDN-0011', 'BURAWSTA001', 150),
('250920001301', 'VNMRSDN-0013', 'BURAWTWO002', 500),

-- 2025년 9월 22일 입고분
('250922000901', 'KENASDN-0009', 'BURAWSTA001', 220),
('250922001701', 'COLASDN-0017', 'BURAWTWO002', 180),
('250922001601', 'ETHASDY-0016', 'BUDECASTA01', 100),
('250922000401', 'COLAWDN-0004', 'BUBEASTA01', 320),
('250922000602', 'BRAASDN-0006', 'BURAWTWO002', 150), -- 0006번 커피, 22일자 두번째 입고
('250922000101', 'ETHASDN-0001', 'BURAWSTA001', 200),

-- 2025년 9월 24일 입고분 (가공 및 창고 이동)
('250924000201', 'ETHAWDN-0002', 'GJSTAETH01', 550), -- 에티오피아 원두 곤지암 입고
('250924000701', 'BRAAWDN-0007', 'DDTWOBRA01', 600), -- 브라질 원두 대덕 입고
('250924000501', 'COLAWDY-0005', 'BUDECASTA01', 120),
('250924000801', 'KENAWDN-0008', 'GJSTAETH01', 250),
('250924001401', 'VNMRWDN-0014', 'DDTWOBRA01', 450),
('250924001001', 'GTMAWDN-0010', 'GJSTAETH01', 180),
('250924002701', 'COLAWDN-0027', 'DDTWOBRA01', 280),

-- 2025년 9월 25일 입고분
('250925001901', 'GTMASDN-0019', 'BURAWSTA001', 130),
('250925002401', 'PERASDN-0024', 'BURAWTWO002', 190),
('250925002901', 'IDNASDN-0029', 'BURAWSTA001', 210),
('250925001201', 'IDNAWDN-0012', 'DDTWOBRA01', 300),
('250925002001', 'IDNAWDN-0020', 'DDTWOBRA01', 240),
('250925002601', 'BRAAWDY-0026', 'BUDECASTA01', 90),
('250925000401', 'COLAWDN-0004', 'BUBEASTA01', 150),
('250925000402', 'COLAWDN-0004', 'GJSTAETH01', 400), -- 0004번 커피, 25일자 두번째 입고 (다른 창고로)

-- 2025년 9월 26일 입고분
('250926002101', 'UGARSDN-0021', 'BURAWTWO002', 330),
('250926002801', 'GTMASDN-0028', 'BURAWSTA001', 110),
('250926001501', 'PHLLSDN-0015', 'BURAWSTA001', 80),
('250926002201', 'INDRWDN-0022', 'DDTWOBRA01', 180),
('250926002301', 'MEXAWDN-0023', 'GJSTAETH01', 200),
('250926002501', 'TZAAWDN-0025', 'GJSTAETH01', 150),
('250926003001', 'KENAWDN-0030', 'GJSTAETH01', 170),
('250926000201', 'ETHAWDN-0002', 'BUBEASTA01', 250), -- 0002번 커피, 26일자 입고 (부산 원두존)
('250926000202', 'ETHAWDN-0002', 'GJSTAETH01', 300), -- 0002번 커피, 26일자 두번째 입고 (곤지암)
('250926000701', 'BRAAWDN-0007', 'DDTWOBRA01', 200);

INSERT INTO inbound_request (inbound_request_id, member_id, manager_id, inbound_request_approved, inbound_receipt)
VALUES     ('inbound00003', 'usera003', 'manager9104', 0, NULL),
    ('inbound00004', 'usera004', 'manager3375', 0, NULL);

INSERT INTO outbound_request (outbound_request_id, member_id, manager_id, outbound_request_approved, outbound_order)
VALUES
    ('outbound0001', 'userb001', 'manager2256', 0, NULL),
    ('outbound0002', 'userb002', 'manager7439', 0, NULL),
    ('outbound0003', 'userb003', 'wmsAdmin', 0, NULL),
    ('outbound0004', 'userb004', 'manager4821', 0, NULL);

INSERT INTO inbound_request_items (inbound_request_id, member_id, manager_id, coffee_id, inbound_request_quantity, inbound_request_date)
VALUES
    ('inbound00001', 'usera001', 'wmsAdmin', 'ETHASDN-0001', 2500, '2025-03-15'),
    ('inbound00002', 'usera002', 'manager4821', 'COLAWDN-0004', 1800, '2025-07-22'),
    ('inbound00003', 'usera003', 'manager9104', 'BRAASDN-0006', 1250, '2026-01-10'),
    ('inbound00004', 'usera004', 'manager3375', 'KENASDN-0009', 2800, '2026-05-05');

INSERT INTO inbound_request_items (inbound_request_id, member_id, manager_id, coffee_id, inbound_request_quantity, inbound_request_date)
VALUES
    ('inbound00001', 'usera001', 'wmsAdmin', 'ETHASDN-0001', 2500, '2025-03-15'),
    ('inbound00002', 'usera002', 'manager4821', 'COLAWDN-0004', 1800, '2025-07-22'),
    ('inbound00003', 'usera003', 'manager9104', 'BRAASDN-0006', 1250, '2026-01-10'),
    ('inbound00004', 'usera004', 'manager3375', 'KENASDN-0009', 2800, '2026-05-05');
    

-- due_diligence 테이블 데이터 INSERT
INSERT INTO due_diligence (due_diligence_id, inventory_id, due_diligence_log, due_diligence_date, due_diligence_approval) VALUES
-- 2025년 9월 23일 실사
('DIL250923001', '250920000101', '정기 재고 실사. 전산 수량과 실물 수량 일치.', '2025-09-23', '승인완료'),
('DIL250923002', '250920000601', '실물 재고 10kg 부족 확인. 원인 파악 후 재보고 예정.', '2025-09-23', '승인요청'),

-- 2025년 9월 25일 실사
('DIL250925001', '250922000901', '일부 포대에서 습기 발견. 샘플 채취 후 보고.', '2025-09-25', '승인요청'),
('DIL250925002', '250922001601', '실사자 오기입으로 인한 반려. 수량 재확인 필요.', '2025-09-25', '승인반려'),
('DIL250925003', '250924000201', '전산 재고 550kg, 실물 재고 550kg. 이상 없음.', '2025-09-25', '승인완료'),
('DIL250925004', '250924000701', '수량 일치 확인.', '2025-09-25', '승인완료'),

-- 2025년 9월 26일 실사
('DIL250926001', '250922001601', '재실사. 전산 수량보다 2kg 초과 확인. 입고 과정 오류로 추정되어 전산 반영.', '2025-09-26', '승인완료'),
('DIL250926002', '250924000501', '디카페인 원두 샘플 테스트. 품질 기준 충족.', '2025-09-26', '승인완료'),
('DIL250926003', '250925001901', '파손된 포대 발견. 3kg 가량 손실 발생.', '2025-09-26', '승인요청'),

-- 2025년 9월 27일 실사
('DIL250927001', '250925002601', '로케이션 태그와 실물 상품 불일치. 시스템 위치 정보 수정 요청.', '2025-09-27', '승인요청'),
('DIL250927002', '250926002301', '신규 입고분 수량 확인. 전산과 일치.', '2025-09-27', '승인완료'),
('DIL250927003', '250926003001', '선입선출 원칙 미준수 의심. 입고일자 라벨 확인 필요.', '2025-09-27', '승인반려'),
('DIL250927004', '250925000402', '곤지암 창고 정기 실사. 수량 400kg 정확함.', '2025-09-27', '승인완료'),
('DIL250927005', '250926000701', '실물 재고 200kg, 전산 재고 200kg. 이상 없음.', '2025-09-27', '승인완료'),

-- 2025년 9월 28일 실사
('DIL250928001', '250926000202', '입고 직후 수량 확인. 300kg 일치.', '2025-09-28', '승인요청'),
('DIL250928002', '250926002101', '로부스타 재고 품질 검사. 이물질 혼입 없음. 상태 양호.', '2025-09-28', '승인완료'),
('DIL250928003', '250920001301', '장기 보관 생두 샘플링. 수분 함량 11.5%로 기준치 내 유지.', '2025-09-28', '승인완료'),
('DIL250928004', '250924001401', '실물 재고 5kg 초과 확인. 원인 분석 후 보고 예정.', '2025-09-28', '승인요청'),
('DIL250928005', '250925002401', '보고서 양식 오류로 반려. 지정된 템플릿으로 재작성 필요.', '2025-09-28', '승인반려'),

-- 2025년 9월 29일 실사
('DIL250929001', '250926002801', '수량 일치. 특이사항 없음.', '2025-09-29', '승인완료'),
('DIL250929002', '250922001701', '실물 재고 180kg, 전산 재고 180kg. 일치함.', '2025-09-29', '승인완료'),
('DIL250929003', '250924000801', '포장재 일부 훼손. 재포장 필요.', '2025-09-29', '승인요청'),
('DIL250929004', '250925000401', '부산 원두존 재고 실사. 수량 정확함.', '2025-09-29', '승인완료'),
('DIL250929005', '250926001501', '리베리카 품종 상태 점검. 향미 손실 없음.', '2025-09-29', '승인요청'),
('DIL250929006', '250924001001', '실사 데이터 중복 제출로 인한 반려.', '2025-09-29', '승인반려'),
('DIL250929007', '250926002201', '수량 및 품질 이상 없음 확인.', '2025-09-29', '승인완료'),

-- 2025년 9월 30일 실사
('DIL250930001', '250920000301', '전산 재고와 실물 재고 일치.', '2025-09-30', '승인완료'),
('DIL250930002', '250922000602', '실물 재고 150kg 확인 완료.', '2025-09-30', '승인완료'),
('DIL250930003', '250926002501', '탄자니아 피베리 품질 등급 재확인. 이상 없음.', '2025-09-30', '승인요청');


-- 창고 관리 테이블 데이터
INSERT INTO cargo_management (manager_id, cargo_id)
VALUES
    ('wmsAdmin', 1),
    ('wmsAdmin', 2),
    ('wmsAdmin', 3),
    ('manager4821', 1),
    ('manager4821', 2),
    ('manager9104', 1),
    ('manager9104', 3),
    ('manager3375', 1),
    ('manager3375', 2),
    ('manager8192', 1),
    ('manager8192', 2),
    ('manager8192', 3),
    ('manager2256', 2),
    ('manager2256', 3),
    ('manager7439', 1),
    ('manager7439', 2),
    ('manager7439', 3),
    ('manager4821', 3),
    ('manager9104', 2);
    