use railway;
# use testdb1;

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
