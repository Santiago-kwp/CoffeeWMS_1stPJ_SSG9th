package service.transaction;

import domain.transaction.OutboundItem;
import domain.transaction.OutboundRequest;
import model.transaction.OutboundDao;

import java.sql.SQLException;
import java.util.List;

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
}
