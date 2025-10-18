package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.transaction.Coffee;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.dto.InboundRequestItemDTO;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundView;
import domain.transaction.vo.InboundStatus;

import java.util.List;

public class ModifyInboundCommand extends AbstractInboundCommand {

    public ModifyInboundCommand(InboundService inboundService, InboundView inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) {
        try {
            inboundView.displayModifyInboundHeader();

            // 1. 수정 가능한 '대기' 상태의 요청 목록을 먼저 보여준다.
            List<InboundRequestDTO> pendingList = inboundService.getInboundRequestsByMember(user.getId(), InboundStatus.PENDING);
            inboundView.displayInboundRequestList(pendingList);

            // 1-1. 수정할 요청이 없으면 커맨드 종료
            if (pendingList.isEmpty()) {
                inboundView.displayMessage("수정 가능한 '대기' 상태의 입고 요청이 없습니다.");
                return;
            }

            // 2. 수정할 입고 요청 ID 입력 받기
            Long inboundRequestId = inboundView.getInboundRequestIdFromUser();

            if (inboundRequestId == null) {
                inboundView.displayMessage("수정 작업을 취소했습니다.");
                return; // 작업 중단
            }


            // 3. 수정할 새로운 아이템 목록을 서비스 계층을 통해 주문 가능한 커피 목록 조회
            List<Coffee> coffeeList = inboundService.getAvailableCoffees();
            if (coffeeList.isEmpty()) {
                inboundView.displayError("주문 가능한 커피 상품이 없습니다.");
                return;
            }

            // 4. View에 커피 목록을 전달하여 사용자로부터 입고 항목 입력 받기

            List<InboundRequestItemDTO> newItems = inboundView.getInboundItemsFromUser(coffeeList);

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
