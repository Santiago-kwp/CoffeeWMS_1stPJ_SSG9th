package controller.support;

import constant.support.CSExceptionMessage;
import constant.support.CSMenuMessage;
import constant.support.MainMenuMessage;
import domain.support.Notice;
import exception.support.InputException;
import model.support.service.dao.daoImpl.NoticeDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CSMenu {
    NoticeMenu noticeMenu = new NoticeMenu();
    InquiryMenu inquiryMenu = new InquiryMenu();
    FaqMenu faqMenu = new FaqMenu();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 메인 페이지
    public void mainMenu() {
        TheEnd:
        while (true) {
            System.out.print(MainMenuMessage.MAIN_MENU.getMessage());
            NoticeDaoImpl noticeDAO = new NoticeDaoImpl();
            // -- 공지사항 메인화면 출력
            List<Notice> noticeListMain = noticeDAO.readNoticeMain();
            for (Notice notice : noticeListMain) {
                notice.setNoticeDate(notice.getNoticeDate());
                notice.setNoticeTitle(notice.getNoticeTitle());
                System.out.printf("%S %S", notice.getNoticeDate(), notice.getNoticeTitle());
                System.out.println();
            }
            System.out.print(MainMenuMessage.MAIN_MENU_OPTION.getMessage());
            int choice;
            try {
                choice = Integer.parseInt(input.readLine());
                if (choice <= 0 || choice > 3)
                    System.out.println(CSExceptionMessage.NOT_INPUT_OPTION.getMessage());
            } catch (IOException e) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
            } catch (NumberFormatException e1) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_NUMBER.getMessage());
            } catch (Exception e2) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_ERROR.getMessage());
            }
            switch (choice) {
                case 1:
                    // 로그인
                    csMenu();
                    break;
                case 2:
                    noticeMenu.memberNoticeMenu();
                    break;
                case 3:
                    System.out.println(CSMenuMessage.LINE.getMessage());
                    System.out.println(MainMenuMessage.MAIN_MENU_END.getMessage());
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
            System.out.print(CSMenuMessage.CS_MENU.getMessage());
            int choice;
            try {
                choice = Integer.parseInt(input.readLine());
                if (choice <= 0 || choice > 4)
                    System.out.println(CSExceptionMessage.NOT_INPUT_OPTION.getMessage());
            } catch (IOException e) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
            } catch (NumberFormatException e1) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_NUMBER.getMessage());
            } catch (Exception e2) {
                throw new InputException(CSExceptionMessage.NOT_INPUT_ERROR.getMessage());
            }
            switch (choice) {
                case 1:
//                    noticeMenu.memberNoticeMenu();
                    noticeMenu.managerNoticeMenu(managerId);
                    break;
                case 2:
//                    inquiryMenu.memberInquiryMenu(memberId);
                    inquiryMenu.managerInquiryMenu(managerId);
                    break;
                case 3:
//                    faqMenu.memberFaqMenu();
                    faqMenu.managerFaqMenu(managerId);
                    break;
                case 4:
                    System.out.println(CSMenuMessage.BACK.getMessage());
                    break TheEndCS;
            }
        }
    }
}
