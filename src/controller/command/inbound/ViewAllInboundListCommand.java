package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.vo.InboundStatus;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundViewDeprecated;

import java.util.List;

/**
 * 전체 입고 요청 목록을 상태별로 조회하는 커맨드입니다. (관리자용)
 */
public class ViewAllInboundListCommand extends AbstractInboundCommand {

    public ViewAllInboundListCommand(InboundService inboundService, InboundViewDeprecated inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) { // User 객체는 받지만, 이 로직에서는 사용되지 않음 (인터페이스 일관성)
        try {
            // 1. View를 통해 사용자로부터 조회할 상태 입력 받기
            InboundStatus status = inboundView.getStatusFromUser();

            // 2. Service 호출하여 전체 사용자의 특정 상태 입고 목록 가져오기
            List<InboundRequestDTO> dtoList = inboundService.getAllInboundRequestsByStatus(status);

            // 3. View를 통해 조회된 목록 출력
            inboundView.displayInboundRequestList(dtoList);

        } catch (Exception e) {
            inboundView.displayError("전체 입고 요청 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}