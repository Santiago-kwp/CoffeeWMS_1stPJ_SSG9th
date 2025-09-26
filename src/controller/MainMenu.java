package controller;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.MainMenuText;
import constant.support.ValidCheck;
import controller.support.NoticeMenu;
import controller.user.LoginMenu;
import domain.support.Notice;
import exception.support.InputException;
import model.support.dao.daoImpl.NoticeDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainMenu {
    ValidCheck validCheck = new ValidCheck();
    LoginMenu loginMenu = LoginMenu.getInstance();
    NoticeMenu noticeMenu = new NoticeMenu();
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
                    loginMenu.loginMenu();
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
}
