package model.transaction;

import domain.transaction.dto.InboundRequestItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 입고 요청 상세 항목(inbound_request_items 테이블)에 대한
 * 데이터 접근 객체(DAO) 인터페이스입니다.
 */
public interface InboundRequestItemDAO {



    /**
     * 단일 입고 요청 상세 항목을 데이터베이스에 삽입합니다.
     *
     * @param dto 삽입할 상세 항목 데이터를 담은 DTO
     * @return 영향을 받은 행의 수 (보통 1)
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    int insertInboundRequestItem(InboundRequestItemDTO dto) throws SQLException;

    /**
     * 여러 입고 요청 상세 항목을 배치(batch) 처리를 통해 한 번에 삽입합니다.
     * 서비스 계층에서의 트랜잭션 관리를 위해 Connection 객체를 파라미터로 받습니다.
     *
     * @param conn 서비스 계층에서 관리하는 Connection 객체
     * @param items 삽입할 상세 항목 DTO 리스트
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    void insertInboundRequestItems(Connection conn, List<InboundRequestItemDTO> items) throws SQLException;

    /**
     * 입고 요청 상세 항목의 정보를 수정합니다.
     *
     * @param dto 수정할 상세 항목 데이터를 담은 DTO
     * @return 영향을 받은 행의 수 (보통 1)
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    int updateInboundRequestItem(InboundRequestItemDTO dto) throws SQLException;

    /**
     * ID를 기준으로 단일 입고 요청 상세 항목을 삭제합니다.
     *
     * @param inboundRequestItemId 삭제할 상세 항목의 ID
     * @return 영향을 받은 행의 수 (보통 1)
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    int deleteInboundRequestItem(Long inboundRequestItemId) throws SQLException;

    /**
     * 특정 입고 요청(inbound_request) ID에 속한 모든 상세 항목을 조회합니다.
     *
     * @param inboundRequestId 부모 입고 요청의 ID
     * @return 조회된 상세 항목 DTO 리스트
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    List<InboundRequestItemDTO> findItemsByInboundRequestId(Long inboundRequestId) throws SQLException;

    // 서비스 계층에서 요청한 추가 메서드
    /**
     * 특정 입고 요청 ID에 해당하는 모든 상세 항목을 삭제합니다.
     * (트랜잭션 관리를 위해 Connection 객체를 받음)
     */
    int deleteItemsByInboundRequestId(Connection conn, Long inboundRequestId) throws SQLException;

    /**
     * 여러 상세 항목을 배치(batch)로 삽입합니다.
     * (트랜잭션 관리를 위해 Connection 객체를 받음)
     * 기존 insertInboundRequestItems와 동일합니다. 이름을 통일하거나 이것을 사용하면 됩니다.
     */
    void insertItems(Connection conn, List<InboundRequestItemDTO> items) throws SQLException;

}