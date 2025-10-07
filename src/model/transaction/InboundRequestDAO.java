package model.transaction;

import domain.transaction.Coffee;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.vo.InboundStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface InboundRequestDAO {

    // 회원의 입고 요청을 DB 데이터에 삽입하는 메소드
    public Long insertInboundRequest(InboundRequestDTO requestDto) throws SQLException;
    // 회원의 입고 요청을 수정, 삭제, 조회, 입고 상태로 조회, 입고 상태로 전체 조회

    int updateInboundRequest(Connection conn, InboundRequestDTO dto) throws SQLException;
    int deleteInboundRequest(Connection conn, Long inboundRequestId) throws SQLException; // 논리적 삭제
    InboundRequestDTO findInboundRequestById(Long inboundRequestId) throws SQLException;
    List<InboundRequestDTO> findInboundRequestsByMemberId(String memberId, InboundStatus status) throws SQLException;
    List<InboundRequestDTO> findAllInboundRequestsByStatus(InboundStatus status) throws SQLException; // 관리자용


    // --- 회원용 조회 메서드 추가 ---
    List<InboundRequestDTO> findRequestsByMemberIdAndDateRange(String memberId, InboundStatus status, LocalDate startDate, LocalDate endDate) throws SQLException;
    List<InboundRequestDTO> findRequestsByMemberIdAndMonth(String memberId, InboundStatus status, int year, int month) throws SQLException;

    // --- 관리자용 조회 메서드 추가 ---
    List<InboundRequestDTO> findAllRequestsByDateRange(InboundStatus status, LocalDate startDate, LocalDate endDate) throws SQLException;
    List<InboundRequestDTO> findAllRequestsByMonth(InboundStatus status, int year, int month) throws SQLException;




}
