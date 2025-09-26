-- 데이터 삽입 스크립트
-- 외래 키 제약 조건을 고려하여 순서대로 데이터를 삽입합니다.

-- 1단계: 외래 키가 없거나 본 테이블 내 속성을 셀프 조인한 테이블

-- users 테이블 데이터 (20개)
INSERT INTO Users (user_id, user_approval, user_pwd, user_name, user_phone, user_email, user_company_code, user_address, user_join_date, user_type) VALUES
('member12345', '미승인', 'pwd12345', '김회원', '010-1234-5678', 'kim@example.com', 'CMP10000001', '서울특별시 강남구', '2023-01-15', '일반회원'),
('member12346', '승인완료', 'pwd12346', '이회원', '010-2345-6789', 'lee@example.com', 'CMP10000002', '부산광역시 해운대구', '2023-02-20', '일반회원'),
('manager1234', '미승인', 'm_pwd1234', '박관리', '010-3456-7890', 'park@example.com', NULL, '경기도 성남시', '2023-03-01', '창고관리자'),
('wmsAdmin', '승인완료', 'admin_pwd', '총관리', '010-4567-8901', 'admin@wms.com', NULL, '서울시 종로구', '2022-12-01', '총관리자'),
('member12347', '승인완료', 'pwd12347', '최회원', '010-4567-8910', 'choi@example.com', 'CMP10000003', '인천광역시 연수구', '2023-04-05', '일반회원'),
('manager1235', '승인완료', 'm_pwd1235', '정관리', '010-5678-9012', 'jung@example.com', NULL, '대전광역시 유성구', '2023-04-10', '창고관리자'),
('member12348', '승인완료', 'pwd12348', '조회원', '010-6789-0123', 'jo@example.com', 'CMP10000004', '대구광역시 수성구', '2023-05-12', '일반회원'),
('member12349', '미승인', 'pwd12349', '윤회원', '010-7890-1234', 'yoon@example.com', 'CMP10000005', '광주광역시 북구', '2023-06-25', '일반회원'),
('manager1236', '승인완료', 'm_pwd1236', '이총괄', '010-8901-2345', 'lee@wms.com', NULL, '서울시 중구', '2023-06-30', '총관리자'),
('member12350', '승인완료', 'pwd12350', '박회원', '010-9012-3456', 'park@example.com', 'CMP10000006', '울산광역시 남구', '2023-07-07', '일반회원'),
('member12351', '승인완료', 'pwd12351', '송회원', '010-0123-4567', 'song@example.com', 'CMP10000007', '경기도 수원시', '2023-08-18', '일반회원'),
('member12352', '미승인', 'pwd12352', '고회원', '010-1122-3344', 'ko@example.com', 'CMP10000008', '충청남도 천안시', '2023-09-22', '일반회원'),
('manager1237', '승인완료', 'm_pwd1237', '한관리', '010-2233-4455', 'han@example.com', NULL, '강원도 원주시', '2023-10-15', '창고관리자'),
('member12353', '승인완료', 'pwd12353', '신회원', '010-3344-5566', 'shin@example.com', 'CMP10000009', '전라북도 전주시', '2023-11-01', '일반회원'),
('member12354', '승인완료', 'pwd12354', '오회원', '010-4455-6677', 'oh@example.com', 'CMP10000010', '경상북도 포항시', '2023-12-09', '일반회원'),
('member12355', '미승인', 'pwd12355', '강회원', '010-5566-7788', 'kang@example.com', 'CMP10000011', '제주특별자치도 제주시', '2024-01-01', '일반회원'),
('member12356', '승인완료', 'pwd12356', '이회원', '010-6677-8899', 'lee2@example.com', 'CMP10000012', '서울특별시 서초구', '2024-02-10', '일반회원'),
('manager1238', '승인완료', 'm_pwd1238', '김창고', '010-7788-9900', 'kimc@example.com', NULL, '경기도 화성시', '2024-03-01', '창고관리자'),
('member12357', '승인완료', 'pwd12357', '정회원', '010-8899-0011', 'jung2@example.com', 'CMP10000013', '부산광역시 수영구', '2024-04-12', '일반회원'),
('member12358', '미승인', 'pwd12358', '최회원', '010-9900-1122', 'choi2@example.com', 'CMP10000014', '인천광역시 중구', '2024-05-05', '일반회원');

-- members 테이블 데이터 (20개)
INSERT INTO Members (member_id, member_pwd, member_company_name, member_phone, member_email, member_company_code, member_address, member_login, member_start_date, member_expired_date) VALUES
('member12346', 'pwd12346', '이회원사', '010-2345-6789', 'lee@example.com', 'CMP10000002', '부산광역시 해운대구', FALSE, '2023-02-20', '2024-02-20'),
('member12347', 'pwd12347', '최회원사', '010-4567-8910', 'choi@example.com', 'CMP10000003', '인천광역시 연수구', TRUE, '2023-04-05', '2024-04-05'),
('member12348', 'pwd12348', '조회원사', '010-6789-0123', 'jo@example.com', 'CMP10000004', '대구광역시 수성구', FALSE, '2023-05-12', '2024-05-12'),
('member12350', 'pwd12350', '박회원사', '010-9012-3456', 'park@example.com', 'CMP10000006', '울산광역시 남구', TRUE, '2023-07-07', '2024-07-07'),
('member12351', 'pwd12351', '송회원사', '010-0123-4567', 'song@example.com', 'CMP10000007', '경기도 수원시', FALSE, '2023-08-18', '2024-08-18'),
('member12353', 'pwd12353', '신회원사', '010-3344-5566', 'shin@example.com', 'CMP10000009', '전라북도 전주시', TRUE, '2023-11-01', '2024-11-01'),
('member12354', 'pwd12354', '오회원사', '010-4455-6677', 'oh@example.com', 'CMP10000010', '경상북도 포항시', FALSE, '2023-12-09', '2024-12-09'),
('member12356', 'pwd12356', '이회원사', '010-6677-8899', 'lee2@example.com', 'CMP10000012', '서울특별시 서초구', TRUE, '2024-02-10', '2025-02-10'),
('member12357', 'pwd12357', '정회원사', '010-8899-0011', 'jung2@example.com', 'CMP10000013', '부산광역시 수영구', TRUE, '2024-04-12', '2025-04-12');

-- managers 테이블 데이터 (20개)
INSERT INTO managers (manager_id, manager_pwd, manager_name, manager_phone, manager_email, manager_login, manager_hire_date, manager_position) VALUES
('wmsAdmin', 'admin_pwd', '총관리', '010-4567-8901', 'admin@wms.com', TRUE, '2022-12-01', '총관리자'),
('manager1235', 'm_pwd1235', '정관리', '010-5678-9012', 'jung@example.com', FALSE, '2023-04-10', '창고관리자'),
('manager1236', 'm_pwd1236', '이총괄', '010-8901-2345', 'lee@wms.com', TRUE, '2023-06-30', '총관리자'),
('manager1237', 'm_pwd1237', '한관리', '010-2233-4455', 'han@example.com', FALSE, '2023-10-15', '창고관리자'),
('manager1238', 'm_pwd1238', '김창고', '010-7788-9900', 'kimc@example.com', TRUE, '2024-03-01', '창고관리자'),
('manager1239', 'm_pwd1239', '박창고', '010-1111-2222', 'parkc@example.com', FALSE, '2024-05-20', '창고관리자'),
('manager1240', 'm_pwd1240', '유총괄', '010-3333-4444', 'yuadm@wms.com', TRUE, '2024-06-10', '총관리자');

-- cargoes 테이블 데이터 (20개)
INSERT INTO cargoes (cargo_code, cargo_name, cargo_address, cargo_grade, cargo_field, cargo_total_capa, cargo_use_capa) VALUES
('BU', '부산신항창고', '부산광역시 강서구', '메인', 10000, 50000, 35000),
('GJ', '곤지암 창고', '경기도 광주시', '서브', 8000, 40000, 25000),
('DD', '대덕창고', '대전광역시 유성구', '서브', 7000, 35000, 20000),
('DD', '대덕창고', '대전광역시 유성구', '메인', 6000, 30000, 15000),
('BU', '부산신항창고', '부산광역시 강서구', '서브', 9000, 45000, 22000),
('GJ', '곤지암 창고', '경기도 광주시', '메인', 8500, 42000, 28000);


-- location_places 테이블 데이터 (20개)
INSERT INTO location_places (location_place_id, zone_id, zone_name, rack_id, rack_name, cell_id, cell_name) VALUES
('BUSTA0101-01', 'BU-Z1', '생두존', 'R1', 'R-A', 'C1', 'C-01'),
('BUSTA0102-02', 'BU-Z2', '원두존', 'R2', 'R-B', 'C2', 'C-02'),
('BUSTA0103-03', 'BU-Z3', '로스팅존', 'R3', 'R-C', 'C3', 'C-03'),
('BUSTA0104-04', 'BU-Z4', '레스팅존', 'R4', 'R-D', 'C4', 'C-04'),
('BUSTA0105-05', 'BU-Z5', '디카페인존', 'R5', 'R-E', 'C5', 'C-05'),
('GJTWO0101-01', 'GJ-Z1', '투썸플레이스', 'R1', 'R-A', 'C1', 'C-01'),
('GJTWO0202-02', 'GJ-Z2', '스타벅스', 'R2', 'R-B', 'C2', 'C-02'),
('DDSTA0101-01', 'DD-Z1', '스타벅스', 'R1', 'R-A', 'C1', 'C-01'),
('DDSTA0202-02', 'DD-Z2', '투썸플레이스', 'R2', 'R-B', 'C2', 'C-02'),
('BUSTA0106-06', 'BU-Z1', '생두존', 'R6', 'R-F', 'C6', 'C-06'),
('BUSTA0107-07', 'BU-Z2', '원두존', 'R7', 'R-G', 'C7', 'C-07'),
('GJSTA0101-03', 'GJ-Z3', '스타벅스', 'R3', 'R-C', 'C3', 'C-03'),
('BUSTA0108-08', 'BU-Z3', '로스팅존', 'R8', 'R-H', 'C8', 'C-08'),
('DDTWO0301-03', 'DD-Z3', '투썸플레이스', 'R3', 'R-C', 'C3', 'C-03'),
('BUSTA0109-09', 'BU-Z4', '레스팅존', 'R9', 'R-I', 'C9', 'C-09'),
('BUSTA0110-10', 'BU-Z5', '디카페인존', 'R10', 'R-J', 'C10', 'C-10'),
('GJTWO0404-04', 'GJ-Z4', '투썸플레이스', 'R4', 'R-D', 'C4', 'C-04'),
('DDSTA0401-04', 'DD-Z4', '스타벅스', 'R4', 'R-D', 'C4', 'C-04'),
('DDSTA0501-05', 'DD-Z5', '스타벅스', 'R5', 'R-E', 'C5', 'C-05');

-- coffees 테이블 데이터 (20개)
INSERT INTO coffees (coffee_id, coffee_name, coffee_origin, decaf, coffee_roasted, price, coffee_grade, coffee_type) VALUES
('COF00000001', '에티오피아 예가체프', 'ETH', 'N', 'M', 15000, 'Specialty', 'Single'),
('COF00000002', '콜롬비아 수프리모', 'COL', 'N', 'D', 12000, 'Premium', 'Single'),
('COF00000003', '브라질 산토스', 'BRA', 'N', 'L', 10000, 'Commercial', 'Blend'),
('COF00000004', '케냐 AA', 'KEN', 'N', 'M', 18000, 'Specialty', 'Single'),
('COF00000005', '과테말라 안티구아', 'GTM', 'N', 'D', 13000, 'Premium', 'Single'),
('COF00000006', '코스타리카 타라주', 'CRI', 'N', 'M', 14000, 'Premium', 'Single'),
('COF00000007', '디카페인 블렌드', 'BRA', 'Y', 'M', 16000, 'Premium', 'Blend'),
('COF00000008', '탄자니아 킬리만자로', 'TZA', 'N', 'D', 17000, 'Specialty', 'Single'),
('COF00000009', '인도네시아 만델링', 'IDN', 'N', 'D', 19000, 'Specialty', 'Single'),
('COF00000010', '파나마 게이샤', 'PAN', 'N', 'L', 25000, 'Specialty', 'Single'),
('COF00000011', '예멘 모카 마타리', 'YEM', 'N', 'M', 22000, 'Specialty', 'Single'),
('COF00000012', '하와이 코나', 'USA', 'N', 'L', 30000, 'Specialty', 'Single'),
('COF00000013', '콜롬비아 디카페인', 'COL', 'Y', 'M', 15500, 'Premium', 'Single'),
('COF00000014', '멕시코 치아파스', 'MEX', 'N', 'M', 11000, 'Commercial', 'Single'),
('COF00000015', '에티오피아 시다모', 'ETH', 'N', 'D', 17500, 'Specialty', 'Single'),
('COF00000016', '니카라과 마라카투라', 'NIC', 'N', 'M', 18500, 'Specialty', 'Single'),
('COF00000017', '과테말라 디카페인', 'GTM', 'Y', 'D', 14500, 'Premium', 'Single'),
('COF00000018', '케냐 카구모', 'KEN', 'N', 'M', 19500, 'Specialty', 'Single'),
('COF00000019', '브라질 세하도', 'BRA', 'N', 'L', 9500, 'Commercial', 'Single'),
('COF00000020', '온두라스 마르칼라', 'HND', 'N', 'M', 12500, 'Premium', 'Single');

-- inquiry_category 테이블 데이터
INSERT INTO inquiry_category (inquiry_category_name) VALUES
('입고 관련'), ('출고 관련'), ('재고 관련'), ('회원 정보'), ('시스템 오류'), ('기타');

-- faq_category 테이블 데이터
INSERT INTO faq_category (faq_category_name) VALUES
('입고 절차'), ('출고 절차'), ('회원 가입'), ('시스템 사용법'), ('계약 문의');

-- 2단계: 1단계 테이블의 기본키를 외래키로 한 개만 갖는 테이블

-- notice 테이블 데이터 (총관리자 아이디 'wmsAdmin' 사용)
INSERT INTO notice (notice_title, notice_content, notice_date, notice_fixed, manager_id) VALUES
('시스템 점검 안내', '시스템 점검이 24시간 동안 있을 예정입니다.', '2023-01-10', TRUE, 'wmsAdmin'),
('창고 이용 수칙', '새로운 창고 이용 수칙이 공지되었습니다.', '2023-02-15', FALSE, 'wmsAdmin'),
('서비스 이용약관 변경', '서비스 이용약관이 변경되었습니다.', '2023-03-20', FALSE, 'wmsAdmin'),
('추석 연휴 휴무 안내', '추석 연휴 동안 고객센터 휴무 예정입니다.', '2023-09-01', TRUE, 'wmsAdmin'),
('신규 창고 오픈 공지', '부산신항창고가 오픈 예정입니다.', '2023-05-10', TRUE, 'wmsAdmin'),
('시스템 업데이트', '시스템 기능이 업데이트 되었습니다.', '2023-07-01', FALSE, 'wmsAdmin');


-- 3단계: 1, 2단계의 두 테이블의 기본키를 외래키로 갖거나 자신의 기본키와 같이 복합키로 가지는 테이블

-- faq 테이블 데이터 (총관리자 아이디 'wmsAdmin' 사용)
INSERT INTO faq (faq_category_id, faq_question, faq_reply, faq_date, manager_id) VALUES
(1, '입고 요청은 어떻게 하나요?', '입고 요청은 회원 로그인 후 ...', '2023-02-25', 'wmsAdmin'),
(3, '회원 탈퇴는 어떻게 하나요?', '마이페이지에서 탈퇴 요청을 하시면 ...', '2023-03-05', 'wmsAdmin'),
(4, '재고 현황은 실시간인가요?', '네, 재고 현황은 실시간으로 업데이트 됩니다.', '2023-04-01', 'wmsAdmin'),
(1, '입고 거절 사유가 궁금합니다.', '입고 거절 사유는 담당 관리자에게 문의 바랍니다.', '2023-05-01', 'wmsAdmin'),
(2, '출고 신청 후 언제 받을 수 있나요?', '출고 신청 후 영업일 기준 3일 이내에 배송이 시작됩니다.', '2023-06-01', 'wmsAdmin');

-- inbound_request 테이블 데이터
INSERT INTO inbound_request (inbound_request_id, member_id, manager_id, inbound_request_approved, inbound_receipt) VALUES
('INB10000001', 'member12347', 'manager1235', TRUE, '입고 완료 확인서입니다.'),
('INB10000002', 'member12348', 'manager1237', FALSE, NULL),
('INB10000003', 'member12350', 'manager1235', TRUE, '입고 완료 확인서입니다.'),
('INB10000004', 'member12351', 'manager1237', FALSE, NULL),
('INB10000005', 'member12353', 'manager1235', TRUE, '입고 완료 확인서입니다.'),
('INB10000006', 'member12354', 'manager1237', FALSE, NULL);

-- outbound_request 테이블 데이터
INSERT INTO outbound_request (outbound_request_id, member_id, manager_id, outbound_request_approved, outbound_order) VALUES
('OUT10000001', 'member12347', 'manager1235', TRUE, '출고 지시서입니다.'),
('OUT10000002', 'member12348', 'manager1237', FALSE, NULL),
('OUT10000003', 'member12350', 'manager1235', TRUE, '출고 지시서입니다.'),
('OUT10000004', 'member12351', 'manager1237', FALSE, NULL),
('OUT10000005', 'member12353', 'manager1235', TRUE, '출고 지시서입니다.');

-- cargo_management 테이블 데이터
INSERT INTO cargo_management (manager_id, cargo_id) VALUES
('manager1235', 1), ('manager1237', 2), ('manager1235', 3);

-- locations 테이블 데이터
INSERT INTO locations (location_id, cargo_id, location_place_id) VALUES
('LOC10000001', 1, 'BUSTA0101-01'),
('LOC10000002', 1, 'BUSTA0102-02'),
('LOC10000003', 2, 'GJTWO0101-01'),
('LOC10000004', 3, 'DDSTA0101-01'),
('LOC10000005', 1, 'BUSTA0103-03'),
('LOC10000006', 1, 'BUSTA0104-04'),
('LOC10000007', 2, 'GJTWO0202-02'),
('LOC10000008', 3, 'DDSTA0202-02');

-- inventory 테이블 데이터
INSERT INTO inventory (inventory_id, coffee_id, location_place_id, inventory_quantity) VALUES
('INV10000001', 'COF00000001', 'BUSTA0101-01', 500),
('INV10000002', 'COF00000002', 'BUSTA0102-02', 300),
('INV10000003', 'COF00000003', 'GJTWO0101-01', 200),
('INV10000004', 'COF00000004', 'DDSTA0101-01', 400),
('INV10000005', 'COF00000005', 'BUSTA0101-01', 150),
('INV10000006', 'COF00000006', 'GJTWO0202-02', 250),
('INV10000007', 'COF00000007', 'BUSTA0105-05', 80),
('INV10000008', 'COF00000008', 'BUSTA0106-06', 120),
('INV10000009', 'COF00000009', 'GJSTA0101-03', 180),
('INV10000010', 'COF00000010', 'DDTWO0301-03', 210);

-- 4단계: 3단계 테이블의 기본키를 외래키로 가지는 테이블

-- inbound_request_items 테이블 데이터
INSERT INTO inbound_request_items (inbound_request_item_id, inbound_request_id, member_id, manager_id, coffee_id, inbound_request_quantity, inbound_request_date) VALUES
(1, 'INB10000001', 'member12347', 'manager1235', 'COF00000001', 100, '2023-05-18'),
(2, 'INB10000001', 'member12347', 'manager1235', 'COF00000005', 50, '2023-05-18'),
(3, 'INB10000002', 'member12348', 'manager1237', 'COF00000002', 200, '2023-05-25'),
(4, 'INB10000003', 'member12350', 'manager1235', 'COF00000003', 150, '2023-07-28'),
(5, 'INB10000003', 'member12350', 'manager1235', 'COF00000006', 75, '2023-07-28'),
(6, 'INB10000004', 'member12351', 'manager1237', 'COF00000004', 120, '2023-08-20'),
(7, 'INB10000005', 'member12353', 'manager1235', 'COF00000007', 80, '2023-10-30');

-- outbound_request_items 테이블 데이터
INSERT INTO outbound_request_items (outbound_request_item_id, outbound_request_id, member_id, manager_id, inventory_id, outbound_request_quantity, outbound_request_date) VALUES
(1, 'OUT10000001', 'member12347', 'manager1235', 'INV10000001', 50, '2023-05-20'),
(2, 'OUT10000001', 'member12347', 'manager1235', 'INV10000005', 20, '2023-05-20'),
(3, 'OUT10000003', 'member12350', 'manager1235', 'INV10000003', 40, '2023-08-01');

-- due_diligence 테이블 데이터
INSERT INTO due_diligence (due_diligence_id, inventory_id, due_diligence_log, due_diligence_date, due_diligence_approval) VALUES
('DIL10000001', 'INV10000001', '재고 수량 일치', '2023-06-01', '승인완료'),
('DIL10000002', 'INV10000002', '포장 상태 불량', '2023-06-02', '미승인'),
('DIL10000003', 'INV10000003', '재고 수량 일치', '2023-08-05', '승인완료');

-- inquiry 테이블 데이터 (총관리자 아이디 'wmsAdmin' 사용)
INSERT INTO inquiry (inquiry_date, inquiry_category_id, inquiry_content, inquiry_status, reply_date, reply_content, member_id, manager_id) VALUES
('2023-03-10', 4, '로그인이 안 돼요.', 'DONE', '2023-03-11', '로그인 정보를 다시 확인해주세요.', 'member12346', 'wmsAdmin'),
('2023-04-05', 1, '입고 신청은 어디서 하나요?', 'DONE', '2023-04-06', '입고 요청 메뉴에서 신청 가능합니다.', 'member12347', 'wmsAdmin'),
('2023-06-15', 3, '재고가 잘못 등록된 것 같습니다.', 'PENDING', NULL, NULL, 'member12348', NULL),
('2023-08-01', 2, '출고 배송 추적은 어떻게 하나요?', 'DONE', '2023-08-02', '출고 내역 조회에서 확인 가능합니다.', 'member12350', 'wmsAdmin'),
('2023-09-20', 5, '앱이 자주 멈춥니다.', 'PENDING', NULL, NULL, 'member12351', NULL),
('2023-11-05', 6, '기타 문의사항입니다.', 'DONE', '2023-11-06', '문의 감사합니다. 내용 확인 후 연락드리겠습니다.', 'member12353', 'wmsAdmin'),
('2024-01-15', 1, '입고 지연 문의', 'PENDING', NULL, NULL, 'member12354', NULL);
