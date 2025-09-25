package controller.support;

import domain.support.Notice;
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
    public void mainMenu() throws IOException {
        TheEnd:
        while (true) {
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("                          Welcome to the Coffee World ");
            System.out.println("----------------------------------<< 공지사항 >>----------------------------------");
            NoticeDaoImpl noticeDAO = new NoticeDaoImpl();
            // -- 공지사항 메인화면 출력
            List<Notice> noticeListMain = noticeDAO.readNoticeMain();
            for (Notice notice : noticeListMain) {
                notice.setNoticeDate(notice.getNoticeDate());
                notice.setNoticeTitle(notice.getNoticeTitle());
                System.out.printf("%S %S", notice.getNoticeDate(), notice.getNoticeTitle());
                System.out.println();
            }
            System.out.println("\n---------------------------------<< 메인 메뉴 >>---------------------------------");
            System.out.println("메인 메뉴: 1.로그인 | 2.공지사항 | 3.종료");
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
                    // 로그인
                    csMenu();
                    break;
                case 2:
                    noticeMenu.memberNoticeMenu();
                    break;
                case 3:
                    System.out.println("[종료]");
                    break TheEnd;
            }
        }
    }

    // 고객센터 ------------------------------------------------------------------------------------------------------
    public void csMenu() throws IOException {
        String managerId = "manager1"; // -> String userId = User.getUserId()
        String memberId = "member1"; // -> String userId = User.getUserId()
        TheEndCS:
        while (true) {
            System.out.println("\n---------------------------------<< 고객 센터 >>---------------------------------");
            System.out.println("메인 메뉴: 1.공지사항 | 2.1:1문의 | 3.FAQ | 4.뒤로가기");
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
                    noticeMenu.memberNoticeMenu();
//                    noticeMenu.managerNoticeMenu(managerId);
                    break;
                case 2:
                    inquiryMenu.memberInquiryMenu(memberId);
//                    inquiryMenu.managerInquiryMenu(managerId);
                    break;
                case 3:
                    faqMenu.memberFaqMenu();
//                    faqMenu.managerFaqMenu(managerId);
                    break;
                case 4:
                    System.out.println("[뒤로가기]");
                    break TheEndCS;
            }
        }
    }
}
