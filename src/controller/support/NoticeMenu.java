package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.ValidCheck;
import domain.support.Notice;
import exception.support.InputException;
import exception.support.NotFoundException;
import model.support.service.dao.NoticeDAO;
import model.support.service.dao.daoImpl.NoticeDaoImpl;
import model.support.service.inputService.NoticeInput;
import model.support.service.inputService.inputImpl.NoticeInputImpl;
import model.support.service.readService.NoticeRead;
import model.support.service.readService.readImpl.NoticeReadImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class NoticeMenu {
    ValidCheck validCheck = new ValidCheck();
    NoticeDAO noticeDAO = new NoticeDaoImpl();
    NoticeInput noticeInput = new NoticeInputImpl();
    NoticeRead noticeRead = new NoticeReadImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 메인 & 창고 관리자 & 회원 <공지사항> 메뉴 ---------------------------------------------------------------------------------------------
    public void memberNoticeMenu() {
        KI:
        while (true) {
            noticeRead.noticeReadAll();

            System.out.print(BoardText.NOTICE_MENU_SIMPLE.getMessage());

            String choice = null;
            try {
                choice = input.readLine();
                validCheck.isTwoMenuValid(choice);
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
            }

            switch (choice) {
                case "1":
                    System.out.print(BoardText.NOTICE_INSERT_ID.getMessage());

                    int readChoice = 0;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
                        memberNoticeMenu();
                    } catch (NumberFormatException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
                        memberNoticeMenu();
                    }

                    System.out.println(BoardText.LINE.getMessage());

                    System.out.println(BoardText.NOTICE_CHOICE.getMessage());

                    Notice oneNotice = noticeDAO.readNoticeOne(readChoice);

                    try {
                        validCheck.isValidNotFoundNotice(oneNotice);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                            BoardText.CREATE_DATE.getMessage(), oneNotice.getNoticeDate(),
                            BoardText.TITLE_.getMessage(), oneNotice.getNoticeTitle(),
                            BoardText.CONTENT_.getMessage(), oneNotice.getNoticeContent());
                    break;

                case "2":
                    System.out.println(BoardText.BACK.getMessage());
                    break KI;
            }
        }
    }

    // 총관리자 <공지사항> 메뉴 ----------------------------------------------------------------------------------------------
    public void managerNoticeMenu(String managerId) {
        KIKI:
        while (true) {
            noticeRead.noticeReadAll();

            System.out.print(BoardText.NOTICE_MENU.getMessage());

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
                    Notice notice = noticeInput.noticeDataInput(managerId);

                    boolean pass = noticeDAO.createNotice(notice);

                    if (pass) System.out.println(BoardText.NOTICE_CREATE_SUCCESS.getMessage());
                    else {
                        System.out.println(BoardText.NOTICE_CREATE_FAILURE.getMessage());
                    }
                    break;

                case "2":
                    System.out.print(BoardText.NOTICE_INSERT_ID.getMessage());

                    int readChoice;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
                        break;
                    }

                    System.out.println(BoardText.LINE.getMessage());

                    System.out.println(BoardText.NOTICE_CHOICE.getMessage());

                    Notice oneNotice = noticeDAO.readNoticeOne(readChoice);

                    try {
                        validCheck.isValidNotFoundNotice(oneNotice);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                            BoardText.CREATE_DATE.getMessage(), oneNotice.getNoticeDate(),
                            BoardText.TITLE_.getMessage(), oneNotice.getNoticeTitle(),
                            BoardText.CONTENT_.getMessage(), oneNotice.getNoticeContent());

                    noticeDetailMenu(readChoice, managerId);
                    break;

                case "3":
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
            }
        }
    }

    // 총관리자 공지사항 상세 메뉴 --------------------------------------------------------------------------------------------
    public void noticeDetailMenu(Integer readChoice, String managerId) {
        System.out.print(BoardText.NOTICE_DETAIL_MENU.getMessage());

        String choice = null;
        try {
            choice = input.readLine();
            validCheck.isThreeMenuValid(choice);
        } catch (InputException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
        }

        System.out.println(BoardText.LINE.getMessage());

        switch (choice) {
            case "1":
                Notice notice = noticeInput.noticeDataUpdate(readChoice, managerId);

                boolean update = noticeDAO.updateNotice(notice);

                if (update) System.out.println(BoardText.NOTICE_UPDATE_SUCCESS.getMessage());
                else System.out.println(BoardText.NOTICE_UPDATE_FAILURE.getMessage());
                break;

            case "2":
                boolean delete = noticeDAO.deleteNotice(readChoice, managerId);

                if (delete) System.out.println(BoardText.NOTICE_DELETE_SUCCESS.getMessage());
                else System.out.println(BoardText.NOTICE_DELETE_FAILURE.getMessage());
                break;

            case "3":
                System.out.println(BoardText.BACK.getMessage());
                break;
        }
    }
}
