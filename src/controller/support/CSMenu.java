package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.MainMenuText;
import constant.support.ValidCheck;
import domain.support.Notice;
import exception.support.InputException;
import model.support.service.dao.daoImpl.NoticeDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CSMenu {
    ValidCheck validCheck = new ValidCheck();
    NoticeMenu noticeMenu = new NoticeMenu();
    InquiryMenu inquiryMenu = new InquiryMenu();
    FaqMenu faqMenu = new FaqMenu();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 메인 페이지
    public void mainMenu() {
        TheEnd:
        while (true) {
            System.out.print(MainMenuText.MAIN_MENU.getMessage());
            NoticeDaoImpl noticeDAO = new NoticeDaoImpl();

            // -- 공지사항 메인화면 출력
            List<Notice> noticeListMain = noticeDAO.readNoticeMain();

            for (Notice notice : noticeListMain) {
                notice.setNoticeDate(notice.getNoticeDate());
                notice.setNoticeTitle(notice.getNoticeTitle());
                System.out.printf("%S %S", notice.getNoticeDate(), notice.getNoticeTitle());
                System.out.println();
            }

            System.out.print(MainMenuText.MAIN_MENU_OPTION.getMessage());

            String choice = null;
            try {
                choice = input.readLine();
                validCheck.isThreeMenuValid(choice);
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            }

            switch (choice) {
                case "1":
                    // 로그인
                    csMenu();
                    break;
                case "2":
                    noticeMenu.memberNoticeMenu();
                    break;
                case "3":
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(MainMenuText.MAIN_MENU_END.getMessage());
                    break TheEnd;
            }
        }
    }

    // 고객센터 ------------------------------------------------------------------------------------------------------
    public void csMenu() {
        String managerId = "manager1"; // -> String userId = User.getUserId()
        String memberId = "member1"; // -> String userId = User.getUserId()
        TheEndCS:
        while (true) {
            System.out.print(BoardText.CS_MENU.getMessage());

            String choice = null;
            try {
                choice = input.readLine();
                validCheck.isFourMenuValid(choice);
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            }

            switch (choice) {
                case "1":
                    noticeMenu.memberNoticeMenu();
//                    noticeMenu.managerNoticeMenu(managerId);
                    break;
                case "2":
                    inquiryMenu.memberInquiryMenu(memberId);
//                    inquiryMenu.managerInquiryMenu(managerId);
                    break;
                case "3":
                    faqMenu.memberFaqMenu();
//                    faqMenu.managerFaqMenu(managerId);
                    break;
                case "4":
                    System.out.println(BoardText.BACK.getMessage());
                    break TheEndCS;
            }
        }
    }
}
