package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.transaction.dto.InboundRequestDTO;
import domain.transaction.vo.InboundStatus;
import domain.user.Member;
import domain.user.User;
import service.transaction.InboundService;
import view.transaction.InboundView;
import view.transaction.SearchFilterType;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class ViewInboundCommand extends AbstractInboundCommand {

    public ViewInboundCommand(InboundService inboundService, InboundView inboundView) {
        super(inboundService, inboundView);
    }

    @Override
    public void execute(User user) { // User 객체를 받지만, 이 로직에서는 사용하지 않음 (인터페이스 일관성)
        try {
            inboundView.displayViewInboundHeader();

            // 1.  어떤 상태를 조회할지 사용자에게 먼저 물어본다.
            InboundStatus statusToView = inboundView.getStatusFromUser();

            // 2. 조회 방식(전체/기간/월별) 선택
            SearchFilterType filterType = inboundView.getSearchFilterTypeFromUser();
            if (filterType == null) { // 사용자가 뒤로가기 선택
                inboundView.displayMessage("조회를 취소했습니다.");
                return;
            }

            // 3. 선택된 방식에 따라 서비스 호출 및 목록 가져오기
            List<InboundRequestDTO> requestList = Collections.emptyList(); // 빈 리스트로 초기화
            boolean isMember = user instanceof Member;

            switch (filterType) {
                case ALL:
                    requestList = isMember ?
                            inboundService.getInboundRequestsByMember(user.getId(), statusToView) :
                            inboundService.getAllInboundRequestsByStatus(statusToView);
                    break;
                case DATE_RANGE:
                    LocalDate[] dateRange = inboundView.getDateRangeFromUser();
                    if (dateRange != null) {
                        requestList = isMember ?
                                inboundService.getInboundRequestsByMemberAndDateRange(user.getId(), statusToView, dateRange[0], dateRange[1]) :
                                inboundService.getAllInboundRequestsByDateRange(statusToView, dateRange[0], dateRange[1]);
                    }
                    break;
                case BY_MONTH:
                    int[] yearMonth = inboundView.getYearMonthFromUser();
                    if (yearMonth != null) {
                        requestList = isMember ?
                                inboundService.getInboundRequestsByMemberAndMonth(user.getId(), statusToView, yearMonth[0], yearMonth[1]) :
                                inboundService.getAllInboundRequestsByMonth(statusToView, yearMonth[0], yearMonth[1]);
                    }
                    break;
            }

            // 4. 조회된 목록 출력
            inboundView.displayInboundRequestList(requestList);


            // 5. 목록이 있을 경우에만 상세 조회로 이어짐
            if (!requestList.isEmpty()) {
                inboundView.displayMessage("상세 조회할 요청 ID를 입력하세요.");
                Long inboundRequestId = inboundView.getInboundRequestIdFromUser();
                if (inboundRequestId != null) { // 상세 조회도 취소 가능
                    InboundRequestDTO requestDto = inboundService.getInboundRequestDetail(inboundRequestId);
                    inboundView.displayInboundRequestDetail(requestDto);
                }
            }

        } catch (Exception e) {
            inboundView.displayError("입고 요청 조회 중 오류: " + e.getMessage());
        }
    }
}