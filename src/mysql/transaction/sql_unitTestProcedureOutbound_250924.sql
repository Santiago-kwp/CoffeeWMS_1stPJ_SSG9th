use testdb1;


select * from outbound_request;

### 1. 회원의 출고 요청
-- 회원의 출고 요청 및 상세 내역을 트랜잭션으로 처리하는 프로시저
DROP PROCEDURE IF EXISTS submit_member_outbound_request;
-- c_outbound_request_id: 출고 요청 고유 ID
-- c_member_id: 요청을 하는 회원 ID
-- c_manager_id: 관리자 ID (두 테이블 모두에 사용)
-- c_request_items_json: 요청 상품 ID와 수량을 담은 JSON 배열
-- c_outbound_date: 회원이 요청한 출고 날짜
DELIMITER @@
CREATE PROCEDURE submit_member_outbound_request(
    IN c_outbound_request_id CHAR(12),
    IN c_member_id VARCHAR(15),
    IN c_manager_id VARCHAR(15),
    IN c_request_items_json TEXT,
    IN c_outbound_request_date DATE
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

    -- 1. 출고 요청 테이블 (outbound_request)에 데이터 삽입
    -- 'outbound_request_approved'는 초기 상태이므로 0 (미승인)으로 설정합니다.
    INSERT INTO outbound_request (
        outbound_request_id,
        member_id,
        manager_id,
        outbound_request_approved,
        outbound_order
    ) VALUES (
        c_outbound_request_id,
        c_member_id,
        c_manager_id,
        0,
        'not yet'
    );

    -- 2. 출고 요청 상세 테이블 (outbound_request_items)에 데이터 삽입
    -- JSON 배열을 파싱하여 각 아이템을 테이블에 삽입합니다.
    INSERT INTO outbound_request_items (
        outbound_request_id,
        member_id,
        manager_id,
        inventory_id,
        outbound_request_quantity,
        outbound_request_date
    )
    SELECT
        c_outbound_request_id,
        c_member_id,
        c_manager_id,
        items.inventory_id,
        items.quantity,
        c_outbound_request_date
    FROM
        JSON_TABLE(
            c_request_items_json,
            '$[*]' COLUMNS (
                inventory_id CHAR(12) PATH '$.inventory_id',
                quantity INT PATH '$.quantity'
            )
        ) AS items;

    -- 모든 작업이 성공하면 커밋
    COMMIT;

END @@

DELIMITER ;

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- submit_member_outbound_request 프로시저 테스트 코드

-- 테스트를 위한 변수 설정 (하드코딩된 값 대신 실제 데이터 사용)
SET @test_request_id = CONCAT('OUT', DATE_FORMAT(NOW(), '%Y%m%d'));
SET @test_member_id = (SELECT member_id FROM members ORDER BY RAND() LIMIT 1);
SET @test_manager_id = (SELECT manager_id FROM managers ORDER BY RAND() LIMIT 1);
SET @test_request_date = CURDATE();

-- 출고 요청할 상품 목록을 JSON 형식으로 생성
SET @test_items_json = (
    SELECT JSON_ARRAYAGG(
        JSON_OBJECT('inventory_id', inventory_id, 'quantity', FLOOR(10 + (RAND() * 90)))
    )
    FROM (
        SELECT inventory_id FROM inventory ORDER BY RAND() LIMIT 4
    ) AS random_inventories
);

-- 프로시저 호출
CALL submit_member_outbound_request(
    @test_request_id,
    @test_member_id,
    @test_manager_id,
    @test_items_json,
    @test_request_date
);

-- 결과 확인
SELECT '출고 요청 데이터 확인:' AS '결과';
SELECT * FROM outbound_request WHERE outbound_request_id = @test_request_id;
SELECT '출고 요청 상세 데이터 확인:' AS '결과';
SELECT * FROM outbound_request_items WHERE outbound_request_id = @test_request_id;

### 
-- 회원의 미승인된 출고 요청 목록 조회 프로시저
DROP PROCEDURE IF EXISTS show_unapproved_outbound_requests;



-- c_member_id: 미승인 출고 목록을 조회할 회원 ID
DELIMITER @@
CREATE PROCEDURE show_unapproved_outbound_requests(IN c_member_id VARCHAR(15))
BEGIN
    SELECT
        I.outbound_request_id,
        CONCAT(C.coffee_name, ' (', C.coffee_id, ')') AS '출고 품목',
        I.outbound_request_quantity AS '요청 수량',
        I.outbound_request_date AS '요청 날짜',
        R.outbound_request_approved AS '승인 상태'
    FROM
        outbound_request_items I
    JOIN
        outbound_request R ON I.outbound_request_id = R.outbound_request_id AND I.member_id = R.member_id AND I.manager_id = R.manager_id
    JOIN
        inventory IV ON I.inventory_id = IV.inventory_id
    JOIN
        coffees C ON IV.coffee_id = C.coffee_id
    WHERE
        I.member_id = c_member_id
        AND (R.outbound_request_approved = 0 OR R.outbound_request_approved IS NULL)
    ORDER BY
        I.outbound_request_date ASC;
END @@

DELIMITER ;

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- show_unapproved_outbound_requests 프로시저 테스트 코드

-- 먼저, 테스트를 위해 임시 미승인 출고 요청 데이터를 생성합니다.
SET @test_request_id = CONCAT('OUT', DATE_FORMAT(NOW(), '%Y%m%d'));
SET @test_member_id = (SELECT member_id FROM members ORDER BY RAND() LIMIT 1);
SET @test_manager_id = (SELECT manager_id FROM managers ORDER BY RAND() LIMIT 1);
SET @test_request_date = CURDATE();

-- 출고 요청할 상품 목록을 JSON 형식으로 생성
SET @test_items_json = (
    SELECT JSON_ARRAYAGG(
        JSON_OBJECT('inventory_id', inventory_id, 'quantity', FLOOR(10 + (RAND() * 90)))
    )
    FROM (
        SELECT inventory_id FROM inventory ORDER BY RAND() LIMIT 4
    ) AS random_inventories
);

-- 기존에 정의된 `submit_member_outbound_request` 프로시저를 사용하여 미승인 요청 생성
CALL submit_member_outbound_request(
    @test_request_id,
    @test_member_id,
    @test_manager_id,
    @test_items_json,
    @test_request_date
);

-- 새로 만든 `show_unapproved_outbound_requests` 프로시저 호출
-- 방금 생성한 회원의 미승인 출고 요청이 조회되는지 확인합니다.
SELECT '--- 미승인 출고 요청 조회 결과 ---' AS '결과';
CALL show_unapproved_outbound_requests(@test_member_id);

-- 테스트용으로 삽입했던 데이터 삭제
DELETE FROM outbound_request_items WHERE outbound_request_id = @test_request_id;
DELETE FROM outbound_request WHERE outbound_request_id = @test_request_id;


### 회원의 출고 요청을 승인하고, 재고에 반영하는 프로시저

-- 관리자가 출고 요청을 승인하고 재고를 업데이트하는 프로시저
DROP PROCEDURE IF EXISTS process_outbound_request;

DELIMITER @@
CREATE PROCEDURE process_outbound_request(
    IN p_outbound_request_id CHAR(12),
    IN p_items_json JSON
)
BEGIN
    DECLARE v_inventory_id CHAR(12);
    DECLARE v_quantity INT;
    DECLARE v_index INT DEFAULT 0;
    
    -- 오류 발생 시 롤백
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    -- 트랜잭션 시작
    START TRANSACTION;

    -- 1. 출고 요청의 승인 상태를 '1'로 업데이트
    UPDATE outbound_request
    SET outbound_request_approved = 1
    WHERE outbound_request_id = p_outbound_request_id;
    
    -- 2. JSON 배열을 순회하며 각 품목의 재고를 업데이트
    WHILE v_index < JSON_LENGTH(p_items_json) DO
        -- JSON 배열에서 inventory_id와 quantity 추출
        SET v_inventory_id = JSON_UNQUOTE(JSON_EXTRACT(p_items_json, CONCAT('$[', v_index, '].inventory_id')));
        SET v_quantity = JSON_UNQUOTE(JSON_EXTRACT(p_items_json, CONCAT('$[', v_index, '].quantity')));
        
        -- 3. 재고 수량 확인
        IF (SELECT inventory_quantity FROM inventory WHERE inventory_id = v_inventory_id) >= v_quantity THEN
            -- 재고가 충분하면 재고 수량을 감소시킴
            UPDATE inventory
            SET inventory_quantity = inventory_quantity - v_quantity
            WHERE inventory_id = v_inventory_id;
        ELSE
            -- 재고가 부족하면 오류 발생
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = '현재 해당 품목의 재고가 부족합니다.';
        END IF;

        -- 다음 품목으로 이동
        SET v_index = v_index + 1;
    END WHILE;
    
    -- 트랜잭션 커밋
    COMMIT;

END @@
DELIMITER ;


###
-- process_outbound_request 프로시저 테스트 코드

-- 테스트를 위한 변수 설정
SET @test_request_id = CONCAT('OUT', DATE_FORMAT(NOW(), '%Y%m%d'));
SET @test_member_id = (SELECT member_id FROM members ORDER BY RAND() LIMIT 1);
SET @test_manager_id = (SELECT manager_id FROM managers ORDER BY RAND() LIMIT 1);
SET @test_request_date = CURDATE();

-- 출고 요청할 상품 목록을 JSON 형식으로 생성
SET @test_items_json = (
    SELECT JSON_ARRAYAGG(
        JSON_OBJECT('inventory_id', inventory_id, 'quantity', FLOOR(5 + (RAND() * 20)))
    )
    FROM (
        SELECT inventory_id FROM inventory ORDER BY RAND() LIMIT 4
    ) AS random_inventories
);

-- 1. `submit_member_outbound_request` 프로시저를 호출하여 미승인 출고 요청 생성
SELECT '--- 1. 미승인 출고 요청 생성 ---' AS '결과';
CALL submit_member_outbound_request(
    @test_request_id,
    @test_member_id,
    @test_manager_id,
    @test_items_json,
    @test_request_date
);

-- 2. `process_outbound_request` 프로시저를 호출하여 요청 승인 및 재고 업데이트
SELECT '--- 2. 출고 요청 승인 및 재고 업데이트 ---' AS '결과';
CALL process_outbound_request(@test_request_id, @test_items_json);

-- 3. 결과 확인
-- 3-1. `outbound_request` 테이블에서 승인 상태 확인
SELECT '--- 3-1. outbound_request 승인 상태 확인 ---' AS '결과';
SELECT
    outbound_request_id,
    outbound_request_approved
FROM
    outbound_request
WHERE
    outbound_request_id = @test_request_id;
    
-- 3-2. `inventory` 테이블에서 재고 수량 확인
SELECT '--- 3-2. inventory 재고 수량 확인 (출고량 반영) ---' AS '결과';
SELECT
    I.inventory_id,
    I.inventory_quantity AS '최종 수량'
FROM
    inventory I
WHERE
    I.inventory_id IN (
        SELECT items.inventory_id
        FROM JSON_TABLE(
            @test_items_json,
            '$[*]' COLUMNS (inventory_id CHAR(12) PATH '$.inventory_id')
        ) AS items
    );

-- 4. 테스트 데이터 삭제 (선택 사항)
DELETE FROM outbound_request_items WHERE outbound_request_id = @test_request_id;
DELETE FROM outbound_request WHERE outbound_request_id = @test_request_id;



##########################
### 입고 고지서를 생성하는 프로시저

drop procedure create_outbound_order;
delimiter @@
create procedure create_outbound_order(IN c_inbound_request_id char(12), IN c_member_id varchar(15), IN c_inbound_receipt TEXT)
begin
	update outbound_request
    set outbound_receipt = c_outbound_receipt
    where outbound_request_id = c_outbound_request_id;
end @@
delimiter ;

-- create_inbound_receipt 프로시저 테스트
-- 테스트를 위해 입고 고지서 데이터를 업데이트할 입고 요청 ID와 회원 ID를 선택합니다.
select * from outbound_request;
SET @order_request_id = (SELECT outbound_request_id FROM outbound_request WHERE outbound_order IS NULL LIMIT 1);
SET @order_member_id = (SELECT member_id FROM outbound_request WHERE outbound_request_id = @order_request_id);
SET @new_order_text = 'Test order for outbound request. This is a newly created order.';

##########################
-- 1. 회원의 승인된 출고 목록 조회 프로시저
DROP PROCEDURE IF EXISTS show_approved_outbound_requests;
DELIMITER @@
CREATE PROCEDURE show_approved_outbound_requests(IN c_member_id VARCHAR(15))
BEGIN
    SELECT
        I.outbound_request_id,
        CONCAT(C.coffee_name, ' (', C.coffee_id, ')') AS '출고 품목',
        I.outbound_request_quantity AS '요청 수량',
        I.outbound_request_date AS '요청 날짜'
    FROM
        outbound_request_items I
    JOIN
        outbound_request R ON I.outbound_request_id = R.outbound_request_id AND I.member_id = R.member_id
    JOIN
        inventory IV ON I.inventory_id = IV.inventory_id
    JOIN
        coffees C ON IV.coffee_id = C.coffee_id
    WHERE
        I.member_id = c_member_id
        AND R.outbound_request_approved = 1
    ORDER BY
        I.outbound_request_date ASC;
END @@
DELIMITER ;


-- 2. 회원의 출고 지시서(outbound_request_order) 조회 프로시저
DROP PROCEDURE IF EXISTS show_outbound_order;
DELIMITER @@
CREATE PROCEDURE show_outbound_order(IN c_member_id VARCHAR(15), IN c_outbound_request_id CHAR(12))
BEGIN
    SELECT
        outbound_order
    FROM
        outbound_request
    WHERE
        member_id = c_member_id
        AND outbound_request_id = c_outbound_request_id;
END @@
DELIMITER ;

-- 3. 출고 상품 검색 프로시저
DROP PROCEDURE IF EXISTS search_outbound_item;
DELIMITER @@
CREATE PROCEDURE search_outbound_item(IN c_member_id VARCHAR(15), IN c_coffee_id CHAR(12))
BEGIN
    SELECT
        I.outbound_request_id,
        CONCAT(C.coffee_name, ' (', C.coffee_id, ')') AS '출고 품목',
        I.outbound_request_quantity AS '요청 수량',
        I.outbound_request_date AS '요청 날짜'
    FROM
        outbound_request_items I
    JOIN
        inventory IV ON I.inventory_id = IV.inventory_id
    JOIN
        coffees C ON IV.coffee_id = C.coffee_id
    WHERE
        I.member_id = c_member_id
        AND IV.coffee_id = c_coffee_id
    ORDER BY
        I.outbound_request_date DESC;
END @@
DELIMITER ;

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- 프로시저 테스트 코드

-- 테스트를 위한 변수 설정 (테스트에 사용할 승인된 출고 요청 데이터를 가정)
SET @test_member_id = (
    SELECT member_id FROM outbound_request WHERE outbound_request_approved = 1 ORDER BY RAND() LIMIT 1
);
SET @test_approved_outbound_id = (
    SELECT outbound_request_id FROM outbound_request WHERE member_id = @test_member_id AND outbound_request_approved = 1 ORDER BY RAND() LIMIT 1
);
SET @test_coffee_id = (
    SELECT C.coffee_id
    FROM outbound_request_items I
    JOIN inventory IV ON I.inventory_id = IV.inventory_id
    JOIN coffees C ON IV.coffee_id = C.coffee_id
    WHERE I.outbound_request_id = @test_approved_outbound_id
    LIMIT 1
);

-- 1. `show_approved_outbound_requests` 프로시저 테스트
SELECT '--- 1. 승인된 출고 목록 조회 테스트 ---' AS '결과';
CALL show_approved_outbound_requests(@test_member_id);

-- 2. `show_outbound_order` 프로시저 테스트
SELECT '--- 2. 출고 지시서 조회 테스트 ---' AS '결과';
CALL show_outbound_order(@test_member_id, @test_approved_outbound_id);

-- 3. `search_outbound_item` 프로시저 테스트
SELECT '--- 3. 출고 상품 검색 테스트 ---' AS '결과';
CALL search_outbound_item(@test_member_id, @test_coffee_id);


