package service.transaction;

import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.vo.InboundStatus;
import exception.transaction.TransactionException; // 사용자 정의 예외

import java.sql.SQLException;
import java.util.List;

public interface InboundService {
  Long requestInbound(InboundRequestDTO requestDto) throws SQLException, TransactionException;
  void modifyPendingInbound(InboundRequestDTO requestDto, String memberId) throws SQLException, TransactionException;
  void cancelPendingInbound(Long inboundRequestId, String memberId) throws SQLException, TransactionException;
  void approveInbound(Long inboundRequestId, String managerId) throws SQLException, TransactionException;
  void rejectInbound(Long inboundRequestId, String managerId) throws SQLException, TransactionException;
  InboundRequestDTO getInboundRequestDetail(Long inboundRequestId) throws SQLException, TransactionException;
  List<InboundRequestDTO> getInboundRequestsByMember(String memberId, InboundStatus status) throws SQLException;
  List<InboundRequestDTO> getAllInboundRequestsByStatus(InboundStatus status) throws SQLException;
}