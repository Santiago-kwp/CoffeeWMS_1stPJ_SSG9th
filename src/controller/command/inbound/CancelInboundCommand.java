package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundViewDeprecated;

public class CancelInboundCommand extends AbstractInboundCommand {

    public CancelInboundCommand(InboundService inboundService, InboundViewDeprecated inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) {
        try {
            inboundView.displayCancelInboundHeader();
            Long inboundRequestId = inboundView.getInboundRequestIdFromUser();
            inboundService.cancelPendingInbound(inboundRequestId, user.getId()); // 파라미터 user의 ID 전달
            inboundView.displaySuccess("입고 요청(ID: " + inboundRequestId + ")이 성공적으로 취소되었습니다.");
        } catch (Exception e) {
            inboundView.displayError("입고 요청 취소 중 오류: " + e.getMessage());
        }
    }
}