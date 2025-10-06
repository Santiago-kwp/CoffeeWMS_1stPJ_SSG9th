package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundViewDeprecated;

public class ApproveInboundCommand extends AbstractInboundCommand {

    public ApproveInboundCommand(InboundService inboundService, InboundViewDeprecated inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) {
        try {
            inboundView.displayApproveInboundHeader();
            Long inboundRequestId = inboundView.getInboundRequestIdFromUser();
            inboundService.approveInbound(inboundRequestId, user.getId()); // 관리자 ID 전달
            inboundView.displaySuccess("입고 요청(ID: " + inboundRequestId + ")을 승인 처리했습니다.");
        } catch (Exception e) {
            inboundView.displayError("입고 요청 승인 중 오류: " + e.getMessage());
        }
    }
}