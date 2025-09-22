## users
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('admin01', '승인', 'admin_1234!', '관리자1', '010-1234-5678', 'admin1@coffeewms.com', NULL,
        '서울시 서초구', '2022-01-01', 'manager');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member01', '승인', 'pass1234!', '김철수', '010-9876-5432', 'chulsoo.kim@company.com',
        '123456789012', '서울시 강남구', '2023-03-10', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member02', '대기', 'password4!', '이영희', '010-1111-2222', 'younghee.lee@company.com',
        '234567890123', '경기도 성남시', '2023-04-20', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('manager01', '승인', 'mgr_pass!', '박민준', '010-3333-4444', 'minjun.park@coffeewms.com', NULL,
        '경기도 수원시', '2022-05-15', 'manager');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member03', '승인', 'mem_secure!', '최수정', '010-5555-6666', 'sujeong.choi@company.com',
        '345678901234', '부산시 해운대구', '2023-06-01', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('manager02', '승인', 'mgr_pass2!', '정수빈', '010-7777-8888', 'subin.jung@coffeewms.com', NULL,
        '대구시 수성구', '2022-08-22', 'manager');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member04', '승인', 'coffee_pw!', '한지민', '010-9999-0000', 'jim.han@company.com',
        '456789012345', '인천시 연수구', '2023-07-15', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member05', '대기', 'beans_pw!', '강동우', '010-1010-2020', 'dongwoo.kang@company.com',
        '567890123456', '대전시 서구', '2023-09-05', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('manager03', '승인', 'mgr_pass3!', '오서준', '010-2020-3030', 'seojoon.oh@coffeewms.com', NULL,
        '광주시 북구', '2023-02-18', 'manager');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member06', '승인', 'espresso_pw!', '김유진', '010-3030-4040', 'yujin.kim@company.com',
        '678901234567', '울산시 남구', '2023-10-10', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('manager04', '승인', 'mgr_pass4!', '이지은', '010-4040-5050', 'jieun.lee@coffeewms.com', NULL,
        '제주시 연동', '2023-04-05', 'manager');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member07', '승인', 'latte_pw!', '박재현', '010-5050-6060', 'jaehyun.park@company.com',
        '789012345678', '강원도 춘천시', '2023-11-20', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('manager05', '승인', 'mgr_pass5!', '김준호', '010-6060-7070', 'juno.kim@coffeewms.com', NULL,
        '충청북도 청주시', '2023-07-01', 'manager');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member08', '대기', 'americano_pw!', '이서영', '010-7070-8080', 'seoyoung.lee@company.com',
        '890123456789', '전라북도 전주시', '2024-01-01', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('manager06', '승인', 'mgr_pass6!', '최예은', '010-8080-9090', 'yeeun.choi@coffeewms.com', NULL,
        '경상북도 구미시', '2023-09-10', 'manager');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member09', '승인', 'moca_pw!', '정우성', '010-9090-1010', 'woosung.jung@company.com',
        '901234567890', '경기도 부천시', '2024-02-15', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('manager07', '승인', 'mgr_pass7!', '강민수', '010-1122-3344', 'minsu.kang@coffeewms.com', NULL,
        '경기도 용인시', '2024-01-20', 'manager');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member10', '승인', 'mocha_pw!', '윤지수', '010-2233-4455', 'jisoo.yoon@company.com',
        '012345678901', '경기도 고양시', '2024-03-01', 'member');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('manager08', '승인', 'mgr_pass8!', '신동현', '010-4455-6677', 'donghyun.shin@coffeewms.com',
        NULL, '인천시 남동구', '2024-03-10', 'manager');
INSERT INTO `users` (`user_id`, `user_approval`, `user_pwd`, `user_name`, `user_phone`,
                     `user_email`, `user_company_code`, `user_address`, `user_join_date`,
                     `user_type`)
VALUES ('member11', '대기', 'vanilla_pw!', '서민지', '010-5566-7788', 'minji.seo@company.com',
        '123400000000', '서울시 송파구', '2024-04-05', 'member');
--
## members
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member1', 'pass1!', '빈스커피', '010-1111-2222', 'beans@co.kr', '111222333444', '서울시 강남구',
        TRUE, '2023-01-01', '2025-12-31');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member2', 'pass2!', '로스터즈', '010-3333-4444', 'roasters@co.kr', '222333444555', '경기도 성남시',
        TRUE, '2023-02-15', '2026-02-14');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member3', 'pass3!', '카페원두', '010-5555-6666', 'cafe@co.kr', '333444555666', '부산시 해운대구',
        FALSE, '2023-03-20', '2026-03-19');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member4', 'pass4!', '더치랩', '010-7777-8888', 'dutch@co.kr', '444555666777', '대구시 중구', TRUE,
        '2023-04-10', '2026-04-09');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member5', 'pass5!', '아로마커피', '010-9999-0000', 'aroma@co.kr', '555666777888', '인천시 연수구',
        TRUE, '2023-05-05', '2026-05-04');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member6', 'pass6!', '커피나무', '010-1234-5678', 'tree@co.kr', '666777888999', '광주시 북구', FALSE,
        '2023-06-12', '2026-06-11');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member7', 'pass7!', '블랙빈', '010-2345-6789', 'blackbean@co.kr', '777888999000', '대전시 서구',
        TRUE, '2023-07-20', '2026-07-19');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member8', 'pass8!', '그라인더', '010-3456-7890', 'grinder@co.kr', '888999000111', '울산시 남구',
        TRUE, '2023-08-08', '2026-08-07');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member9', 'pass9!', '에스프레소', '010-4567-8901', 'espresso@co.kr', '999000111222', '제주시 연동',
        FALSE, '2023-09-01', '2026-08-31');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member10', 'pass10!', '핸드드립', '010-5678-9012', 'handdrip@co.kr', '101112131415', '강원도 춘천시',
        TRUE, '2023-10-15', '2026-10-14');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member11', 'pass11!', '콜드브루', '010-6789-0123', 'coldbrew@co.kr', '112233445566',
        '충청북도 청주시', FALSE, '2023-11-03', '2026-11-02');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member12', 'pass12!', '아메리카노', '010-7890-1234', 'americano@co.kr', '123456789000',
        '전라북도 전주시', TRUE, '2023-12-25', '2026-12-24');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member13', 'pass13!', '카페라떼', '010-8901-2345', 'latte@co.kr', '134567890111', '경상북도 구미시',
        TRUE, '2024-01-10', '2027-01-09');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member14', 'pass14!', '카푸치노', '010-9012-3456', 'cappuccino@co.kr', '145678901222',
        '경기도 부천시', FALSE, '2024-02-05', '2027-02-04');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member15', 'pass15!', '모카포트', '010-0123-4567', 'mocha@co.kr', '156789012333', '경기도 용인시',
        TRUE, '2024-03-01', '2027-02-28');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member16', 'pass16!', '에티오피아', '010-1234-0000', 'ethiopia@co.kr', '167890123444',
        '경기도 고양시', TRUE, '2024-03-15', '2027-03-14');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member17', 'pass17!', '케냐', '010-2345-1111', 'kenya@co.kr', '178901234555', '인천시 남동구',
        FALSE, '2024-04-01', '2027-03-31');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member18', 'pass18!', '콜롬비아', '010-3456-2222', 'colombia@co.kr', '189012345666', '서울시 송파구',
        TRUE, '2024-04-10', '2027-04-09');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member19', 'pass19!', '브라질', '010-4567-3333', 'brazil@co.kr', '190123456777', '서울시 강동구',
        TRUE, '2024-04-20', '2027-04-19');
INSERT INTO `members` (`member_id`, `member_pwd`, `member_company_name`, `member_phone`,
                       `member_email`, `member_company_code`, `member_address`, `member_login`,
                       `member_start_date`, `member_expired_date`)
VALUES ('member20', 'pass20!', '과테말라', '010-5678-4444', 'guatemala@co.kr', '201234567888',
        '경기도 하남시', TRUE, '2024-05-01', '2027-04-30');
--
## managers
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager1', 'mpass1!', '이민혁', '010-1234-5678', 'minhyuk.lee@coffeewms.com', TRUE,
        '2022-01-01', '총괄매니저', NULL);
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager2', 'mpass2!', '김지영', '010-2345-6789', 'jiyoung.kim@coffeewms.com', TRUE,
        '2022-03-15', '팀장', 'manager1');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager3', 'mpass3!', '박서준', '010-3456-7890', 'seojoon.park@coffeewms.com', FALSE,
        '2022-06-20', '팀장', 'manager1');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager4', 'mpass4!', '이지훈', '010-4567-8901', 'jihun.lee@coffeewms.com', TRUE,
        '2022-09-10', '매니저', 'manager2');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager5', 'mpass5!', '한윤아', '010-5678-9012', 'yuna.han@coffeewms.com', TRUE, '2022-12-05',
        '매니저', 'manager2');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager6', 'mpass6!', '최준호', '010-6789-0123', 'junho.choi@coffeewms.com', FALSE,
        '2023-02-28', '팀원', 'manager4');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager7', 'mpass7!', '강동현', '010-7890-1234', 'donghyun.kang@coffeewms.com', TRUE,
        '2023-04-18', '팀원', 'manager4');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager8', 'mpass8!', '오수민', '010-8901-2345', 'sumin.oh@coffeewms.com', TRUE, '2023-06-30',
        '팀원', 'manager5');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager9', 'mpass9!', '윤서현', '010-9012-3456', 'seohyun.yun@coffeewms.com', FALSE,
        '2023-08-14', '팀원', 'manager5');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager10', 'mpass10!', '정현우', '010-0123-4567', 'hyunwoo.jung@coffeewms.com', TRUE,
        '2023-10-01', '인턴', 'manager6');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager11', 'mpass11!', '권유리', '010-1122-3344', 'yuri.kwon@coffeewms.com', TRUE,
        '2023-11-20', '인턴', 'manager6');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager12', 'mpass12!', '송민재', '010-2233-4455', 'minjae.song@coffeewms.com', FALSE,
        '2024-01-05', '팀원', 'manager7');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager13', 'mpass13!', '서지훈', '010-3344-5566', 'jihun.seo@coffeewms.com', TRUE,
        '2024-02-10', '팀원', 'manager7');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager14', 'mpass14!', '황유나', '010-4455-6677', 'yuna.hwang@coffeewms.com', TRUE,
        '2024-03-25', '매니저', 'manager8');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager15', 'mpass15!', '조현민', '010-5566-7788', 'hyunmin.jo@coffeewms.com', FALSE,
        '2024-04-05', '팀원', 'manager8');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager16', 'mpass16!', '김도윤', '010-6677-8899', 'doyun.kim@coffeewms.com', TRUE,
        '2024-04-18', '팀원', 'manager9');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager17', 'mpass17!', '이서연', '010-7788-9900', 'seoyeon.lee@coffeewms.com', TRUE,
        '2024-05-01', '인턴', 'manager9');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager18', 'mpass18!', '장민우', '010-8899-0011', 'minwoo.jang@coffeewms.com', FALSE,
        '2024-05-10', '인턴', 'manager10');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager19', 'mpass19!', '임수아', '010-9900-1122', 'sua.im@coffeewms.com', TRUE, '2024-06-01',
        '팀원', 'manager10');
INSERT INTO `managers` (`manager_id`, `manager_pwd`, `manager_name`, `manager_phone`,
                        `manager_email`, `manager_login`, `manager_hire_date`, `manager_position`,
                        `manager_super_id`)
VALUES ('manager20', 'mpass20!', '한결', '010-0011-2233', 'gyeol.han@coffeewms.com', TRUE,
        '2024-06-15', '팀원', 'manager11');
--
## cargoes
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000001', '강남 본사 창고', '서울시 강남구 역삼동', 'A', 500, 1000, 750);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000002', '성남 지사 창고', '경기도 성남시 분당구', 'A', 800, 1500, 1200);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000003', '부산 창고', '부산시 해운대구 센텀시티', 'B', 600, 1200, 900);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000004', '대구 창고', '대구시 달서구', 'B', 400, 800, 600);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000005', '인천 공항 창고', '인천시 중구 운서동', 'A', 1200, 2500, 2000);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000006', '대전 창고', '대전시 유성구', 'C', 300, 600, 450);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000007', '광주 창고', '광주시 광산구', 'C', 500, 1000, 800);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000008', '울산 창고', '울산시 북구', 'B', 700, 1400, 1000);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000009', '제주 창고', '제주시 한림읍', 'A', 200, 400, 300);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000010', '서울 용산 창고', '서울시 용산구', 'B', 450, 900, 700);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000011', '수원 지사 창고', '경기도 수원시 영통구', 'A', 550, 1100, 850);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000012', '용인 지사 창고', '경기도 용인시 기흥구', 'B', 650, 1300, 1050);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000013', '춘천 창고', '강원도 춘천시', 'C', 350, 700, 500);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000014', '청주 창고', '충청북도 청주시 흥덕구', 'A', 850, 1700, 1300);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000015', '전주 창고', '전라북도 전주시 완산구', 'B', 750, 1500, 1150);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000016', '구미 창고', '경상북도 구미시', 'A', 950, 1900, 1500);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000017', '포항 창고', '경상북도 포항시 남구', 'B', 500, 1000, 750);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000018', '김해 창고', '경상남도 김해시', 'C', 400, 800, 600);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000019', '양산 창고', '경상남도 양산시', 'A', 600, 1200, 900);
INSERT INTO `cargoes` (`cargo_id`, `cargo_name`, `cargo_address`, `cargo_grade`, `cargo_field`,
                       `cargo_total_capa`, `cargo_use_capa`)
VALUES ('CGO0000020', '서울 강서 창고', '서울시 강서구', 'B', 700, 1400, 1000);
--
## location_places
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000001', 'A-ZONE', 'A구역', 'RACK-01', '1번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000002', 'A-ZONE', 'A구역', 'RACK-01', '1번랙', 'CELL-02', '2번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000003', 'A-ZONE', 'A구역', 'RACK-02', '2번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000004', 'B-ZONE', 'B구역', 'RACK-03', '3번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000005', 'B-ZONE', 'B구역', 'RACK-03', '3번랙', 'CELL-02', '2번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000006', 'C-ZONE', 'C구역', 'RACK-04', '4번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000007', 'C-ZONE', 'C구역', 'RACK-05', '5번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000008', 'D-ZONE', 'D구역', 'RACK-06', '6번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000009', 'D-ZONE', 'D구역', 'RACK-06', '6번랙', 'CELL-02', '2번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000010', 'E-ZONE', 'E구역', 'RACK-07', '7번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000011', 'E-ZONE', 'E구역', 'RACK-08', '8번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000012', 'F-ZONE', 'F구역', 'RACK-09', '9번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000013', 'F-ZONE', 'F구역', 'RACK-09', '9번랙', 'CELL-02', '2번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000014', 'G-ZONE', 'G구역', 'RACK-10', '10번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000015', 'G-ZONE', 'G구역', 'RACK-10', '10번랙', 'CELL-02', '2번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000016', 'H-ZONE', 'H구역', 'RACK-11', '11번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000017', 'H-ZONE', 'H구역', 'RACK-11', '11번랙', 'CELL-02', '2번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000018', 'I-ZONE', 'I구역', 'RACK-12', '12번랙', 'CELL-01', '1번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000019', 'I-ZONE', 'I구역', 'RACK-12', '12번랙', 'CELL-02', '2번셀');
INSERT INTO `location_places` (`location_place_id`, `zone_id`, `zone_name`, `rack_id`, `rack_name`,
                               `cell_id`, `cell_name`)
VALUES ('LCP0000020', 'J-ZONE', 'J구역', 'RACK-13', '13번랙', 'CELL-01', '1번셀');
--
## coffees
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000001', '에티오피아 예가체프', 'G1', 5, 3, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000002', '콜롬비아 수프리모', 'Supremo', 3, 4, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000003', '케냐 AA', 'AA', 4, 4, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000004', '브라질 산토스', 'Santos', 2, 5, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000005', '인도네시아 만델링', 'G1', 2, 5, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000006', '과테말라 안티구아', 'SHB', 4, 3, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000007', '코스타리카 타라수', 'SHB', 4, 3, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000008', '파나마 게이샤', 'Geisha', 5, 2, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000009', '하와이 코나', 'Extra Fancy', 3, 4, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000010', '베트남 로부스타', 'G1', 1, 5, 'Robusta');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000011', '온두라스 SHG', 'SHG', 3, 3, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000012', '엘살바도르 SHG', 'SHG', 4, 3, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000013', '탄자니아 AA', 'AA', 4, 3, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000014', '예멘 모카 마타리', 'Mocha', 5, 4, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000015', '자메이카 블루마운틴', 'No.1', 4, 3, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000016', '멕시코 알투라', 'Altura', 3, 4, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000017', '인도 몬순 말라바', 'Monsooned', 2, 5, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000018', '쿠바 크리스탈 마운틴', 'Cristal', 4, 3, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000019', '니카라과 SHG', 'SHG', 3, 4, 'Arabica');
INSERT INTO `coffees` (`coffee_id`, `coffee_name`, `coffee_grade`, `acidity`, `body`, `coffee_type`)
VALUES ('COF00000020', '케냐 PB', 'PB', 4, 3, 'Arabica');
--
## products
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000001', 'CS', '에티오피아 예가체프 생두', 'ETH', 25000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000002', 'CS', '콜롬비아 수프리모 생두', 'COL', 22000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000003', 'CP', '케냐 AA 로스팅 원두', 'KEN', 28000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000004', 'CP', '브라질 산토스 로스팅 원두', 'BRA', 18000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000005', 'CS', '인도네시아 만델링 생두', 'IDN', 20000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000006', 'CS', '과테말라 안티구아 생두', 'GTM', 23000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000007', 'CP', '콜롬비아 디카페인 원두', 'COL', 26000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000008', 'CP', '파나마 게이샤 로스팅 원두', 'PAN', 55000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000009', 'CS', '하와이 코나 생두', 'USA', 60000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000010', 'CS', '베트남 로부스타 생두', 'VNM', 15000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000011', 'CP', '온두라스 로스팅 원두', 'HND', 21000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000012', 'CP', '엘살바도르 로스팅 원두', 'SLV', 24000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000013', 'CS', '탄자니아 AA 생두', 'TZA', 27000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000014', 'CS', '예멘 모카 마타리 생두', 'YEM', 35000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000015', 'CP', '자메이카 블루마운틴 로스팅 원두', 'JAM', 80000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000016', 'CS', '멕시코 알투라 생두', 'MEX', 19000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000017', 'CP', '인도 몬순 말라바 로스팅 원두', 'IND', 22000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000018', 'CS', '쿠바 크리스탈 마운틴 생두', 'CUB', 30000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000019', 'CP', '니카라과 로스팅 원두', 'NIC', 23000);
INSERT INTO `products` (`product_id`, `product_group`, `product_name`, `product_origin`,
                        `product_price`)
VALUES ('PRO00000020', 'CP', '케냐 PB 로스팅 원두', 'KEN', 26000);
--
## inquiry_category
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (1, '입고문의');
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (2, '출고문의');
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (3, '재고문의');
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (4, '배송문의');
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (5, '시스템문의');
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (6, '결제/수수료');
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (7, '계정/로그인');
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (8, '상품정보');
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (9, '제휴문의');
INSERT INTO `inquiry_category` (`inquiry_category_id`, `inquiry_category_name`)
VALUES (10, '기타');
--
## faq_category
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (1, '회원가입/탈퇴');
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (2, '로그인/계정');
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (3, '입고 절차');
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (4, '출고 절차');
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (5, '재고 관리');
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (6, '배송 및 운송');
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (7, '이용 요금');
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (8, '상품 정보');
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (9, '고객센터');
INSERT INTO `faq_category` (`faq_category_id`, `faq_category_name`)
VALUES (10, '기타');


######### 2단계 :  1단계 테이블을 참조하는 테이블

## notice
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (1, '시스템 점검 안내', '시스템 안정화를 위한 정기 점검이 예정되어 있습니다. 점검 시간 동안 서비스 이용이 중단됩니다.', '2024-05-10', TRUE,
        'manager1');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (2, '입출고 수수료 변경 안내', '2024년 6월 1일부터 입출고 수수료가 변경됩니다. 자세한 내용은 공지사항을 참고해주세요.', '2024-04-25',
        TRUE, 'manager2');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (3, '신규 창고 오픈 안내', '서울 강서구에 새로운 창고가 오픈합니다. 보다 넓고 편리한 공간을 제공합니다.', '2024-05-01', TRUE,
        'manager3');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (4, '입고 서류 양식 변경', '입고 신청 시 제출해야 하는 서류 양식이 변경되었습니다. 새로운 양식으로 제출 부탁드립니다.', '2024-04-15',
        FALSE, 'manager4');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (5, '개인정보처리방침 개정 안내', '더 나은 서비스 제공을 위해 개인정보처리방침이 개정되었습니다.', '2024-03-20', FALSE, 'manager5');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (6, '시스템 업데이트 완료', '입출고 요청 UI가 개선된 시스템 업데이트가 완료되었습니다.', '2024-03-05', FALSE, 'manager6');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (7, '제주 창고 이용 가능 상품 확대', '제주 창고에 보관 가능한 상품 품목이 확대되었습니다.', '2024-02-18', FALSE, 'manager7');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (8, '원두 상품 등록 절차 간소화', '상품 등록 절차를 간소화하여 회원 편의성을 높였습니다.', '2024-01-30', FALSE, 'manager8');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (9, '2024년 설날 연휴 업무 안내', '설 연휴 기간 동안의 업무 일정에 대해 안내드립니다.', '2024-01-10', FALSE, 'manager9');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (10, '긴급 서버 점검 안내', '갑작스러운 서버 오류로 인해 긴급 점검을 진행합니다.', '2024-01-05', TRUE, 'manager10');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (11, '출고 프로세스 변경 안내', '출고 처리 시간이 단축되었습니다.', '2023-12-20', FALSE, 'manager11');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (12, '12월 재고 실사 일정', '12월 정기 재고 실사 일정을 공지합니다.', '2023-11-25', FALSE, 'manager12');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (13, '보안 강화 업데이트', '계정 보안 강화를 위한 업데이트가 진행됩니다.', '2023-11-10', FALSE, 'manager13');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (14, '문의 게시판 리뉴얼', '문의 게시판 기능이 개선되었습니다.', '2023-10-01', FALSE, 'manager14');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (15, '이용 약관 개정 안내', '이용 약관이 개정되었으니 확인해 주세요.', '2023-09-15', FALSE, 'manager15');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (16, '추석 연휴 배송 안내', '추석 연휴 기간 동안의 배송 일정에 대해 안내드립니다.', '2023-09-01', FALSE, 'manager16');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (17, '새로운 서비스 도입 예정', '새로운 부가 서비스 도입을 준비하고 있습니다.', '2023-08-20', FALSE, 'manager17');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (18, '창고 보관 환경 개선', '온습도 조절 기능을 강화하여 상품 보관 환경이 개선되었습니다.', '2023-08-01', FALSE, 'manager18');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (19, '고객 만족도 조사', '서비스 개선을 위한 고객 만족도 조사를 진행합니다.', '2023-07-10', FALSE, 'manager19');
INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`, `notice_fixed`,
                      `manager_id`)
VALUES (20, '원두 품목 추가 공지', '새로운 원두 품목이 추가되었습니다.', '2023-06-25', FALSE, 'manager20');

## 3단계: 두 개 이상의 테이블을 참조하는 테이블
## faq
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (1, 1, '회원 탈퇴는 어떻게 하나요?', '마이페이지에서 회원 탈퇴를 신청할 수 있습니다. 탈퇴 후 30일간 재가입이 불가능합니다.', '2024-05-15',
        'manager1');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (2, 2, '비밀번호를 잊어버렸어요.', '로그인 페이지에서 비밀번호 찾기를 통해 재설정할 수 있습니다.', '2024-05-10', 'manager2');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (3, 3, '입고 신청 절차가 어떻게 되나요?', '입고 요청서 작성 후 승인되면 상품을 창고로 보내주시면 됩니다.', '2024-05-08',
        'manager3');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (4, 4, '출고 신청 후 언제 배송되나요?', '출고 승인 후 1~3일 내에 출고가 완료되며 배송이 시작됩니다.', '2024-05-05', 'manager4');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (5, 5, '현재 재고 현황을 어떻게 확인하나요?', '재고 현황 메뉴에서 실시간 재고를 확인할 수 있습니다.', '2024-05-02', 'manager5');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (6, 6, '배송 요금은 어떻게 산정되나요?', '배송 요금은 무게와 거리에 따라 산정됩니다.', '2024-04-25', 'manager6');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (7, 7, '보관 수수료는 어떻게 계산되나요?', '보관 수수료는 상품의 부피와 보관 기간에 따라 계산됩니다.', '2024-04-20', 'manager7');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (8, 8, '상품 등록 시 유의사항이 있나요?', '상품 정보는 정확하게 기입해야 하며, 허위 정보 기입 시 제재가 있을 수 있습니다.', '2024-04-18',
        'manager8');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (9, 9, '고객센터 운영 시간은 어떻게 되나요?', '평일 오전 9시부터 오후 6시까지 운영됩니다.', '2024-04-15', 'manager9');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (10, 10, '로스팅을 직접 해도 되나요?', '저희 창고는 보관 및 유통 서비스만 제공하며, 로스팅은 직접 하셔야 합니다.', '2024-04-10',
        'manager10');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (11, 2, '회원 정보를 수정하고 싶어요.', '마이페이지에서 회원 정보를 수정할 수 있습니다.', '2024-04-05', 'manager11');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (12, 3, '입고 승인이 지연되는 이유는 무엇인가요?', '요청 서류 미비, 상품 정보 오류 등의 이유로 승인이 지연될 수 있습니다.', '2024-03-30',
        'manager12');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (13, 4, '출고 취소는 가능한가요?', '출고 승인 전에는 취소가 가능하지만, 출고가 진행된 이후에는 취소가 어렵습니다.', '2024-03-25',
        'manager13');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (14, 5, '재고가 부족하면 어떻게 되나요?', '재고가 부족한 경우, 출고 요청이 거절될 수 있습니다.', '2024-03-20', 'manager14');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (15, 6, '배송 추적은 가능한가요?', '출고 후 송장 번호를 통해 배송 추적이 가능합니다.', '2024-03-15', 'manager15');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (16, 7, '월별 보관료 청구는 어떻게 되나요?', '매월 말일 기준으로 보관료가 계산되어 익월 초에 청구됩니다.', '2024-03-10',
        'manager16');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (17, 8, '유통기한 관리는 어떻게 이루어지나요?', '입고된 상품의 유통기한을 시스템에 등록하시면, 유통기한이 임박한 상품에 대해 알림을 드립니다.',
        '2024-03-05', 'manager17');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (18, 9, 'ARS 상담이 가능한가요?', '현재 ARS 상담은 제공하지 않으며, 온라인 문의를 통해 상담이 가능합니다.', '2024-03-01',
        'manager18');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (19, 10, '가공품도 보관이 가능한가요?', '네, 로스팅된 원두 등 가공된 커피 상품도 보관이 가능합니다.', '2024-02-25', 'manager19');
INSERT INTO `faq` (`faq_id`, `faq_category_id`, `faq_question`, `faq_reply`, `faq_date`,
                   `manager_id`)
VALUES (20, 2, '아이디를 잊어버렸어요.', '가입 시 등록한 이메일로 아이디 찾기가 가능합니다.', '2024-02-20', 'manager20');
--
## inbound_request
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240520001', 'member1', 'manager4', TRUE, '2024-05-20', '빈스커피 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240520002', 'member2', 'manager5', TRUE, '2024-05-20', '로스터즈 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240519001', 'member3', 'manager6', FALSE, '2024-05-19', '카페원두 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240518001', 'member4', 'manager7', TRUE, '2024-05-18', '더치랩 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240517001', 'member5', 'manager8', TRUE, '2024-05-17', '아로마커피 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240516001', 'member6', 'manager9', FALSE, '2024-05-16', '커피나무 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240515001', 'member7', 'manager10', TRUE, '2024-05-15', '블랙빈 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240514001', 'member8', 'manager11', TRUE, '2024-05-14', '그라인더 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240513001', 'member9', 'manager12', FALSE, '2024-05-13', '에스프레소 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240512001', 'member10', 'manager13', TRUE, '2024-05-12', '핸드드립 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240511001', 'member11', 'manager14', FALSE, '2024-05-11', '콜드브루 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240510001', 'member12', 'manager15', TRUE, '2024-05-10', '아메리카노 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240509001', 'member13', 'manager16', TRUE, '2024-05-09', '카페라떼 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240508001', 'member14', 'manager17', FALSE, '2024-05-08', '카푸치노 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240507001', 'member15', 'manager18', TRUE, '2024-05-07', '모카포트 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240506001', 'member16', 'manager19', TRUE, '2024-05-06', '에티오피아 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240505001', 'member17', 'manager20', FALSE, '2024-05-05', '케냐 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240504001', 'member18', 'manager1', TRUE, '2024-05-04', '콜롬비아 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240503001', 'member19', 'manager2', TRUE, '2024-05-03', '브라질 입고 요청서');
INSERT INTO `inbound_request` (`inbound_request_id`, `member_id`, `manager_id`,
                               `inbound_request_approved`, `inbound_date`, `inbound_receipt`)
VALUES ('INB240502001', 'member20', 'manager3', TRUE, '2024-05-02', '과테말라 입고 요청서');
--
## outbound_request
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240520001', 'member1', 'manager1', TRUE, '2024-05-20', '빈스커피 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240519001', 'member2', 'manager2', TRUE, '2024-05-19', '로스터즈 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240518001', 'member3', 'manager3', FALSE, '2024-05-18', '카페원두 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240517001', 'member4', 'manager4', TRUE, '2024-05-17', '더치랩 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240516001', 'member5', 'manager5', TRUE, '2024-05-16', '아로마커피 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240515001', 'member6', 'manager6', FALSE, '2024-05-15', '커피나무 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240514001', 'member7', 'manager7', TRUE, '2024-05-14', '블랙빈 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240513001', 'member8', 'manager8', TRUE, '2024-05-13', '그라인더 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240512001', 'member9', 'manager9', FALSE, '2024-05-12', '에스프레소 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240511001', 'member10', 'manager10', TRUE, '2024-05-11', '핸드드립 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240510001', 'member11', 'manager11', FALSE, '2024-05-10', '콜드브루 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240509001', 'member12', 'manager12', TRUE, '2024-05-09', '아메리카노 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240508001', 'member13', 'manager13', TRUE, '2024-05-08', '카페라떼 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240507001', 'member14', 'manager14', FALSE, '2024-05-07', '카푸치노 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240506001', 'member15', 'manager15', TRUE, '2024-05-06', '모카포트 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240505001', 'member16', 'manager16', TRUE, '2024-05-05', '에티오피아 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240504001', 'member17', 'manager17', FALSE, '2024-05-04', '케냐 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240503001', 'member18', 'manager18', TRUE, '2024-05-03', '콜롬비아 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240502001', 'member19', 'manager19', TRUE, '2024-05-02', '브라질 출고 요청서');
INSERT INTO `outbound_request` (`outbound_request_id`, `member_id`, `manager_id`,
                                `outbound_request_approved`, `outbound_date`, `outbound_order`)
VALUES ('OUT240501001', 'member20', 'manager20', TRUE, '2024-05-01', '과테말라 출고 요청서');
--
## cargo_management
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager1', 'CGO0000001');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager1', 'CGO0000002');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager2', 'CGO0000003');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager2', 'CGO0000004');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager3', 'CGO0000005');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager3', 'CGO0000006');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager4', 'CGO0000007');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager4', 'CGO0000008');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager5', 'CGO0000009');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager5', 'CGO0000010');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager6', 'CGO0000011');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager6', 'CGO0000012');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager7', 'CGO0000013');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager7', 'CGO0000014');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager8', 'CGO0000015');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager8', 'CGO0000016');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager9', 'CGO0000017');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager9', 'CGO0000018');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager10', 'CGO0000019');
INSERT INTO `cargo_management` (`manager_id`, `cargo_id`)
VALUES ('manager10', 'CGO0000020');
--
## locations
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000001', 'CGO0000001', 'LCP0000001');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000002', 'CGO0000001', 'LCP0000002');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000003', 'CGO0000002', 'LCP0000003');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000004', 'CGO0000002', 'LCP0000004');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000005', 'CGO0000003', 'LCP0000005');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000006', 'CGO0000003', 'LCP0000006');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000007', 'CGO0000004', 'LCP0000007');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000008', 'CGO0000004', 'LCP0000008');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000009', 'CGO0000005', 'LCP0000009');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000010', 'CGO0000005', 'LCP0000010');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000011', 'CGO0000006', 'LCP0000011');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000012', 'CGO0000006', 'LCP0000012');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000013', 'CGO0000007', 'LCP0000013');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000014', 'CGO0000007', 'LCP0000014');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000015', 'CGO0000008', 'LCP0000015');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000016', 'CGO0000008', 'LCP0000016');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000017', 'CGO0000009', 'LCP0000017');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000018', 'CGO0000009', 'LCP0000018');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000019', 'CGO0000010', 'LCP0000019');
INSERT INTO `locations` (`location_id`, `cargo_id`, `location_place_id`)
VALUES ('LOC000000020', 'CGO0000010', 'LCP0000020');
--
## inventory
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000001', 'PRO00000001', 'LCP0000001', 500);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000002', 'PRO00000002', 'LCP0000002', 450);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000003', 'PRO00000003', 'LCP0000003', 300);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000004', 'PRO00000004', 'LCP0000004', 250);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000005', 'PRO00000005', 'LCP0000005', 600);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000006', 'PRO00000006', 'LCP0000006', 400);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000007', 'PRO00000007', 'LCP0000007', 200);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000008', 'PRO00000008', 'LCP0000008', 150);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000009', 'PRO00000009', 'LCP0000009', 100);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000010', 'PRO00000010', 'LCP0000010', 550);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000011', 'PRO00000011', 'LCP0000011', 320);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000012', 'PRO00000012', 'LCP0000012', 480);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000013', 'PRO00000013', 'LCP0000013', 290);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000014', 'PRO00000014', 'LCP0000014', 180);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000015', 'PRO00000015', 'LCP0000015', 80);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000016', 'PRO00000016', 'LCP0000016', 450);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000017', 'PRO00000017', 'LCP0000017', 350);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000018', 'PRO00000018', 'LCP0000018', 210);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000019', 'PRO00000019', 'LCP0000019', 310);
INSERT INTO `inventory` (`inventory_id`, `product_id`, `location_place_id`, `inventory_quantity`)
VALUES ('INV000000020', 'PRO00000020', 'LCP0000020', 270);
--
## coffees_processed
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000003', 'COF00000003', 'N', '2024-05-18');
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000004', 'COF00000004', 'N', '2024-05-17');
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000007', 'COF00000002', 'Y', '2024-05-15');
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000008', 'COF00000008', 'N', '2024-05-14');
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000011', 'COF00000011', 'N', '2024-05-12');
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000012', 'COF00000012', 'N', '2024-05-10');
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000015', 'COF00000015', 'N', '2024-05-08');
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000017', 'COF00000017', 'N', '2024-05-06');
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000019', 'COF00000019', 'N', '2024-05-04');
INSERT INTO `coffees_processed` (`product_id`, `coffee_id`, `decaf`, `roasted_date`)
VALUES ('PRO00000020', 'COF00000020', 'N', '2024-05-02');


## 4단계: 3단계 테이블을 참조하는 테이블
## inbound_request_items
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (1, 'INB240520001', 'member1', 'manager4', 'PRO00000001', 500);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (2, 'INB240520002', 'member2', 'manager5', 'PRO00000003', 300);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (3, 'INB240519001', 'member3', 'manager6', 'PRO00000005', 200);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (4, 'INB240518001', 'member4', 'manager7', 'PRO00000006', 400);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (5, 'INB240517001', 'member5', 'manager8', 'PRO00000009', 100);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (6, 'INB240516001', 'member6', 'manager9', 'PRO00000010', 500);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (7, 'INB240515001', 'member7', 'manager10', 'PRO00000013', 250);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (8, 'INB240514001', 'member8', 'manager11', 'PRO00000014', 150);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (9, 'INB240513001', 'member9', 'manager12', 'PRO00000016', 300);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (10, 'INB240512001', 'member10', 'manager13', 'PRO00000018', 200);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (11, 'INB240510001', 'member12', 'manager15', 'PRO00000002', 450);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (12, 'INB240509001', 'member13', 'manager16', 'PRO00000004', 350);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (13, 'INB240507001', 'member15', 'manager18', 'PRO00000007', 180);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (14, 'INB240506001', 'member16', 'manager19', 'PRO00000008', 120);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (15, 'INB240504001', 'member18', 'manager1', 'PRO00000011', 280);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (16, 'INB240503001', 'member19', 'manager2', 'PRO00000012', 320);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (17, 'INB240502001', 'member20', 'manager3', 'PRO00000015', 90);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (18, 'INB240504001', 'member18', 'manager1', 'PRO00000017', 250);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (19, 'INB240503001', 'member19', 'manager2', 'PRO00000019', 280);
INSERT INTO `inbound_request_items` (`inbound_request_item_id`, `inbound_request_id`, `member_id`,
                                     `manager_id`, `product_id`, `inbound_request_quantity`)
VALUES (20, 'INB240502001', 'member20', 'manager3', 'PRO00000020', 220);
--
## outbound_request_items
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (1, 'OUT240520001', 'member1', 'manager1', 'INV000000001', 50);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (2, 'OUT240519001', 'member2', 'manager2', 'INV000000003', 30);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (3, 'OUT240517001', 'member4', 'manager4', 'INV000000006', 40);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (4, 'OUT240516001', 'member5', 'manager5', 'INV000000007', 20);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (5, 'OUT240514001', 'member7', 'manager7', 'INV000000009', 10);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (6, 'OUT240513001', 'member8', 'manager8', 'INV000000010', 50);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (7, 'OUT240511001', 'member10', 'manager10', 'INV000000012', 45);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (8, 'OUT240509001', 'member12', 'manager12', 'INV000000014', 18);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (9, 'OUT240508001', 'member13', 'manager13', 'INV000000015', 8);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (10, 'OUT240506001', 'member15', 'manager15', 'INV000000017', 35);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (11, 'OUT240505001', 'member16', 'manager16', 'INV000000019', 31);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (12, 'OUT240503001', 'member18', 'manager18', 'INV000000002', 40);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (13, 'OUT240502001', 'member19', 'manager19', 'INV000000004', 25);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (14, 'OUT240501001', 'member20', 'manager20', 'INV000000005', 60);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (15, 'OUT240517001', 'member4', 'manager4', 'INV000000008', 15);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (16, 'OUT240514001', 'member7', 'manager7', 'INV000000011', 32);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (17, 'OUT240511001', 'member10', 'manager10', 'INV000000013', 29);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (18, 'OUT240509001', 'member12', 'manager12', 'INV000000016', 45);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (19, 'OUT240506001', 'member15', 'manager15', 'INV000000018', 21);
INSERT INTO `outbound_request_items` (`outbound_request_item_id`, `outbound_request_id`,
                                      `member_id`, `manager_id`, `inventory_id`,
                                      `outbound_request_quantity`)
VALUES (20, 'OUT240505001', 'member16', 'manager16', 'INV000000020', 27);
--
## due_diligence
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405200001', 'INV000000001', '500개 재고 확인 완료.', '2024-05-20', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405200002', 'INV000000002', '450개 재고 확인 완료.', '2024-05-20', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405190001', 'INV000000003', '300개 재고 확인 완료.', '2024-05-19', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405180001', 'INV000000004', '250개 재고 확인 완료.', '2024-05-18', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405170001', 'INV000000005', '600개 재고 확인 완료.', '2024-05-17', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405160001', 'INV000000006', '400개 재고 확인 완료.', '2024-05-16', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405150001', 'INV000000007', '200개 재고 확인 완료.', '2024-05-15', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405140001', 'INV000000008', '150개 재고 확인 완료.', '2024-05-14', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405130001', 'INV000000009', '100개 재고 확인 완료.', '2024-05-13', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405120001', 'INV000000010', '550개 재고 확인 완료.', '2024-05-12', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405110001', 'INV000000011', '320개 재고 확인 완료.', '2024-05-11', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405100001', 'INV000000012', '480개 재고 확인 완료.', '2024-05-10', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405090001', 'INV000000013', '290개 재고 확인 완료.', '2024-05-09', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405080001', 'INV000000014', '180개 재고 확인 완료.', '2024-05-08', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405070001', 'INV000000015', '80개 재고 확인 완료.', '2024-05-07', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405060001', 'INV000000016', '450개 재고 확인 완료.', '2024-05-06', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405050001', 'INV000000017', '350개 재고 확인 완료.', '2024-05-05', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405040001', 'INV000000018', '210개 재고 확인 완료.', '2024-05-04', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405030001', 'INV000000019', '310개 재고 확인 완료.', '2024-05-03', '승인');
INSERT INTO `due_diligence` (`due_diligence_id`, `inventory_id`, `due_diligence_log`,
                             `due_diligence_date`, `due_diligence_approval`)
VALUES ('DD2405020001', 'INV000000020', '270개 재고 확인 완료.', '2024-05-02', '승인');
--
## inquiry
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (1, '2024-05-20', 1, '새로운 상품 입고 절차에 대해 문의합니다.', 'DONE', '2024-05-21', '입고 절차는 FAQ를 참고해주세요.',
        'member1', 'manager1');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (2, '2024-05-19', 2, '출고 요청 상품의 배송 상황을 알고 싶습니다.', 'DONE', '2024-05-20',
        '송장 번호를 확인하여 배송 추적 부탁드립니다.', 'member2', 'manager2');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (3, '2024-05-18', 3, '재고가 부족한 것 같습니다. 확인 부탁드립니다.', 'PENDING', NULL, NULL, 'member3', NULL);
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (4, '2024-05-17', 4, '배송 요금이 생각보다 비싼데 재산정 가능한가요?', 'DONE', '2024-05-18',
        '배송 요금은 무게와 거리를 기준으로 자동 산정됩니다.', 'member4', 'manager3');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (5, '2024-05-16', 5, '로그인 시 오류가 발생합니다. 확인해주세요.', 'DONE', '2024-05-17',
        '캐시 삭제 후 다시 시도해 보시기 바랍니다.', 'member5', 'manager4');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (6, '2024-05-15', 6, '보관 수수료 청구 내역에 오류가 있는 것 같습니다.', 'PENDING', NULL, NULL, 'member6', NULL);
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (7, '2024-05-14', 7, '비밀번호 변경이 되지 않습니다.', 'DONE', '2024-05-15', '임시 비밀번호를 발급해 드렸습니다.',
        'member7', 'manager5');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (8, '2024-05-13', 8, '상품명 변경이 가능한가요?', 'DONE', '2024-05-14', '상품 정보 수정은 관리자에게 문의 부탁드립니다.',
        'member8', 'manager6');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (9, '2024-05-12', 9, '저희 회사의 원두를 창고에 보관하고 싶습니다.', 'DONE', '2024-05-13',
        '담당 매니저가 연락드릴 예정입니다.', 'member9', 'manager7');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (10, '2024-05-11', 10, '기타 문의: 창고 견학이 가능한가요?', 'DONE', '2024-05-12', '견학 일정은 별도로 조율이 필요합니다.',
        'member10', 'manager8');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (11, '2024-05-10', 1, '입고 신청이 반려되었습니다. 사유를 알고 싶습니다.', 'DONE', '2024-05-11',
        '첨부 서류가 누락되어 반려되었습니다.', 'member11', 'manager9');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (12, '2024-05-09', 2, '출고 요청을 취소하고 싶습니다.', 'DONE', '2024-05-10', '출고 승인 전이므로 취소가 완료되었습니다.',
        'member12', 'manager10');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (13, '2024-05-08', 3, '재고 실사가 필요한데 어떻게 신청하나요?', 'DONE', '2024-05-09', '재고 실사 신청 메뉴에서 가능합니다.',
        'member13', 'manager11');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (14, '2024-05-07', 4, '배송지 변경을 요청합니다.', 'PENDING', NULL, NULL, 'member14', NULL);
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (15, '2024-05-06', 5, '모바일 앱이 출시될 예정인가요?', 'DONE', '2024-05-07', '현재 계획에는 없지만 검토 중입니다.',
        'member15', 'manager12');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (16, '2024-05-05', 6, '세금계산서 발행이 가능한가요?', 'DONE', '2024-05-06', '네, 월별 정산 시 자동으로 발행됩니다.',
        'member16', 'manager13');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (17, '2024-05-04', 7, '회원정보를 수정하고 싶습니다.', 'DONE', '2024-05-05', '마이페이지에서 수정 가능합니다.',
        'member17', 'manager14');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (18, '2024-05-03', 8, '상품 사진을 추가하고 싶습니다.', 'PENDING', NULL, NULL, 'member18', NULL);
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (19, '2024-05-02', 9, '담당 매니저 연락처를 알고 싶습니다.', 'DONE', '2024-05-03', '담당 매니저의 정보를 전달해 드렸습니다.',
        'member19', 'manager15');
INSERT INTO `inquiry` (`inquiry_id`, `inquiry_date`, `inquiry_category_id`, `inquiry_content`,
                       `inquiry_status`, `reply_date`, `reply_content`, `member_id`, `manager_id`)
VALUES (20, '2024-05-01', 10, '제조사 정보 변경이 가능한가요?', 'DONE', '2024-05-02', '정보 변경은 관리자를 통해 가능합니다.',
        'member20', 'manager16');
