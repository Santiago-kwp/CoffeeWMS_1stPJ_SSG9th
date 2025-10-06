package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundViewDeprecated;

public class RejectInboundCommand extends AbstractInboundCommand {

    public RejectInboundCommand(InboundService inboundService, InboundViewDeprecated inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) {
        try {
            inboundView.displayRejectInboundHeader();
            Long inboundRequestId = inboundView.getInboundRequestIdFromUser();
            inboundService.rejectInbound(inboundRequestId, user.getId()); // 관리자 ID 전달
            inboundView.displaySuccess("입고 요청(ID: " + inboundRequestId + ")을 거절 처리했습니다.");
        } catch (Exception e) {
            inboundView.displayError("입고 요청 거절 중 오류: " + e.getMessage());
        }
    }
}