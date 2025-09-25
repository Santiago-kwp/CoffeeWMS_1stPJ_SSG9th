package controller.support;

import domain.support.Inquiry;
import model.support.service.dao.InquiryDAO;
import model.support.service.dao.daoImpl.InquiryDaoImpl;
import model.support.service.input.InquiryInput;
import model.support.service.input.inputImpl.InquiryInputImpl;
import model.support.service.read.InquiryRead;
import model.support.service.read.readImpl.InquiryReadImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InquiryMenu {
    InquiryDAO inquiryDAO = new InquiryDaoImpl();
    InquiryInput inquiryInput = new InquiryInputImpl();
    InquiryRead inquiryRead = new InquiryReadImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

//    public void inquiryMenu(User user) throws IOException {
//        if (user instanceof Manager manager){
//            managerInquiryMenu();
//        } else if (user instanceof Member member) {
//            memberInquiryMenu();
//        }
//    }

    // 총관리자 1:1문의 메뉴 -------------------------------------------------------------------------------------------------
    public void managerInquiryMenu() throws IOException {
        KI:
        while (true) {
            inquiryRead.inquiryReadAll();

            System.out.println("\n-------------------------------<< 1:1 문의 메뉴 >>-------------------------------");
            System.out.println("문의 메뉴: 1.상세 조회 | 2.뒤로가기");
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
                    System.out.println("문의 번호를 입력해주세요.");
                    System.out.print("> ");
                    int readChoice = Integer.parseInt(input.readLine());
                    line();
                    System.out.println("[선택하신 문의]");
                    String status = null;
                    Inquiry oneInquiry = inquiryDAO.readInquiryManagerOne(readChoice);
                    switch (oneInquiry.getInquiryStatus()){
                        case PENDING -> {
                            status = "답변 대기";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "문의", oneInquiry.getInquiryContent(), "답변 상태", status);
                        }
                        case DONE -> {
                            status = "답변 완료";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "문의", oneInquiry.getInquiryContent(), "답변 상태", status, "답변날짜", oneInquiry.getReplyDate(), "답변", oneInquiry.getReplyContent());
                        }
                    }
                    managerInquiryDetailMenu(readChoice);
                    break;
                case 2:
                    System.out.println("[뒤로가기]");
                    break KI;
            }
        }
    }

    // 회원 1:1문의 메뉴 -------------------------------------------------------------------------------------------------
    public void memberInquiryMenu() throws IOException {
        KI:
        while (true) {
            inquiryRead.inquiryReadAll();

            System.out.println("\n-------------------------------<< 1:1 문의 메뉴 >>-------------------------------");
            System.out.println("문의 메뉴: 1.문의 생성 | 2.상세 조회 | 3.뒤로가기");
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
                    Inquiry inquiry = inquiryInput.inquiryDataInput();
                    boolean pass = inquiryDAO.createInquiry(inquiry);
                    if (pass) System.out.println("문의사항이 성공적으로 생성되었습니다.");
                    else {
                        System.out.println("생성 실패, 다시 시도 부탁드립니다. ");
                    }
                    break;
                case 2:
                    System.out.println("문의 번호를 입력해주세요.");
                    System.out.print("> ");
                    int readChoice = Integer.parseInt(input.readLine());
                    line();
                    System.out.println("[선택하신 문의]");
                    String status = null;
                    Inquiry oneInquiry = inquiryDAO.readInquiryManagerOne(readChoice);
                    switch (oneInquiry.getInquiryStatus()){
                        case PENDING -> {
                            status = "답변 대기";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "문의", oneInquiry.getInquiryContent(), "답변 상태", status);
                        }
                        case DONE -> {
                            status = "답변 완료";
                            System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                                    "문의날짜", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "문의", oneInquiry.getInquiryContent(), "답변 상태", status, "답변날짜", oneInquiry.getReplyDate(), "답변", oneInquiry.getReplyContent());
                        }
                    }
                    memberInquiryDetailMenu(readChoice);
                    break;
                case 3:
                    System.out.println("[뒤로가기]");
                    break KI;
            }
        }
    }

    // 총관리자 공지사항 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void managerInquiryDetailMenu(Integer readChoice) throws IOException {
        System.out.println("\n------------------------------<< 1:1 문의 상세 메뉴 >>------------------------------");
        System.out.println("문의 상세 메뉴: 1.답변 | 2.삭제 | 3.뒤로가기");
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
                Inquiry inquiry = inquiryInput.managerInquiryDataUpdate(readChoice);
                boolean update = inquiryDAO.updateInquiryManager(inquiry);
                if (update) System.out.println("문의사항이 성공적으로 답변 완료되었습니다.");
                else {
                    System.out.println("답변 작성 실패, 다시 시도 부탁드립니다. ");
                }
                break;
            case 2:
                boolean delete = inquiryDAO.deleteInquiryManager(readChoice);
                if (delete) System.out.println("문의사항이 성공적으로 삭제되었습니다.");
                else {
                    System.out.println("삭제 실패, 다시 시도 부탁드립니다.");
                }
                break;
            case 3:
                System.out.println("[뒤로가기]");
                break;
        }
    }

    // 회원 공지사항 상세 메뉴 ---------------------------------------------------------------------------------------------------
    public void memberInquiryDetailMenu(Integer readChoice) throws IOException {
        System.out.println("\n------------------------------<< 1:1 문의 상세 메뉴 >>------------------------------");
        System.out.println("문의 상세 메뉴: 1.수정 | 2.삭제 | 3.뒤로가기");
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
                Inquiry inquiry = inquiryInput.memberInquiryDataUpdate(readChoice);
                boolean update = inquiryDAO.updateInquiryMember(inquiry);
                if (update) System.out.println("문의사항이 성공적으로 수정 완료되었습니다.");
                else {
                    System.out.println("수정 실패, 다시 시도 부탁드립니다. ");
                }
                break;
            case 2:
                System.out.println("회원 아이디");
                System.out.print("> ");
                String memberId = input.readLine();

                boolean delete = inquiryDAO.deleteInquiryMember(readChoice, memberId);
                if (delete) System.out.println("문의사항이 성공적으로 삭제되었습니다.");
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
