package controller.support;

import domain.support.Faq;
import domain.support.Notice;
import model.support.FaqDAO;
import model.support.NoticeDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class FaqMenu {
    FaqDAO faqDAO;
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 창고관리자 & 회원 <FAQ> 메뉴 ------------------------------------------------------------------------------------------------------
    public void memberFaqMenu() throws IOException {
        KIKI:
        while (true) {
            faqReadAll();

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
            faqReadAll();

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
                    Faq faq = faqDataInput();
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
                Faq faq = faqDataUpdate(readChoice);
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

    //######################################################################################################################
    // FAQ 데이터 입력 -------------------------------------------------------------------------------------------------
    public Faq faqDataInput() throws IOException {
        Faq faq = new Faq();
        faqDAO = new FaqDAO();
        System.out.println("\n[FAQ 생성]");
        line();
        System.out.println("[FAQ 카테고리 목록]");
        faqDAO.readFaqCategory();
        line();
        System.out.println("카테고리 번호를 선택해주세요.");
        System.out.print("> ");
        Integer categoryId = Integer.parseInt(input.readLine());
        faq.setFaqCategoryId(categoryId);

        System.out.println("질문 입력");
        System.out.print("> ");
        String question = input.readLine();
        faq.setFaqQuestion(question);

        System.out.println("답안 입력");
        System.out.print("> ");
        String reply = input.readLine();
        faq.setFaqReply(reply);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("관리자 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        faq.setFaqManagerId(managerId);

        return faq;
    }

    // FAQ 데이터 수정 -------------------------------------------------------------------------------------------------
    public Faq faqDataUpdate(Integer readChoice) throws IOException {
        Faq faq = new Faq();

        faq.setFaqId(readChoice);

        System.out.println("\n[FAQ 수정]");
        line();
        System.out.println("[FAQ 카테고리 목록]");
        faqDAO.readFaqCategory();
        line();
        System.out.println("카테고리 번호를 선택해주세요.");
        System.out.print("> ");
        Integer categoryId = Integer.parseInt(input.readLine());
        faq.setFaqCategoryId(categoryId);

        System.out.println("질문 입력");
        System.out.print("> ");
        String question = input.readLine();
        faq.setFaqQuestion(question);

        System.out.println("답안 입력");
        System.out.print("> ");
        String reply = input.readLine();
        faq.setFaqReply(reply);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("관리자 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        faq.setFaqManagerId(managerId);

        return faq;
    }

    // FAQ 전체 조회 ---------------------------------------------------------------------------------------------------
    public void faqReadAll() {
        faqDAO = new FaqDAO();
        System.out.println("\n-------------------------------<< FAQ 전체 목록 >>--------------------------------");
        System.out.printf("%-5S\t | %-10S\t | %-13S\t | %-18S\t | %-15S\t\n", "NO", "날짜", "카테고리", "질문", "답변");
        line();
        List<Faq> readAll = faqDAO.readFaqAll();
        for (Faq faq : readAll) {

            // 내용을 10글자만 출력
            String q = faq.getFaqQuestion();
            if (q.length() > 10) q = q.substring(0, 10);

            String r = faq.getFaqReply();
            if (r.length() > 10) r = r.substring(0, 10);

            System.out.printf("%-5S\t | %-10S\t | %-13S\t | %-15S\t | %-15S\t", faq.getFaqId(), faq.getFaqDate(), faq.getFaqCategoryName(), q, r);
            System.out.println();
        }
    }

    // 라인 -------------------------------------------------------------------------------------------------------------
    public void line() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
