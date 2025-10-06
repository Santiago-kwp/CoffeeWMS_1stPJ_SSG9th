package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.dto.InboundRequestItemDTO;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundViewDeprecated;

import java.util.List;

public class ModifyInboundCommand extends AbstractInboundCommand {

    public ModifyInboundCommand(InboundService inboundService, InboundViewDeprecated inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) {
        try {
            inboundView.displayModifyInboundHeader();

            Long inboundRequestId = inboundView.getInboundRequestIdFromUser();
            List<InboundRequestItemDTO> newItems = inboundView.getInboundItemsFromUser();

            InboundRequestDTO requestDto = new InboundRequestDTO();
            requestDto.setInboundRequestId(inboundRequestId);
            requestDto.setItems(newItems);

            inboundService.modifyPendingInbound(requestDto, user.getId()); // 파라미터 user의 ID 전달
            inboundView.displaySuccess("입고 요청(ID: " + inboundRequestId + ")이 성공적으로 수정되었습니다.");

        } catch (Exception e) {
            inboundView.displayError("입고 요청 수정 중 오류: " + e.getMessage());
        }
    }
}
