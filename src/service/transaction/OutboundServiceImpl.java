package service.transaction;

import constant.transaction.ErrorCode;
import domain.transaction.OutboundItem;
import domain.transaction.OutboundRequest;
import exception.transaction.OutboundRequestException;
import exception.transaction.TransactionException;
import model.transaction.OutboundDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OutboundServiceImpl implements OutboundService{

    private OutboundDao outboundDao;

    // 연결 객체를 받고, DAO 메소드를 사용하기 위해 의존성 주입
    public OutboundServiceImpl() {
        this.outboundDao = new OutboundDao();
    }

    @Override
    public void submitOutboundRequest(OutboundRequest request) {
        System.out.println("서비스: 출고 요청서를 제출 중입니다...");
        boolean success = outboundDao.callSubmitOutboundRequestProcedure(
                request.getOutboundRequestId(),
                request.getMemberId(),
                request.getManagerId(),
                request.getJsonItems()
        );
        if (success) {
            System.out.println("서비스: 입고 요청이 성공적으로 수행되었습니다.");
        } else {
            System.out.println("서비스: 입고 요청이 실패했습니다.");
        }

    }

    @Override
    public List<OutboundItem> getUnapprovedRequestsByMember(String memberId) {
        // DAO 메서드를 호출하여 미승인 요청 목록을 가져옵니다.
        return outboundDao.getUnapprovedRequestsByMember(memberId);
    }

    @Override
    public List<OutboundItem> getApprovedRequestsByMember(String memberId) {
        // DAO 메서드를 호출하여 미승인 요청 목록을 가져옵니다.
        return outboundDao.getApprovedRequestsByMember(memberId);
    }

    @Override
    public Map<String, Integer> getUnapprovedRequests() {
        return outboundDao.getUnapprovedRequests();

    }

    @Override
    public void processOutboundRequest(String outboundRequestId, String jsonItems) {
        try {
            outboundDao.processOutboundRequest(outboundRequestId, jsonItems);
        } catch (SQLException e) {
            throw new OutboundRequestException("출고 요청 승인 중 예외 발생!", e);
        }
    }

    @Override
    public List<OutboundItem> getOutboundRequestItems(String memberId, String requestId) {
        try {
            return outboundDao.getOutboundRequestItems(memberId, requestId);
        } catch (SQLException e) {
            throw new OutboundRequestException("출고 요청 건 조회 중 예외 발생!", e);
        }
    }

    @Override
    public Map<String, Integer> getMemberApprovedOutboundRequests() {
        return outboundDao.getMemberApprovedOutboundRequests();
    }


}
