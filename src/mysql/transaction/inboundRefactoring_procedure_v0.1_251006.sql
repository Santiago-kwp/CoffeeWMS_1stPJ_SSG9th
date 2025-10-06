

/*
 회원의 입고 요청을 받고 DB 테이블에 저장하는 프로시저
 */
DELIMITER //

DROP PROCEDURE IF EXISTS CreateInboundRequest;

CREATE PROCEDURE CreateInboundRequest(
    IN p_member_id VARCHAR(15),
    IN p_request_date DATE,
    IN p_items_json JSON,
    OUT p_generated_request_id BIGINT
)
BEGIN
    DECLARE v_inbound_request_id BIGINT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
ROLLBACK;
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error creating inbound request. Transaction rolled back.';
END;

START TRANSACTION;

-- 1. inbound_request 테이블에 데이터 삽입
INSERT INTO inbound_request (member_id, request_date, status, is_deleted)
VALUES (p_member_id, p_request_date, '대기',  0); -- NULL로 삽입

-- 2. 삽입된 inbound_request의 ID 가져오기
-- LAST_INSERT_ID() 함수는 1개의 insert 쿼리에 대해서 성공시 마지막 auto_increment 값

SET v_inbound_request_id = LAST_INSERT_ID();
    SET p_generated_request_id = v_inbound_request_id;

    -- 3. inbound_request_items 테이블에 상세 항목 삽입 (JSON_TABLE 활용)
    IF p_items_json IS NOT NULL AND JSON_LENGTH(p_items_json) > 0 THEN
        INSERT INTO inbound_request_items (inbound_request_id, coffee_id, inbound_request_quantity)
SELECT
    v_inbound_request_id,
    JSON_UNQUOTE(JSON_EXTRACT(item_data, '$.coffeeId')),
    JSON_UNQUOTE(JSON_EXTRACT(item_data, '$.inboundRequestQuantity'))
FROM
    JSON_TABLE(
            p_items_json,
            '$[*]' COLUMNS (
                    item_data JSON PATH '$'
                )
    ) AS jt;
END IF;

COMMIT;
END //

DELIMITER ;