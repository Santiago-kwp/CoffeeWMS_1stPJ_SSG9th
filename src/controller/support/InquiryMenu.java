package controller.support;

import domain.support.Inquiry;
import model.support.service.dao.InquiryDAO;
import model.support.service.dao.daoImpl.InquiryDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class InquiryMenu {
    InquiryDAO inquiryDAO = new InquiryDaoImpl();
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
            inquiryReadAll();

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
                    Inquiry oneInquiry = inquiryDAO.readInquiryManagerOne(readChoice);
                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n", "작성일", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "내용", oneInquiry.getInquiryContent());
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
            inquiryReadAll();

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
                    Inquiry inquiry = inquiryDataInput();
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
                    Inquiry oneInquiry = inquiryDAO.readInquiryManagerOne(readChoice);
                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n", "작성일", oneInquiry.getInquiryDate(), "카테고리", oneInquiry.getInquiryCategoryName(), "내용", oneInquiry.getInquiryContent());
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
                Inquiry inquiry = managerInquiryDataUpdate(readChoice);
                boolean update = inquiryDAO.updateInquiryManager(inquiry);
                if (update) System.out.println("문의사항이 성공적으로 답변 완료되었습니다.");
                else {
                    System.out.println("답변 작성 실패, 다시 시도 부탁드립니다. ");
                }
                break;
            case 2:
                System.out.println("관리자 아이디");
                System.out.print("> ");
                String managerId = input.readLine();

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
                Inquiry inquiry = memberInquiryDataUpdate(readChoice);
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

    //######################################################################################################################
    // 1:1 문의 데이터 입력 -------------------------------------------------------------------------------------------------
    public Inquiry inquiryDataInput() throws IOException {
        Inquiry inquiry = new Inquiry();
        InquiryDaoImpl inquiryDAO = new InquiryDaoImpl();

        System.out.println("\n[1:1 문의 생성]");
        line();
        System.out.println("[문의 카테고리 목록]");
        inquiryDAO.readFaqCategory();
        line();
        System.out.println("카테고리 번호를 선택해주세요.");
        System.out.print("> ");
        Integer categoryId = Integer.parseInt(input.readLine());
        inquiry.setInquiryCategoryId(categoryId);

        System.out.println("내용 입력");
        System.out.print("> ");
        String content = input.readLine();
        inquiry.setInquiryContent(content);

        // 회원 아이디 가져오기 (임시)
        System.out.println("회원 아이디");
        System.out.print("> ");
        String memberId = input.readLine();

        inquiry.setInquiryMemberId(memberId);

        return inquiry;
    }

    // 회원 1:1 문의 데이터 수정 -------------------------------------------------------------------------------------------------
    public Inquiry memberInquiryDataUpdate(Integer readChoice) throws IOException {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println("\n[1:1 문의 수정]");
        line();
        System.out.println("[문의 카테고리 목록]");
        inquiryDAO.readFaqCategory();
        line();
        System.out.println("카테고리 번호를 선택해주세요.");
        System.out.print("> ");
        Integer categoryId = Integer.parseInt(input.readLine());
        inquiry.setInquiryCategoryId(categoryId);

        System.out.println("내용 입력");
        System.out.print("> ");
        String content = input.readLine();
        inquiry.setInquiryContent(content);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("회원 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        inquiry.setInquiryManagerId(managerId);

        return inquiry;
    }

    // 총관리자 1:1 문의 데이터 수정 -------------------------------------------------------------------------------------------------
    public Inquiry managerInquiryDataUpdate(Integer readChoice) throws IOException {
        Inquiry inquiry = new Inquiry();

        inquiry.setInquiryId(readChoice);

        System.out.println("\n[1:1 문의 수정]");

        System.out.println("답변 입력");
        System.out.print("> ");
        String content = input.readLine();
        inquiry.setReplyContent(content);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("관리자 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        inquiry.setInquiryManagerId(managerId);

        return inquiry;
    }

    // 1:1문의 전체 조회 ---------------------------------------------------------------------------------------------------
    public void inquiryReadAll() {
        inquiryDAO = new InquiryDaoImpl();
        System.out.println("\n------------------------------<< 1:1문의 전체 목록 >>------------------------------");
        System.out.printf("%-5S\t | %-10S\t | %-12S\t | %-15S\t | %-10S\t\n", "NO", "날짜", "카테고리", "문의 내용", "답변 상태");
        line();
        List<Inquiry> readAll = inquiryDAO.readInquiryManagerAll();
        for (Inquiry inquiry : readAll) {

            // 내용을 30글자만 출력
            String content = inquiry.getInquiryContent();
            if (content.length() > 10) content = content.substring(0, 10);

            String status = null;
            switch (inquiry.getInquiryStatus()) {
                case PENDING -> status = "답변 대기";
                case DONE -> status = "답변 완료";
            }
            System.out.printf("%-5S\t | %-10S\t | %-12S\t | %-15S\t | %-10S\t",
                    inquiry.getInquiryId(), inquiry.getInquiryDate(), inquiry.getInquiryCategoryName(), content, status);
            System.out.println();
        }
    }


    // 라인 -------------------------------------------------------------------------------------------------------------
    public void line() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
