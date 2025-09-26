SELECT * FROM notice;

########################################################################################################################
-- 프로시져
#공지사항 입력
DELIMITER @@
CREATE PROCEDURE create_notice(IN p_notice_title VARCHAR(50),
                               IN p_notice_content TEXT,
                               IN p_notice_fixed BOOLEAN,
                               IN p_manager_id VARCHAR(15))
BEGIN
    INSERT INTO notice(notice_title, notice_content, notice_date, notice_fixed, manager_id)
    VALUES (p_notice_title, p_notice_content, NOW(), p_notice_fixed, p_manager_id);
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#공지사항 조회 -> 고정 목록을 먼저 출력하고, 최근일 순으로 출력
DELIMITER @@
CREATE PROCEDURE read_notice_main()
BEGIN
    SELECT notice_date, notice_title
    FROM notice
    ORDER BY notice_fixed DESC, notice_date DESC
    LIMIT 3;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#전체 공지사항 조회
DELIMITER @@
CREATE PROCEDURE read_notice_all()
BEGIN
    SELECT notice_id, notice_date, notice_title, notice_content
    FROM notice
    ORDER BY notice_fixed DESC, notice_date DESC;
END @@
DELIMITER ;
DROP PROCEDURE read_notice_all;
#-----------------------------------------------------------------------------------------------------------------------
#상세 공지사항 조회
DELIMITER @@
CREATE PROCEDURE read_notice_one(IN p_notice_id INTEGER)
BEGIN
    SELECT notice_date, notice_title, notice_content
    FROM notice
    WHERE notice_id = p_notice_id;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#공지사항 수정
DELIMITER @@
CREATE PROCEDURE update_notice(IN p_notice_id INTEGER,
                               IN p_notice_title VARCHAR(50),
                               IN p_notice_content TEXT,
                               IN p_notice_fixed BOOLEAN,
                               IN p_manager_id VARCHAR(15))
BEGIN
    UPDATE notice
    SET notice_title   = p_notice_title,
        notice_content = p_notice_content,
        notice_fixed   = p_notice_fixed
    WHERE notice_id = p_notice_id
      AND manager_id = p_manager_id;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#공지사항 삭제
DELIMITER @@
CREATE PROCEDURE delete_notice(IN p_notice_id INTEGER, IN p_manager_id VARCHAR(15))
BEGIN
    DELETE FROM notice WHERE notice_id = p_notice_id AND manager_id = p_manager_id;
END @@
DELIMITER ;
########################################################################################################################
#1:1문의 입력
DELIMITER @@
CREATE PROCEDURE create_inquiry(IN p_inquiry_category_id INTEGER,
                                IN p_inquiry_content TEXT,
                                IN p_member_id VARCHAR(15))
BEGIN
    INSERT INTO inquiry(inquiry_date, inquiry_category_id, inquiry_content, member_id, inquiry_status)
    VALUES (NOW(), p_inquiry_category_id, p_inquiry_content, p_member_id, 'PENDING');
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#1:1문의 조회(회원) -> 로그인한 본인이 작성한 질문만 조회 가능
DELIMITER @@
CREATE PROCEDURE read_inquiry_member_all(IN p_member_id VARCHAR(15))
BEGIN
    SELECT A.inquiry_id,
           A.inquiry_date,
           B.inquiry_category_name,
           A.inquiry_content,
           A.inquiry_status,
           A.reply_date,
           A.reply_content
    FROM inquiry AS A
             JOIN inquiry_category AS B
                  ON A.inquiry_category_id = B.inquiry_category_id
    WHERE A.member_id = p_member_id
    ORDER BY A.inquiry_date DESC, A.inquiry_id DESC;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#1:1문의 조회(총관리자) -> 로그인한 본인이 작성한 질문만 조회 가능
DELIMITER @@
CREATE PROCEDURE read_inquiry_manager_all()
BEGIN
    SELECT A.inquiry_id,
           A.inquiry_date,
           B.inquiry_category_name,
           A.inquiry_content,
           A.inquiry_status,
           A.reply_date,
           A.reply_content
    FROM inquiry AS A
             JOIN inquiry_category AS B
                  ON A.inquiry_category_id = B.inquiry_category_id
    ORDER BY A.inquiry_date DESC, A.inquiry_id DESC;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
    DROP PROCEDURE read_inquiry_member_one;
#1:1문의 조회(회원) -> 로그인한 본인이 작성한 질문만 조회 가능
DELIMITER @@
CREATE PROCEDURE read_inquiry_member_one(IN p_member_id VARCHAR(15),
                                         IN p_inquiry_id INTEGER)
BEGIN
    SELECT A.inquiry_id,
           A.inquiry_date,
           B.inquiry_category_name,
           A.inquiry_content,
           A.inquiry_status,
           A.reply_date,
           A.reply_content
    FROM inquiry AS A
             JOIN inquiry_category AS B
                  ON A.inquiry_category_id = B.inquiry_category_id
    WHERE A.member_id = p_member_id AND A.inquiry_id = p_inquiry_id
    ORDER BY A.inquiry_date DESC, A.inquiry_id DESC;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#1:1문의 조회(총관리자) -> 로그인한 본인이 작성한 질문만 조회 가능
DELIMITER @@
CREATE PROCEDURE read_inquiry_manager_one(IN p_inquiry_id INTEGER)
BEGIN
    SELECT A.inquiry_id,
           A.inquiry_date,
           B.inquiry_category_name,
           A.inquiry_content,
           A.inquiry_status,
           A.reply_date,
           A.reply_content
    FROM inquiry AS A
             JOIN inquiry_category AS B
                  ON A.inquiry_category_id = B.inquiry_category_id
    WHERE A.inquiry_id = p_inquiry_id
    ORDER BY A.inquiry_date DESC, A.inquiry_id DESC;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#1:1문의 수정_작성자 수정
DELIMITER @@
CREATE PROCEDURE update_inquiry_member(IN p_inquiry_id INTEGER, -- 해당 문의
                                       IN p_inquiry_category_id INTEGER,
                                       IN p_inquiry_content TEXT,
                                       IN p_member_id VARCHAR(15)) -- 해당 작성자
BEGIN
    UPDATE inquiry
    SET inquiry_category_id = p_inquiry_category_id,
        inquiry_content     = p_inquiry_content
    WHERE inquiry_id = p_inquiry_id
      AND member_id = p_member_id
      AND inquiry_status = 'PENDING'; -- 답변 전에만 수정 가능
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#1:1문의 수정_답변자 답변
DELIMITER @@
CREATE PROCEDURE update_inquiry_manager(IN p_inquiry_id INTEGER,
                                        IN p_reply_content TEXT,
                                        IN p_manager_id VARCHAR(15))
BEGIN
    UPDATE inquiry
    SET reply_date     = NOW(),
        reply_content  = p_reply_content,
        manager_id     = p_manager_id,
        inquiry_status = 'DONE'
    WHERE inquiry_id = p_inquiry_id;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#1:1문의 삭제_작성자
DELIMITER @@
CREATE PROCEDURE delete_inquiry_member(IN p_inquiry_id INTEGER, IN p_member_id VARCHAR(15))
BEGIN
    DELETE FROM inquiry WHERE inquiry_id = p_inquiry_id AND member_id = p_member_id;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#1:1문의 삭제_총관리자
DELIMITER @@
CREATE PROCEDURE delete_inquiry_manager(IN p_inquiry_id INTEGER)
BEGIN
    DELETE FROM inquiry WHERE inquiry_id = p_inquiry_id;
END @@
DELIMITER ;
########################################################################################################################
#FAQ 입력
DELIMITER @@
CREATE PROCEDURE create_faq(IN f_category_id INTEGER,
                            IN f_question TEXT,
                            IN f_reply TEXT,
                            IN m_id VARCHAR(15))
BEGIN
    INSERT INTO faq(faq_category_id, faq_question, faq_reply, faq_date, manager_id)
    VALUES (f_category_id, f_question, f_reply, NOW(), m_id);
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#FAQ 전체 조회
DELIMITER @@
CREATE PROCEDURE read_faq_all()
BEGIN
    SELECT A.faq_id, A.faq_date, B.faq_category_name, A.faq_question, A.faq_reply
    FROM faq AS A
             JOIN faq_category AS B
                  ON A.faq_category_id = B.faq_category_id
    ORDER BY faq_date DESC, faq_id DESC;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#FAQ 상세 조회
DELIMITER @@
CREATE PROCEDURE read_faq_one(IN p_faq_id INTEGER)
BEGIN
    SELECT A.faq_date, B.faq_category_name, A.faq_question, A.faq_reply
    FROM faq AS A
             JOIN faq_category AS B
                  ON A.faq_category_id = B.faq_category_id
    WHERE A.faq_id = p_faq_id
    ORDER BY faq_date DESC, faq_id DESC;
END @@
DELIMITER ;
DROP PROCEDURE read_faq_one;
#-----------------------------------------------------------------------------------------------------------------------
#FAQ 수정
DELIMITER @@
CREATE PROCEDURE update_faq(IN f_id INTEGER,
                            IN f_question TEXT,
                            IN f_reply TEXT)
BEGIN
    UPDATE faq SET faq_question = f_question, faq_reply = f_reply WHERE faq_id = f_id;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#FAQ 삭제
DELIMITER @@
CREATE PROCEDURE delete_faq(IN f_id INTEGER,
                            IN m_id VARCHAR(15))
BEGIN
    DELETE FROM faq WHERE faq_id = f_id AND manager_id = m_id;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#FAQ 카테고리 조회
DELIMITER @@
CREATE PROCEDURE read_faq_category()
BEGIN
    SELECT faq_category_id, faq_category_name FROM faq_category;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
#1:1 문의 카테고리 조회
DELIMITER @@
CREATE PROCEDURE read_inquiry_category()
BEGIN
    SELECT inquiry_category_id, inquiry_category_name FROM inquiry_category;
END @@
DELIMITER ;
#-----------------------------------------------------------------------------------------------------------------------
DESC notice;
DESC inquiry;
DESC faq;
ALTER TABLE inquiry
    MODIFY inquiry_id INT NOT NULL AUTO_INCREMENT;
ALTER TABLE faq
    MODIFY faq_id INT NOT NULL AUTO_INCREMENT;