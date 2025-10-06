package controller.transaction;

import controller.command.Command;
import domain.user.Manager;
import domain.user.Member;
import domain.user.User;
import view.transaction.InboundView;

import java.util.Map;

/**
 * 입고 관리 메뉴의 흐름을 제어하는 컨트롤러 클래스입니다.
 * 사용자 입력을 받아 적절한 Command를 실행시키는 Invoker 역할을 합니다.
 */
public class InboundController {

    private final InboundView inboundView;
    // 이 컨트롤러가 사용할 Command들을 Map 형태로 주입받습니다.
    private final Map<String, Command> commands;

    /**
     * 생성자를 통해 의존성을 주입받습니다.
     * @param inboundView 사용자 입출력을 담당하는 뷰
     * @param commands 메뉴 번호와 매핑된 커맨드 객체 맵
     */
    public InboundController(InboundView inboundView, Map<String, Command> commands) {
        this.inboundView = inboundView;
        this.commands = commands;
    }

    /**
     * 사용자의 종류에 따라 입고 관리 메인 메뉴를 보여주고 로직을 실행합니다.
     * @param user 현재 로그인한 사용자(Member 또는 Manager)
     */
    public void showInboundMenu(User user) {
        boolean keepRunning = true;
        while (keepRunning) {
            try {
                // 사용자의 종류에 따라 다른 메뉴를 보여줍니다.
                if (user instanceof Member) {
                    inboundView.displayMemberMenu();
                } else if (user instanceof Manager) {
                    inboundView.displayManagerMenu();
                }

                String choice = inboundView.getMenuChoiceFromUser(); // View에서 메뉴 번호 입력 받기

                if (choice.equals("0")) {
                    keepRunning = false; // 0 입력 시 루프 종료 (이전 메뉴로)
                    continue;
                }

                Command command = commands.get(choice);

                if (command != null) {
                    // 선택된 커맨드 실행. 커맨드는 현재 사용자 정보를 알아야 하므로 user 객체 전달
                    command.execute(user);
                } else {
                    inboundView.displayError("잘못된 메뉴 선택입니다. 다시 입력해주세요.");
                }
            } catch (Exception e) {
                // Command 실행 중 발생할 수 있는 모든 예외 처리
                inboundView.displayError("작업 중 예상치 못한 오류가 발생했습니다: " + e.getMessage());
            }
        }
    }
}