package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.vo.InboundStatus;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundView;

import java.util.List;

public class CancelInboundCommand extends AbstractInboundCommand {

    public CancelInboundCommand(InboundService inboundService, InboundView inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) {
        try {
            inboundView.displayCancelInboundHeader();

            // 1. 취소 가능한 '대기' 상태의 요청 목록을 먼저 보여준다.
            List<InboundRequestDTO> pendingList = inboundService.getInboundRequestsByMember(user.getId(), InboundStatus.PENDING);
            inboundView.displayInboundRequestList(pendingList);

            // 1-1. 취소할 요청이 없으면 커맨드 종료
            if (pendingList.isEmpty()) {
                inboundView.displayMessage("취소 가능한 '대기' 상태의 입고 요청이 없습니다.");
                return;
            }

            // 2. 취소할 입고 요청 ID 입력 받기
            Long inboundRequestId = inboundView.getInboundRequestIdFromUser();

            if (inboundRequestId == null) {
                inboundView.displayMessage("수정 작업을 취소했습니다.");
                return; // 작업 중단
            }

            // 3. Service 호출
            inboundService.cancelPendingInbound(inboundRequestId, user.getId());

            inboundView.displaySuccess("입고 요청(ID: " + inboundRequestId + ")이 성공적으로 취소되었습니다.");
        } catch (Exception e) {
            inboundView.displayError("입고 요청 취소 중 오류: " + e.getMessage());
        }
    }
}