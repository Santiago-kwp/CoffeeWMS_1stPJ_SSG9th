use testdb1;

select * from inbound_request;

select * from coffees;

### 1. 회원의 입고 요청
DROP PROCEDURE IF EXISTS submit_member_inbound_request;



-- 입고 요청 및 상세 내역을 트랜잭션으로 처리하는 프로시저
-- c_inbound_request_id: 입고 요청 고유 ID
-- c_member_id: 요청을 하는 회원 ID
-- c_manager_id: 관리자 ID (두 테이블 모두에 사용)
-- c_request_items_json: 요청 상품 ID와 수량을 담은 JSON 배열 (예: '[{"coffee_id": "...", "quantity": ...}, ...]')
-- c_inbound_request_date: 회원이 요청한 입고 날짜
DELIMITER @@
CREATE PROCEDURE submit_member_inbound_request(
    IN c_inbound_request_id CHAR(12),
    IN c_member_id VARCHAR(15),
    IN c_manager_id VARCHAR(15),
    IN c_request_items_json TEXT,
    IN c_inbound_request_date DATE
)
BEGIN
    -- 에러 발생 시 롤백 처리
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    -- 트랜잭션 시작
    START TRANSACTION;

    -- 1. 입고 요청 테이블 (inbound_request)에 데이터 삽입
    -- 'inbound_request_approved'는 초기 상태이므로 0 (미승인)으로 설정합니다.
    INSERT INTO inbound_request (
        inbound_request_id,
        member_id,
        manager_id,
        inbound_request_approved,
        inbound_receipt
    ) VALUES (
        c_inbound_request_id,
        c_member_id,
        c_manager_id,
        0,
        'not yet'
    );

    -- 2. 입고 요청 상세 테이블 (inbound_request_items)에 데이터 삽입
    -- JSON 배열을 파싱하여 각 아이템을 테이블에 삽입합니다.
    INSERT INTO inbound_request_items (
        inbound_request_id,
        member_id,
        manager_id,
        coffee_id,
        inbound_request_quantity,
        inbound_request_date
    )
    SELECT
        c_inbound_request_id,
        c_member_id,
        c_manager_id, -- 수정: c_manager_id_items 대신 c_manager_id를 사용
        items.coffee_id,
        items.quantity,
        c_inbound_request_date
    FROM
        JSON_TABLE(
            c_request_items_json,
            '$[*]' COLUMNS (
                coffee_id CHAR(12) PATH '$.coffee_id',
                quantity INT PATH '$.quantity'
            )
        ) AS items;

    -- 모든 작업이 성공하면 커밋
    COMMIT;

END @@

DELIMITER ;

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- submit_member_inbound_request 프로시저 테스트 코드

-- 테스트를 위한 변수 설정 (하드코딩된 값 대신 실제 데이터 사용)
SET @test_request_id = CONCAT('REQ', DATE_FORMAT(NOW(), '%Y%m%d')); -- 현재 시각 기반의 고유한 ID 생성
SET @test_member_id = (SELECT member_id FROM members ORDER BY RAND() LIMIT 1); -- 임의의 회원 ID 선택
SET @test_manager_id = (SELECT manager_id FROM managers ORDER BY RAND() LIMIT 1); -- 임의의 관리자 ID 선택
SET @test_request_date = CURDATE(); -- 오늘 날짜를 요청 날짜로 설정

-- 입고 요청할 상품 목록을 JSON 형식으로 생성
-- 4개의 임의의 커피와 수량을 선택하여 JSON 배열로 만듭니다.
SET @test_items_json = (
    SELECT JSON_ARRAYAGG(
        JSON_OBJECT('coffee_id', coffee_id, 'quantity', FLOOR(10 + (RAND() * 90)))
    )
    FROM (
        SELECT coffee_id FROM coffees ORDER BY RAND() LIMIT 4
    ) AS random_coffees
);

-- 프로시저 호출
-- 위에서 생성한 변수들을 인자로 전달합니다.
CALL submit_member_inbound_request(
    @test_request_id,
    @test_member_id,
    @test_manager_id,
    @test_items_json,
    @test_request_date
);

-- 결과 확인
-- inbound_request 테이블에 데이터가 정상적으로 삽입되었는지 확인
SELECT '입고 요청 데이터 확인:' AS '결과';
SELECT * FROM inbound_request WHERE inbound_request_id = @test_request_id;

-- inbound_request_items 테이블에 데이터가 정상적으로 삽입되었는지 확인
SELECT '입고 요청 상세 데이터 확인:' AS '결과';
SELECT * FROM inbound_request_items WHERE inbound_request_id = @test_request_id;

-- 관리자가 회원의 미승인된 입고 요청 내역이 있는 회원의 아이디를 조회하는 프로시저
-- -> 선택한 회원의 입고요청상세 전체 내역 조회 : procedure show_all_inbound_request_items;
drop procedure  get_all_member_has_unapproved_inbound_request;
delimiter @@
create procedure get_all_member_has_unapproved_inbound_request()
begin
-- 회원 ID, 미승인 건수 출력
select member_id, count(member_id) as unapproved_request_num
from inbound_request
where inbound_request_approved = 0
group by member_id
order by count(member_id) desc;
end @@
delimiter ;

call get_all_member_has_unapproved_inbound_request();


-- 회원의 미승인된 입고 요청을 회원 정보로 조회하는 프로시저
drop procedure get_unapproved_inbounds_by_member;
delimiter @@
create procedure get_unapproved_inbounds_by_member(
	IN c_member_id varchar(15))
begin
	select I.member_id, C.coffee_name, I.inbound_request_quantity, I.inbound_request_date, I.inbound_request_item_id
    from inbound_request_items I
    join inbound_request R on I.member_id = R.member_id
    join coffees C on C.coffee_id = I.coffee_id
    where R.inbound_request_approved = 0 AND I.member_id = c_member_id
    order by I.inbound_request_date;
end @@
delimiter ;
    
select * from inbound_request_items;
select * from inbound_request;

call get_unapproved_inbounds_by_member('member12346');

-- 회원의 승인된 입고 완료 현황을 회원 id로 조회하는 프로시저
drop procedure get_approved_inbounds_by_member;
delimiter @@
create procedure get_approved_inbounds_by_member(
	IN c_member_id varchar(15))
begin
	select I.member_id, C.coffee_name, I.inbound_request_quantity, I.inbound_request_date
    from inbound_request_items I
    join inbound_request R on I.member_id = R.member_id
    join coffees C on C.coffee_id = I.coffee_id
    where R.inbound_request_approved = 1 AND I.member_id = c_member_id AND inbound_request_date < now()
    order by I.inbound_request_date;
end @@
delimiter ;
    
select * from inbound_request_items;
select * from inbound_request;

call get_approved_inbounds_by_member('member12347');

-- 회원의 입고 요청 상세 테이블에서 하나의 커피와 수량을 수정하는 프로시저

drop procedure update_inbound_request_items;
delimiter @@
create procedure update_inbound_request_items(
	IN c_inbound_request_item_id int, 
	IN c_inbound_request_id char(12),
	IN c_member_id varchar(15), 
    IN c_manager_id varchar(15), 
    IN c_coffee_id char(12), 
    IN c_inbound_request_qty int, 
    IN c_inbound_request_date date,
    OUT result_code int)
begin
    -- 에러 발생 시 롤백 처리 및 실패 코드 반환
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET result_code = 0; -- -1: SQL 예외 발생
    END;

    -- 트랜잭션 시작
    START TRANSACTION;

	update inbound_request_items
    set coffee_id = c_coffee_id, inbound_request_quantity = c_inbound_request_qty, inbound_request_date = c_inbound_request_date
    where inbound_request_item_id = c_inbound_request_item_id;
    
	-- 1 이상: 정상적으로 업데이트된 행의 개수
    SET result_code = ROW_COUNT();
    

end @@ 
delimiter ;

-- update_inbound_request_items 프로시저 테스트
-- 프로시저에 전달할 값을 SELECT 문으로 가져옵니다.
-- 이 예제에서는 inbound_request_items 테이블의 첫 번째 행을 업데이트합니다.
SET @update_item_id = (SELECT inbound_request_item_id FROM inbound_request_items ORDER BY inbound_request_item_id LIMIT 1);
SET @update_req_id = (SELECT inbound_request_id FROM inbound_request_items WHERE inbound_request_item_id = @update_item_id);
SET @update_member_id = (SELECT member_id FROM inbound_request_items WHERE inbound_request_item_id = @update_item_id);
SET @update_manager_id = (SELECT manager_id FROM inbound_request_items WHERE inbound_request_item_id = @update_item_id);

-- 업데이트할 새로운 커피 ID와 수량을 랜덤하게 선택하여 변수에 저장합니다.
SET @new_coffee_id = (SELECT coffee_id FROM coffees ORDER BY RAND() LIMIT 1);
SET @new_quantity = 120;
SET @update_date = CURDATE();


-- 프로시저를 호출하여 데이터 업데이트를 수행합니다.
CALL update_inbound_request_items(@update_item_id, @update_req_id, @update_member_id, @update_manager_id, @new_coffee_id, @new_quantity, @update_date, @result);
select @result;


-- 업데이트가 정상적으로 수행되었는지 확인하기 위해 해당 행을 다시 조회합니다.
SELECT * FROM inbound_request_items WHERE inbound_request_item_id = @update_item_id;

### 회원의 입고 요청 삭제 프로시저
-- 회원의 입고 요청 데이터 삭제 프로시저
drop procedure if exists delete_inbound_request;
delimiter @@
create procedure delete_inbound_request(
	IN c_inbound_request_id char(12),
    IN c_member_id varchar(15),
    OUT result_code int)
begin
    -- 롤백 핸들러
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
		SET result_code = 0; -- -1: SQL 예외 발생
	END;

    START TRANSACTION;

    -- 1. inbound_request_items 테이블에서 관련 데이터를 삭제합니다.
    DELETE FROM inbound_request_items
    WHERE inbound_request_id = c_inbound_request_id AND member_id = c_member_id;

    -- 2. inbound_request 테이블에서 관련 데이터를 삭제합니다.
    DELETE FROM inbound_request
    WHERE inbound_request_id = c_inbound_request_id AND member_id = c_member_id;

    COMMIT;
    
    -- 1 이상: 정상적으로 업데이트된 행의 개수
    SET result_code = ROW_COUNT();
end @@
delimiter ;

-- 테스트 코드 작성

-- delete_inbound_request 프로시저 테스트
-- 테스트를 위해 임시로 데이터를 삽입합니다.
-- 임의의 member_id와 manager_id를 선택합니다.
SET @test_member_id = (SELECT member_id FROM members ORDER BY RAND() LIMIT 1);
SET @test_manager_id = (SELECT manager_id FROM managers ORDER BY RAND() LIMIT 1);
SET @test_request_id = 'testreq_0001'; -- 테스트용 고유 ID

-- inbound_request 테이블에 데이터 삽입
INSERT INTO inbound_request (inbound_request_id, member_id, manager_id, inbound_request_approved, inbound_receipt)
VALUES (@test_request_id, @test_member_id, @test_manager_id, FALSE, 'Test Receipt');

-- inbound_request_items 테이블에 데이터 삽입
INSERT INTO inbound_request_items (inbound_request_id, member_id, manager_id, coffee_id, inbound_request_quantity, inbound_request_date)
VALUES (@test_request_id, @test_member_id, @test_manager_id, (SELECT coffee_id FROM coffees ORDER BY RAND() LIMIT 1), 50, CURDATE());

-- 데이터 삭제 전 확인
SELECT 'Before Delete:' AS status;
SELECT * FROM inbound_request WHERE inbound_request_id = @test_request_id;
SELECT * FROM inbound_request_items WHERE inbound_request_id = @test_request_id;

-- 프로시저 호출
CALL delete_inbound_request(@test_request_id, @test_member_id, @result);

-- 데이터 삭제 후 확인
SELECT * FROM inbound_request WHERE inbound_request_id = @test_request_id;
SELECT * FROM inbound_request_items WHERE inbound_request_id = @test_request_id;



-- 관리자가 입고 요청을 승인하고 재고를 업데이트하는 프로시저
drop procedure process_inbound_request;
DELIMITER @@
CREATE PROCEDURE process_inbound_request(
    IN p_inbound_request_id CHAR(12),
    IN p_items_json JSON
)
BEGIN
    DECLARE v_coffee_id CHAR(12);
    DECLARE v_location_place_id CHAR(30);
    DECLARE v_quantity INT;
    DECLARE v_inbound_request_item_id BIGINT;
    DECLARE v_index INT DEFAULT 0;
    
    -- 오류 발생 시 롤백
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    -- 트랜잭션 시작
    START TRANSACTION;

    -- 1. 입고 요청의 승인 상태를 '1'로 업데이트
    UPDATE inbound_request
    SET inbound_request_approved = 1
    WHERE inbound_request_id = p_inbound_request_id;
    
    -- 2. JSON 배열을 순회하며 각 품목의 재고를 업데이트
    WHILE v_index < JSON_LENGTH(p_items_json) DO
        -- JSON 배열에서 coffee_id, location_place_id, quantity를 추출
        SET v_coffee_id = JSON_UNQUOTE(JSON_EXTRACT(p_items_json, CONCAT('$[', v_index, '].coffee_id')));
        SET v_location_place_id = JSON_UNQUOTE(JSON_EXTRACT(p_items_json, CONCAT('$[', v_index, '].location_place_id')));
        SET v_quantity = JSON_UNQUOTE(JSON_EXTRACT(p_items_json, CONCAT('$[', v_index, '].quantity')));
        SET v_inbound_request_item_id = JSON_UNQUOTE(JSON_EXTRACT(p_items_json, CONCAT('$[', v_index, '].inbound_request_item_id')));
        
        -- 3. 재고 테이블에 해당 커피와 보관 위치가 이미 있는지 확인
        -- 보관 위치(`location_place_id`)가 지정되었으므로, 같은 커피ID와 보관위치ID가 있는지 확인
        IF EXISTS (
            SELECT 1
            FROM inventory
            WHERE coffee_id = v_coffee_id AND location_place_id = v_location_place_id
        ) THEN
            -- 이미 존재하면 재고 수량을 증가시킴
            UPDATE inventory
            SET inventory_quantity = inventory_quantity + v_quantity
            WHERE coffee_id = v_coffee_id AND location_place_id = v_location_place_id;
        ELSE
            -- 존재하지 않으면 새로운 재고 항목을 삽입, 이때 재고 아이디 = "입고 요청 아이디 + v_index"로 
            INSERT INTO inventory (inventory_id, location_place_id, coffee_id, inventory_quantity)
            VALUES (CONCAT(v_inbound_request_item_id,'_', v_index), v_location_place_id, v_coffee_id, v_quantity);
        END IF;

        -- 다음 품목으로 이동
        SET v_index = v_index + 1;
    END WHILE;
    
    -- 트랜잭션 커밋
    COMMIT;

END @@
DELIMITER ;

-- procedure 테스트
-- process_inbound_request 프로시저 테스트
-- 테스트에 사용할 입고 요청 ID를 선택합니다. 이 예제에서는 승인되지 않은 요청 중 첫 번째 것을 선택합니다.
SET @inbound_request_id_to_process = (SELECT inbound_request_id FROM inbound_request WHERE inbound_request_approved = 0 ORDER BY RAND() LIMIT 1);


-- 테스트용 JSON 데이터를 만듭니다.
-- 이 예제에서는 해당 입고 요청과 관련된 품목들을 조회하여 JSON 형식으로 변환합니다.
SET @json_items = (SELECT JSON_ARRAYAGG(
    JSON_OBJECT(
        'inbound_request_item_id', item.inbound_request_item_id,
        'coffee_id', item.coffee_id,
        'quantity', item.inbound_request_quantity,
        'location_place_id', (SELECT location_place_id FROM location_places ORDER BY RAND() LIMIT 1) -- 임의의 위치 지정
    )
)
FROM inbound_request_items item
WHERE item.inbound_request_id = @inbound_request_id_to_process);

-- 프로시저를 호출하여 입고 요청을 처리합니다.
CALL process_inbound_request(@inbound_request_id_to_process, @json_items);

-- 업데이트가 정상적으로 수행되었는지 확인합니다.
-- 1. inbound_request 테이블의 승인 상태를 확인합니다.
-- 2. inventory 테이블에 재고가 업데이트되었는지 확인합니다.
SELECT * FROM inbound_request WHERE inbound_request_id = @inbound_request_id_to_process;
SELECT * FROM inventory WHERE coffee_id IN (SELECT coffee_id FROM inbound_request_items WHERE inbound_request_id = @inbound_request_id_to_process);



            
#############################



-- 1.3 회원의 입고요청상세 전체 내역 조회
drop procedure show_all_inbound_request_items;
delimiter @@
create procedure show_all_inbound_request_items(IN c_member_id varchar(15))
begin
	select C.coffee_name, C.coffee_id, I.inbound_request_quantity, I.inbound_request_date
	from inbound_request_items I
    join coffees C on I.coffee_id = C.coffee_id
    join inbound_request R on I.inbound_request_id = R.inbound_request_id
    where I.member_id = c_member_id;
end @@
delimiter ;
-- members 테이블에서 실제 존재하는 member_id를 선택하여 테스트합니다.
SET @member_id_to_show = (SELECT member_id FROM inbound_request_items ORDER BY RAND() LIMIT 1);

-- 프로시저를 호출하여 전체 입고 요청 내역을 조회합니다.
CALL show_all_inbound_request_items(@member_id_to_show);


-- 1.4 회원의 입고요청 하나 조회
drop procedure show_one_inbound_request_items;
delimiter @@
create procedure show_one_inbound_request_items(IN c_member_id varchar(15), IN c_inbound_request_id char(12))
begin
	select C.coffee_name, C.coffee_id, I.inbound_request_quantity, I.inbound_request_date
	from inbound_request_items I
    join coffees C on I.coffee_id = C.coffee_id
    join inbound_request R on I.inbound_request_id = R.inbound_request_id
    where I.member_id = c_member_id AND I.inbound_request_id = c_inbound_request_id;
end @@
delimiter ;


-- inbound_request_items 테이블에서 실제 존재하는 member_id와 inbound_request_id를 선택하여 테스트합니다.
SET @member_id_one_item = (SELECT member_id FROM inbound_request_items ORDER BY RAND() LIMIT 1);
SET @inbound_request_id_one_item = (SELECT inbound_request_id FROM inbound_request_items WHERE member_id = @member_id_one_item ORDER BY RAND() LIMIT 1);

-- 프로시저를 호출하여 특정 회원의 특정 입고 요청 내역을 조회합니다.
CALL show_one_inbound_request_items(@member_id_one_item, @inbound_request_id_one_item);


### 입고 고지서를 입력하는 프로시저

drop procedure create_inbound_receipt;
delimiter @@
create procedure create_inbound_receipt(IN c_inbound_request_id char(12), IN c_member_id varchar(15), IN c_inbound_receipt TEXT)
begin
	update inbound_request
    set inbound_receipt = c_inbound_receipt
    where inbound_request_id = c_inbound_request_id;
end @@
delimiter ;

-- create_inbound_receipt 프로시저 테스트
-- 테스트를 위해 입고 고지서 데이터를 업데이트할 입고 요청 ID와 회원 ID를 선택합니다.
select * from inbound_request;
SET @receipt_request_id = (SELECT inbound_request_id FROM inbound_request WHERE inbound_receipt IS NULL LIMIT 1);
SET @receipt_member_id = (SELECT member_id FROM inbound_request WHERE inbound_request_id = @receipt_request_id);
SET @new_receipt_text = 'Test receipt for inbound request. This is a newly created receipt.';

-- 프로시저를 호출하여 고지서 데이터를 업데이트합니다.
CALL create_inbound_receipt(@receipt_request_id, @receipt_member_id, @new_receipt_text);

-- 업데이트가 정상적으로 수행되었는지 확인합니다.
SELECT 'After Receipt Update:' AS status;
SELECT * FROM inbound_request WHERE inbound_request_id = @receipt_request_id;

### 회원의 입고 현황 조회 프로시저
-- 승인된 입고 현황을 기간별로 조회
drop procedure show_inbound_period;
delimiter @@
create procedure show_inbound_period(IN c_member_id varchar(15), IN start_date date, IN end_date date)
begin
	select I.inbound_request_id, concat(C.coffee_name, '(', C.coffee_id, ')') as '입고품목', I.inbound_request_quantity, I.inbound_request_date
    from inbound_request_items I
    join coffees C on I.coffee_id = C.coffee_id
    join inbound_request R on I.inbound_request_id = R.inbound_request_id
    where I.member_id = c_member_id and R.inbound_request_approved = 1 and I.inbound_request_date between start_date and end_date
    order by inbound_request_date asc;
    
end @@
delimiter ;

-- show_inbound_period 프로시저 테스트
-- 테스트를 위해 입고 현황을 조회할 회원의 ID와 날짜 범위를 선택합니다.
-- 입고 요청 상세 내역이 존재하는 회원 중 한 명을 선택합니다.
SET @member_id_period = (SELECT member_id FROM inbound_request_items ORDER BY RAND() LIMIT 1);
-- 조회 시작 날짜를 현재 날짜로부터 1년 전으로 설정합니다.
SET @start_date = DATE_SUB(CURDATE(), INTERVAL 1 YEAR);
-- 조회 종료 날짜를 현재 날짜로 설정합니다.
SET @end_date = CURDATE();

-- 프로시저를 호출하여 해당 회원의 입고 현황을 기간별로 조회합니다.
CALL show_inbound_period(@member_id_period, @start_date, @end_date);


-- 월별 입고현황 조회
drop procedure show_inbound_month;
delimiter @@
create procedure show_inbound_month(IN c_member_id varchar(15), IN mon int)
begin
	select I.inbound_request_id, concat(C.coffee_name, '(', C.coffee_id, ')') as '입고품목', I.inbound_request_quantity, I.inbound_request_date
    from inbound_request_items I
    join coffees C on I.coffee_id = C.coffee_id
    join inbound_request R on I.inbound_request_id = R.inbound_request_id
    where I.member_id = c_member_id and R.inbound_request_approved = 1 and month(I.inbound_request_date) = mon
    order by inbound_request_date asc;
    
end @@
delimiter ;

-- show_inbound_month 프로시저 테스트
-- 테스트를 위해 입고 현황을 조회할 회원 ID와 월을 선택합니다.
-- 승인된 입고 요청이 있는 회원 중 한 명을 선택합니다.
SET @member_id_month = (SELECT I.member_id FROM inbound_request_items I JOIN inbound_request R ON I.inbound_request_id = R.inbound_request_id WHERE R.inbound_request_approved = 1 ORDER BY RAND() LIMIT 1);

-- 해당 회원의 승인된 요청이 있는 월 중 하나를 선택합니다.
SET @month_to_show = (SELECT MONTH(I.inbound_request_date) FROM inbound_request_items I JOIN inbound_request R ON I.inbound_request_id = R.inbound_request_id WHERE I.member_id = @member_id_month AND R.inbound_request_approved = 1 ORDER BY RAND() LIMIT 1);

-- 프로시저를 호출하여 해당 회원의 월별 입고 현황을 조회합니다.
CALL show_inbound_month(@member_id_month, @month_to_show);
