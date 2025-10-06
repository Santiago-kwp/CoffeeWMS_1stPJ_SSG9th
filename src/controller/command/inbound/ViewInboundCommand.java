package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.transaction.dto.InboundRequestDTO;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundViewDeprecated;

public class ViewInboundCommand extends AbstractInboundCommand {

    public ViewInboundCommand(InboundService inboundService, InboundViewDeprecated inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) { // User 객체를 받지만, 이 로직에서는 사용하지 않음 (인터페이스 일관성)
        try {
            inboundView.displayViewInboundHeader();
            Long inboundRequestId = inboundView.getInboundRequestIdFromUser();
            InboundRequestDTO requestDto = inboundService.getInboundRequestDetail(inboundRequestId);
            inboundView.displayInboundRequestDetail(requestDto);
        } catch (Exception e) {
            inboundView.displayError("입고 요청 조회 중 오류: " + e.getMessage());
        }
    }
}