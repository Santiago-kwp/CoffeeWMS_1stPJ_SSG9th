package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.ValidCheck;
import domain.user.Manager;
import domain.user.Member;
import exception.support.InputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CSMenu {
    ValidCheck validCheck = new ValidCheck();
    NoticeMenu noticeMenu = new NoticeMenu();
    InquiryMenu inquiryMenu = new InquiryMenu();
    FaqMenu faqMenu = new FaqMenu();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public void memberCSMenu(Member member) {
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
//            String choice = exceptionHandler.csMenuExp();

            switch (choice) {
                case "1":
                    noticeMenu.memberNoticeMenu();
                    break;
                case "2":
                    inquiryMenu.memberInquiryMenu(member.getId());
                    break;
                case "3":
                    faqMenu.memberFaqMenu();
                    break;
                case "4":
                    System.out.println(BoardText.BACK.getMessage());
                    break TheEndCS;
            }
        }
    }

    public void managerCSMenu(Manager manager) {
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
                    noticeMenu.managerNoticeMenu(manager);
                    break;
                case "2":
                    try {
                        validCheck.managerCheck(manager);
                    } catch (InputException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    inquiryMenu.managerInquiryMenu(manager.getId());
                    break;
                case "3":
                    faqMenu.managerFaqMenu(manager);
                    break;
                case "4":
                    System.out.println(BoardText.BACK.getMessage());
                    break TheEndCS;
            }
        }
    }
}
