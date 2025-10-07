package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.vo.InboundStatus;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundView;

import java.util.List;

public class RejectInboundCommand extends AbstractInboundCommand {

    public RejectInboundCommand(InboundService inboundService, InboundView inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) {
        try {
            inboundView.displayRejectInboundHeader();

            // 1. 거절 가능한 '대기' 상태의 요청 목록을 먼저 보여준다.
            List<InboundRequestDTO> pendingList = inboundService.getInboundRequestsByMember(user.getId(), InboundStatus.PENDING);
            inboundView.displayInboundRequestList(pendingList);

            // 1-1. 거절할 요청이 없으면 커맨드 종료
            if (pendingList.isEmpty()) {
                inboundView.displayMessage("수정 가능한 '대기' 상태의 입고 요청이 없습니다.");
                return;
            }

            // 2. 거절할 입고 요청 ID 입력 받기
            Long inboundRequestId = inboundView.getInboundRequestIdFromUser();

            if (inboundRequestId == null) {
                inboundView.displayMessage("거절 작업을 취소했습니다.");
                return; // 작업 중단
            }

            inboundService.rejectInbound(inboundRequestId, user.getId()); // 관리자 ID 전달
            inboundView.displaySuccess("입고 요청(ID: " + inboundRequestId + ")을 거절 처리했습니다.");
        } catch (Exception e) {
            inboundView.displayError("입고 요청 거절 중 오류: " + e.getMessage());
        }
    }
}