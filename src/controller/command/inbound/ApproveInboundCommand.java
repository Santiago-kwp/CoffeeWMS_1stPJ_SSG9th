package controller.command.inbound;

import controller.command.AbstractInboundCommand;
import domain.cargo.dto.LocationDTO;
import domain.transaction.Coffee;
import domain.transaction.dto.InboundRequestDTO;
import domain.user.User;
import service.cargo.LocationService;
import service.transaction.InboundService;
import view.transaction.InboundView;

import java.util.List;

public class ApproveInboundCommand extends AbstractInboundCommand {

    private final LocationService locationService;

    public ApproveInboundCommand(InboundService inboundService, InboundView inboundView, LocationService locationService) {
        super(inboundService, inboundView);
        this.locationService = locationService;
    }

    @Override
    public void execute(User user) {
        try {
            inboundView.displayApproveInboundHeader();

            // 1. 승인 대기 중인 입고 요청 목록을 먼저 조회한다.
            List<InboundRequestDTO> pendingList = inboundService.getPendingInboundListForAdmin();

            // 1-1. 커피 이름을 표시하기 위해 전체 커피 목록도 조회한다.
            List<Coffee> allCoffees = inboundService.getAvailableCoffees();

            // 1-2. 조회된 목록을 View를 통해 출력한다.
            inboundView.displayPendingInboundList(pendingList, allCoffees);

            if (pendingList.isEmpty()) {
                return; // 대기 중인 요청이 없으면 작업 중단
            }

            // 2. 승인할 입고 요청 ID 입력 받기
            Long inboundRequestId = inboundView.getInboundRequestIdFromUser();

            if (inboundRequestId == null) {
                inboundView.displayMessage("승인 작업을 취소했습니다.");
                return; // 작업 중단
            }


            // 2-1. (추가) 선택된 요청의 상세 정보를 가져온다 (확인 화면에 필요)
            InboundRequestDTO selectedRequest = inboundService.getInboundRequestDetail(inboundRequestId);

            // 3. LocationService를 통해 할당 가능한 위치 목록 조회
            List<LocationDTO> locations = locationService.getAvailableLocations();
            if (locations.isEmpty()) {
                inboundView.displayError("할당 가능한 위치가 없습니다. 먼저 위치를 등록해주세요.");
                return;
            }



            // 4. View를 통해 관리자에게 위치 선택 받기
            String selectedLocationPlaceId = inboundView.getLocationChoiceFromUser(locations);

            if (selectedLocationPlaceId == null) {
                inboundView.displayMessage("승인 작업을 취소했습니다.");
                return; // 작업 중단
            }
            // 선택된 LocationDTO 객체를 찾는다 (확인 화면에 필요)
            LocationDTO chosenLocation = locations.stream()
                    .filter(loc -> loc.getLocationPlaceId().equals(selectedLocationPlaceId))
                    .findFirst()
                    .orElse(null);
            if (chosenLocation == null) { // 혹시 모를 예외 처리
                inboundView.displayError("선택된 위치 정보를 찾을 수 없습니다.");
                return;
            }

            // 5.  최종 확인 절차
            // ==========================================================
            inboundView.displayApproveConfirmation(selectedRequest, chosenLocation, allCoffees);
            boolean confirmed = inboundView.getConfirmationFromUser();

            if (!confirmed) {
                inboundView.displayMessage("입고 승인 작업이 취소되었습니다.");
                return; // 'n' 입력 시 작업 중단
            }

            // 6. 수정된 Service 메서드 호출
            inboundService.approveInbound(inboundRequestId, user.getId(), selectedLocationPlaceId);

            inboundView.displaySuccess("입고 요청(ID: " + inboundRequestId + ")을 승인하고 재고 위치를 할당했습니다.");

        } catch (Exception e) {
            inboundView.displayError("입고 요청 승인 중 오류: " + e.getMessage());
        }
    }
}