package controller.support;

import domain.support.Faq;
import model.support.service.dao.FaqDAO;
import model.support.service.dao.daoImpl.FaqDaoImpl;
import model.support.service.input.FaqInput;
import model.support.service.input.inputImpl.FaqInputImpl;
import model.support.service.read.FaqRead;
import model.support.service.read.readImpl.FaqReadImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FaqMenu {
    FaqDAO faqDAO = new FaqDaoImpl();
    FaqInput faqInput = new FaqInputImpl();
    FaqRead faqRead = new FaqReadImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 창고관리자 & 회원 <FAQ> 메뉴 ------------------------------------------------------------------------------------------------------
    public void memberFaqMenu() throws IOException {
        KIKI:
        while (true) {
            faqRead.faqReadAll();

            System.out.println("\n------------------------------<< FAQ 메뉴 >>------------------------------");
            System.out.println("FAQ 메뉴: 1.상세 조회 | 2.뒤로가기");
            System.out.print("메뉴 선택 > ");
            int choice = 0;
            try {
                choice = Integer.parseInt(input.readLine());
            } catch (IOException e) {
                System.out.println("입력도중 에러 발생");
            } catch (NumberFormatException e1) {
                System.out.println("숫자만 입력");
            } catch (Exception e2) {
                System.out.println("에러 발생");
            }
            switch (choice) {
                case 1:
                    System.out.println("FAQ 번호를 입력해주세요.");
                    System.out.print("> ");
                    int readChoice = Integer.parseInt(input.readLine());
                    line();
                    System.out.println("[선택하신 FAQ]");
                    Faq oneFaq = faqDAO.readFaqOne(readChoice);
                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n", "작성일", oneFaq.getFaqDate(), "카테고리", oneFaq.getFaqCategoryName(), "질문", oneFaq.getFaqQuestion(), "답변", oneFaq.getFaqReply());
                    faqDetailMenu(readChoice);
                    break;
                case 2:
                    System.out.println("[뒤로가기]");
                    break KIKI;
            }
        }
    }

    // 총관리자 <FAQ> 메뉴 ------------------------------------------------------------------------------------------------------
    public void managerFaqMenu() throws IOException {
        KIKI:
        while (true) {
            faqRead.faqReadAll();

            System.out.println("\n------------------------------<< FAQ 메뉴 >>------------------------------");
            System.out.println("FAQ 메뉴: 1.FAQ 생성 | 2.상세 조회 | 3.뒤로가기");
            System.out.print("메뉴 선택 > ");
            int choice = 0;
            try {
                choice = Integer.parseInt(input.readLine());
            } catch (IOException e) {
                System.out.println("입력도중 에러 발생");
            } catch (NumberFormatException e1) {
                System.out.println("숫자만 입력");
            } catch (Exception e2) {
                System.out.println("에러 발생");
            }
            switch (choice) {
                case 1:
                    Faq faq = faqInput.faqDataInput();
                    boolean pass = faqDAO.createFaq(faq);
                    if (pass) System.out.println("FAQ 성공적으로 생성되었습니다.");
                    else {
                        System.out.println("생성 실패, 다시 시도 부탁드립니다. ");
                    }
                    break;
                case 2:
                    System.out.println("FAQ 번호를 입력해주세요.");
                    System.out.print("> ");
                    int readChoice = Integer.parseInt(input.readLine());
                    line();
                    System.out.println("[선택하신 FAQ]");
                    Faq oneFaq = faqDAO.readFaqOne(readChoice);
                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n", "작성일", oneFaq.getFaqDate(), "카테고리", oneFaq.getFaqCategoryName(), "질문", oneFaq.getFaqQuestion(), "답변", oneFaq.getFaqReply());
                    faqDetailMenu(readChoice);
                    break;
                case 3:
                    System.out.println("[뒤로가기]");
                    break KIKI;
            }
        }
    }

    //  총관리자 <FAQ> 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void faqDetailMenu(Integer readChoice) throws IOException {
        System.out.println("\n------------------------------<< FAQ 상세 메뉴 >>------------------------------");
        System.out.println("FAQ 상세 메뉴: 1.수정 | 2.삭제 | 3.뒤로가기");
        System.out.print("메뉴 선택 > ");
        int choice = 0;
        try {
            choice = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            System.out.println("입력도중 에러 발생");
        } catch (NumberFormatException e1) {
            System.out.println("숫자만 입력");
        } catch (Exception e2) {
            System.out.println("에러 발생");
        }
        line();
        switch (choice) {
            case 1:
                Faq faq = faqInput.faqDataUpdate(readChoice);
                boolean update = faqDAO.updateFaq(faq);
                if (update) System.out.println("FAQ가 성공적으로 수정되었습니다.");
                else {
                    System.out.println("수정 실패, 다시 시도 부탁드립니다. ");
                }
                break;
            case 2:
                System.out.println("관리자 아이디");
                System.out.print("> ");
                String managerId = input.readLine();

                boolean delete = faqDAO.deleteFaq(readChoice, managerId);
                if (delete) System.out.println("FAQ가 성공적으로 삭제되었습니다.");
                else {
                    System.out.println("삭제 실패, 다시 시도 부탁드립니다.");
                }
                break;
            case 3:
                System.out.println("[뒤로가기]");
                break;
        }
    }

    // 라인 -------------------------------------------------------------------------------------------------------------
    public void line() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
