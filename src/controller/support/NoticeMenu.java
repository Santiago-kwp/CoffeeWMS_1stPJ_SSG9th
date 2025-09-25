package controller.support;

import constant.support.CSExceptionMessage;
import constant.support.CSMenuMessage;
import domain.support.Notice;
import exception.support.InputException;
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
    NoticeDAO noticeDAO = new NoticeDaoImpl();
    NoticeInput noticeInput = new NoticeInputImpl();
    NoticeRead noticeRead = new NoticeReadImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 메인 & 창고 관리자 & 회원 <공지사항> 메뉴 ---------------------------------------------------------------------------------------------
    public void memberNoticeMenu() {
        KI:
        while (true) {
            noticeRead.noticeReadAll();

            System.out.print(CSMenuMessage.NOTICE_MENU_SIMPLE.getMessage());

            int choice;
            try {
                choice = Integer.parseInt(input.readLine());
                if (choice <= 0 || choice > 2)
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
                    System.out.print(CSMenuMessage.NOTICE_INSERT_ID.getMessage());

                    int readChoice;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
                    }

                    System.out.println(CSMenuMessage.LINE.getMessage());

                    System.out.println(CSMenuMessage.NOTICE_CHOICE.getMessage());

                    Notice oneNotice = noticeDAO.readNoticeOne(readChoice);

                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n", "작성일",
                            oneNotice.getNoticeDate(), "제목", oneNotice.getNoticeTitle(), "내용", oneNotice.getNoticeContent());
                    break;

                case 2:
                    System.out.println(CSMenuMessage.BACK.getMessage());
                    break KI;
            }
        }
    }

    // 총관리자 <공지사항> 메뉴 ----------------------------------------------------------------------------------------------
    public void managerNoticeMenu(String managerId) {
        KIKI:
        while (true) {
            noticeRead.noticeReadAll();

            System.out.print(CSMenuMessage.NOTICE_MENU.getMessage());

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
                    Notice notice = noticeInput.noticeDataInput(managerId);

                    boolean pass = noticeDAO.createNotice(notice);

                    if (pass) System.out.println(CSMenuMessage.NOTICE_CREATE_SUCCESS.getMessage());
                    else {
                        System.out.println(CSMenuMessage.NOTICE_CREATE_FAILURE.getMessage());
                    }
                    break;

                case 2:
                    System.out.print(CSMenuMessage.NOTICE_INSERT_ID.getMessage());

                    int readChoice;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
                    }

                    System.out.println(CSMenuMessage.LINE.getMessage());

                    System.out.println(CSMenuMessage.NOTICE_CHOICE.getMessage());

                    Notice oneNotice = noticeDAO.readNoticeOne(readChoice);
                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                            "작성일", oneNotice.getNoticeDate(), "제목", oneNotice.getNoticeTitle(), "내용", oneNotice.getNoticeContent());
                    noticeDetailMenu(readChoice, managerId);
                    break;

                case 3:
                    System.out.println(CSMenuMessage.BACK.getMessage());
                    break KIKI;
            }
        }
    }

    // 총관리자 공지사항 상세 메뉴 --------------------------------------------------------------------------------------------
    public void noticeDetailMenu(Integer readChoice, String managerId) {
        System.out.print(CSMenuMessage.NOTICE_DETAIL_MENU.getMessage());

        int choice;
        try {
            choice = Integer.parseInt(input.readLine());
            if (choice <= 0 || choice > 3) System.out.println(CSExceptionMessage.NOT_INPUT_OPTION.getMessage());
        } catch (IOException e) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_IO.getMessage());
        } catch (NumberFormatException e1) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_NUMBER.getMessage());
        } catch (Exception e2) {
            throw new InputException(CSExceptionMessage.NOT_INPUT_ERROR.getMessage());
        }

        System.out.println(CSMenuMessage.LINE.getMessage());

        switch (choice) {
            case 1:
                Notice notice = noticeInput.noticeDataUpdate(readChoice, managerId);

                boolean update = noticeDAO.updateNotice(notice);

                if (update) System.out.println(CSMenuMessage.NOTICE_UPDATE_SUCCESS.getMessage());
                else System.out.println(CSMenuMessage.NOTICE_UPDATE_FAILURE.getMessage());
                break;

            case 2:
                boolean delete = noticeDAO.deleteNotice(readChoice, managerId);

                if (delete) System.out.println(CSMenuMessage.NOTICE_DELETE_SUCCESS.getMessage());
                else System.out.println(CSMenuMessage.NOTICE_DELETE_FAILURE.getMessage());
                break;

            case 3:
                System.out.println(CSMenuMessage.BACK.getMessage());
                break;
        }
    }
}
