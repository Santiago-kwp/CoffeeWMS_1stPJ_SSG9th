package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.vo.InboundStatus;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundView;

import java.util.List;

/**
 * 현재 로그인한 회원의 입고 요청 목록을 상태별로 조회하는 커맨드입니다.
 */
public class ViewMemberInboundListCommand extends AbstractInboundCommand {

    public ViewMemberInboundListCommand(InboundService inboundService, InboundView inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) {
        try {
            // 1. View를 통해 사용자로부터 조회할 상태 입력 받기
            InboundStatus status = inboundView.getStatusFromUser();

            // 2. Service 호출하여 현재 사용자의 특정 상태 입고 목록 가져오기
            List<InboundRequestDTO> dtoList = inboundService.getInboundRequestsByMember(user.getId(), status);

            // 3. View를 통해 조회된 목록 출력
            inboundView.displayInboundRequestList(dtoList);

        } catch (Exception e) {
            inboundView.displayError("입고 요청 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}