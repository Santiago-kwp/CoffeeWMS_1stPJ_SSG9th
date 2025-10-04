package model.transaction;


import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.dto.InboundRequestItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface InboundRequestDAO {

    // 회원의 입고 요청 생성
    /**
     * 새로운 입고 요청을 생성하고 데이터베이스에 저장합니다.
     * 입고 요청 상세(InboundRequestItemDTO)도 함께 처리합니다.
     *
     * @param requestDTO 생성할 입고 요청 정보 (InboundRequestDTO)
     * @return 생성된 입고 요청의 ID (Primary Key)
     * @throws SQLException 데이터베이스 작업 중 오류 발생 시
     */
    Long createInboundRequest(InboundRequestDTO requestDTO) throws SQLException;

    /**
     * 특정 입고 요청의 정보를 조회합니다.
     *
     * @param inboundRequestId 조회할 입고 요청의 ID
     * @return 조회된 입고 요청 정보 (InboundRequestDTO), 없으면 null 반환
     * @throws SQLException 데이터베이스 작업 중 오류 발생 시
     */
    InboundRequestDTO getInboundRequestById(Long inboundRequestId) throws SQLException;

    // 회원의 입고 요청 수정

    /**
     * 회원의 입고 요청 상태를 수정합니다.
     * (예: 대기 -> 승인완료, 대기 -> 승인거절)
     *
     * @param inboundRequestId 수정할 입고 요청의 ID
     * @param newStatus 새로운 상태
     * @param managerId 상태를 변경한 관리자 ID (승인/거절 시)
     * @return 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 작업 중 오류 발생 시
     */
    boolean updateInboundRequestStatus(Long inboundRequestId, String newStatus, String managerId) throws SQLException;

    // 회원의 입고 요청 삭제
    /**
     * 회원의 입고 요청을 논리적으로 삭제 처리합니다.
     * is_deleted = 1, is_deleted_at = 현재 날짜로 설정합니다.
     *
     * @param inboundRequestId 삭제할 입고 요청의 ID
     * @return 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 작업 중 오류 발생 시
     */
    boolean softDeleteInboundRequest(Long inboundRequestId) throws SQLException;

    /**
     * 특정 회원이 요청한 모든 입고 요청 리스트를 조회합니다.
     *
     * @param conn 데이터베이스 연결 객체
     * @param memberId 조회할 회원의 ID
     * @return 해당 회원의 입고 요청 리스트
     * @throws SQLException 데이터베이스 작업 중 오류 발생 시
     */
    List<InboundRequestDTO> getInboundRequestsByMemberId(String memberId) throws SQLException;

    /**
     * 모든 입고 요청 리스트를 조회합니다. (관리자용)
     *
     * @return 모든 입고 요청 리스트
     * @throws SQLException 데이터베이스 작업 중 오류 발생 시
     */
    List<InboundRequestDTO> getAllInboundRequests() throws SQLException;


    /**
     * 특정 입고 요청에 속한 입고 요청 상세 목록을 조회합니다
     * @param inboundRequestId 입고 요청 ID
     * @return 입고 요청 상세 DTO 리스트
     * @throws SQLException 데이터베이스 작업 중 오류 발생 시
     */
    List<InboundRequestItemDTO> getInboundRequestItemsByRequestId(Long inboundRequestId) throws SQLException;

    /**
     * 입고 요청 상세 항목을 데이터베이스에 저장합니다.
     * @param inboundRequestId 상위 입고 요청 ID
     * @param itemDTO 저장할 입고 요청 상세 정보
     * @throws SQLException 데이터베이스 작업 중 오류 발생 시
     */
    void createInboundRequestItem(Long inboundRequestId, InboundRequestItemDTO itemDTO) throws SQLException;


}
