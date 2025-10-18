package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.transaction.Coffee;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.dto.InboundRequestItemDTO;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundView;

import java.time.LocalDate;
import java.util.List;

public class RequestInboundCommand extends AbstractInboundCommand {


    public RequestInboundCommand(InboundService inboundService, InboundView inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) {
        try {
            inboundView.displayRequestInboundHeader();

            // 1. 서비스 계층을 통해 주문 가능한 커피 목록 조회
            List<Coffee> coffeeList = inboundService.getAvailableCoffees();
            if (coffeeList.isEmpty()) {
                inboundView.displayError("주문 가능한 커피 상품이 없습니다.");
                return;
            }

            // 2. View에 커피 목록을 전달하여 사용자로부터 입고 항목 입력 받기
            List<InboundRequestItemDTO> items = inboundView.getInboundItemsFromUser(coffeeList);

            // 3.  최종 확인 절차
            inboundView.displayRequestConfirmation(items, coffeeList); // 선택 내역 보여주기
            boolean confirmed = inboundView.getConfirmationFromUser(); // y/n 입력 받기

            if (!confirmed) {
                inboundView.displayMessage("입고 요청이 취소되었습니다.");
                return; // 'n'을 입력하면 커맨드 실행을 중단하고 메뉴로 복귀
            }

            // 4. View를 통해 입고 요청 날짜 입력 받기
            LocalDate requestDate = inboundView.getRequestDateFromUser();

            // 5. DTO 생성 및 InboundService 호출
            InboundRequestDTO requestDto = new InboundRequestDTO();
            requestDto.setMemberId(user.getId());
            requestDto.setRequestDate(requestDate);
            requestDto.setItems(items);

            Long newInboundRequestId = inboundService.requestInbound(requestDto);
            inboundView.displaySuccess("입고 요청이 성공적으로 등록되었습니다. (요청 ID: " + newInboundRequestId + ")");

        } catch (Exception e) {
            inboundView.displayError("입고 요청 생성 중 오류: " + e.getMessage());
        }
    }
}