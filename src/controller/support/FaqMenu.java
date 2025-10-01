package controller.support;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import constant.support.ValidCheck;
import domain.support.Board;
import domain.support.Faq;
import domain.user.Manager;
import exception.support.InputException;
import exception.support.NotFoundException;
import model.support.dao.FaqDAO;
import model.support.dao.daoImpl.FaqDaoImpl;
import service.support.csService.CSOption;
import service.support.inputService.BoardInput;
//import service.support.inputService.FaqInput;
import service.support.csService.csServiceImpl.CSOptionImpl;
import service.support.inputService.inputImpl.FaqInputImpl;
import service.support.readService.FaqRead;
import service.support.readService.readImpl.FaqReadImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FaqMenu {
    ValidCheck validCheck = new ValidCheck();
    FaqDAO faqDAO = new FaqDaoImpl();
    BoardInput faqInput = new FaqInputImpl();
    FaqRead faqRead = new FaqReadImpl();
    CSOption csOption = new CSOptionImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 회원 <FAQ> 메뉴 ------------------------------------------------------------------------------------------------------
    public void memberFaqMenu() {
        KIKI:
        while (true) {
            faqRead.faqReadAll();

            System.out.print(BoardText.FAQ_MENU_SIMPLE.getMessage());

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
                    System.out.print(BoardText.FAQ_INSERT_ID.getMessage());

                    int readChoice = 0;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
                        memberFaqMenu();
                    } catch (NumberFormatException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
                        memberFaqMenu();
                    }

                    System.out.println(BoardText.LINE.getMessage());

                    System.out.println(BoardText.FAQ_CHOICE.getMessage());

                    Faq oneFaq = faqDAO.readFaqOne(readChoice);

                    try {
                        validCheck.isValidNotFoundFaq(oneFaq);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    faqRead.faqReadOne(oneFaq);

                    csOption.backOption();

                    break;

                case "2":
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
            }
        }
    }

    // 총관리자 <FAQ> 메뉴 ------------------------------------------------------------------------------------------------------
    public void managerFaqMenu(Manager manager) {
        KIKI:
        while (true) {
            String managerId = manager.getId();

            faqRead.faqReadAll();

            System.out.print(BoardText.FAQ_MENU.getMessage());

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
                    try {
                        validCheck.managerCheck(manager);
                    } catch (InputException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    Board board = faqInput.dataInput(managerId);
                    Boolean pass;

                    if (board instanceof Faq faq) {
                        pass = faqDAO.createFaq(faq);
                    } else {
                        System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
                        break;
                    }

                    if (pass) System.out.println(BoardText.FAQ_CREATE_SUCCESS.getMessage());
                    else System.out.println(BoardText.FAQ_CREATE_FAILURE.getMessage());
                    break;

                case "2":
                    System.out.print(BoardText.FAQ_INSERT_ID.getMessage());

                    int readChoice = 0;
                    try {
                        readChoice = Integer.parseInt(input.readLine());
                    } catch (IOException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
                        managerFaqMenu(manager);
                    } catch (NumberFormatException e) {
                        System.out.println(BoardErrorCode.NOT_INPUT_NUMBER.getMessage());
                        managerFaqMenu(manager);
                    }

                    System.out.println(BoardText.LINE.getMessage());

                    System.out.println(BoardText.FAQ_CHOICE.getMessage());

                    Faq oneFaq = faqDAO.readFaqOne(readChoice);

                    try {
                        validCheck.isValidNotFoundFaq(oneFaq);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    faqRead.faqReadOne(oneFaq);

                    faqDetailMenu(readChoice, manager);
                    break;

                case "3":
                    System.out.println(BoardText.BACK.getMessage());
                    break KIKI;
            }
        }
    }

    //  총관리자 <FAQ> 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void faqDetailMenu(Integer readChoice, Manager manager) {

        String managerId = manager.getId();

        System.out.print(BoardText.FAQ_DETAIL_MENU.getMessage());

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
                try {
                    validCheck.managerCheck(manager);
                } catch (InputException e) {
                    System.out.println(e.getMessage());
                    break;
                }

                Board board = faqInput.dataUpdate(readChoice, managerId);
                Boolean update;

                if (board instanceof Faq faq) {
                    update = faqDAO.updateFaq(faq);
                } else {
                    System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
                    break;
                }

                if (update) System.out.println(BoardText.FAQ_UPDATE_SUCCESS.getMessage());
                else System.out.println(BoardText.FAQ_UPDATE_FAILURE.getMessage());
                break;

            case "2":
                try {
                    validCheck.managerCheck(manager);
                } catch (InputException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                boolean delete = faqDAO.deleteFaq(readChoice, managerId);

                if (delete) System.out.println(BoardText.FAQ_DELETE_SUCCESS.getMessage());
                else System.out.println(BoardText.FAQ_DELETE_FAILURE.getMessage());
                break;

            case "3":
                System.out.println(BoardText.BACK.getMessage());
                break;
        }
    }
}
