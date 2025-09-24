package controller.support;

import domain.support.Notice;
import model.support.service.dao.NoticeDAO;
import model.support.service.dao.daoImpl.NoticeDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class NoticeMenu {
    NoticeDAO noticeDAO = new NoticeDaoImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 메인 & 창고 관리자 & 회원 <공지사항> 메뉴 ---------------------------------------------------------------------------------------------
    public void memberNoticeMenu() throws IOException {
        KI:
        while (true) {
            noticeReadAll();

            System.out.println("\n-------------------------------<< 공지사항 메뉴 >>-------------------------------");
            System.out.println("공지사항 메인 메뉴: 1.상세 조회 | 2.뒤로가기");
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
                    System.out.println("공지 번호를 입력해주세요.");
                    System.out.print("> ");
                    int readChoice = Integer.parseInt(input.readLine());
                    line();
                    System.out.println("[선택하신 공지]");
                    Notice oneNotice = noticeDAO.readNoticeOne(readChoice);
                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n", "작성일", oneNotice.getNoticeDate(), "제목", oneNotice.getNoticeTitle(), "내용", oneNotice.getNoticeContent());
                    break;
                case 2:
                    System.out.println("[뒤로가기]");
                    break KI;
            }
        }
    }

    // 총관리자 <공지사항> 메뉴 ----------------------------------------------------------------------------------------------
    public void managerNoticeMenu() throws IOException {
        KIKI:
        while (true) {
            noticeReadAll();

            System.out.println("\n------------------------------<< 공지사항 메뉴 >>------------------------------");
            System.out.println("공지사항 메뉴: 1.공지 생성 | 2.상세 조회 | 3.뒤로가기");
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
                    Notice notice = noticeDataInput();
                    boolean pass = noticeDAO.createNotice(notice);
                    if (pass) System.out.println("공지사항이 성공적으로 생성되었습니다.");
                    else {
                        System.out.println("생성 실패, 다시 시도 부탁드립니다. ");
                    }
                    break;
                case 2:
                    System.out.println("공지 번호를 입력해주세요.");
                    System.out.print("> ");
                    int readChoice = Integer.parseInt(input.readLine());
                    line();
                    System.out.println("[선택하신 공지]");
                    Notice oneNotice = noticeDAO.readNoticeOne(readChoice);
                    System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n", "작성일", oneNotice.getNoticeDate(), "제목", oneNotice.getNoticeTitle(), "내용", oneNotice.getNoticeContent());
                    noticeDetailMenu(readChoice);
                    break;
                case 3:
                    System.out.println("[뒤로가기]");
                    break KIKI;
            }
        }
    }

    // 총관리자 공지사항 상세 메뉴 --------------------------------------------------------------------------------------------
    public void noticeDetailMenu(Integer readChoice) throws IOException {
        System.out.println("\n------------------------------<< 공지사항 상세 메뉴 >>------------------------------");
        System.out.println("공지사항 상세 메뉴: 1.수정 | 2.삭제 | 3.뒤로가기");
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
                Notice notice = noticeDataUpdate(readChoice);
                boolean update = noticeDAO.updateNotice(notice);
                if (update) System.out.println("공지사항이 성공적으로 수정되었습니다.");
                else {
                    System.out.println("수정 실패, 다시 시도 부탁드립니다. ");
                }
                break;
            case 2:
                System.out.println("관리자 아이디");
                System.out.print("> ");
                String managerId = input.readLine();

                boolean delete = noticeDAO.deleteNotice(readChoice, managerId);
                if (delete) System.out.println("공지사항이 성공적으로 삭제되었습니다.");
                else {
                    System.out.println("삭제 실패, 다시 시도 부탁드립니다.");
                }
                break;
            case 3:
                System.out.println("[뒤로가기]");
                break;
        }
    }

    //##################################################################################################################
    // 공지사항 데이터 입력 -------------------------------------------------------------------------------------------------
    public Notice noticeDataInput() throws IOException {
        Notice notice = new Notice();
        System.out.println("\n[공지사항 생성]");
        System.out.println("제목 입력");
        System.out.print("> ");
        String title = input.readLine();
        notice.setNoticeTitle(title);

        System.out.println("내용 입력");
        System.out.print("> ");
        String content = input.readLine();
        notice.setNoticeContent(content);

        System.out.println("상단고정 여부");
        System.out.print("Y / N > ");
        Character C = input.readLine().charAt(0);
        boolean fixed = yesOrNo(C);
        notice.setNoticeFixed(fixed);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("관리자 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        notice.setNoticeManagerId(managerId);

        return notice;
    }

    // 공지사항 데이터 수정 -------------------------------------------------------------------------------------------------
    public Notice noticeDataUpdate(Integer readChoice) throws IOException {
        Notice notice = new Notice();

        notice.setNoticeId(readChoice);

        System.out.println("\n[공지사항 수정]");
        System.out.println("제목 입력");
        System.out.print("> ");
        String title = input.readLine();
        notice.setNoticeTitle(title);

        System.out.println("내용 입력");
        System.out.print("> ");
        String content = input.readLine();
        notice.setNoticeContent(content);

        System.out.println("상단고정 여부");
        System.out.print("Y / N > ");
        Character C = input.readLine().charAt(0);
        boolean fixed = yesOrNo(C);
        notice.setNoticeFixed(fixed);

        // 매니저 아이디 가져오기 (임시)
        System.out.println("관리자 아이디");
        System.out.print("> ");
        String managerId = input.readLine();
        notice.setNoticeManagerId(managerId);

        return notice;
    }

    // 공지사항 전체 출력 ---------------------------------------------------------------------------------------------------
    public void noticeReadAll() {
        noticeDAO = new NoticeDaoImpl();
        System.out.println("\n------------------------------<< 공지사항 전체 목록 >>------------------------------");
        System.out.printf("%-5S\t | %-10S\t | %-35S\t | %-10S\t\n", "NO", "날짜", "제목", "내용");
        line();
        List<Notice> readAll = noticeDAO.readNoticeAll();
        for (Notice notice : readAll) {

            // 내용을 30글자만 출력
            String content = notice.getNoticeContent();
            if (content.length() > 10) content = content.substring(0, 10);

            System.out.printf("%-5S\t | %-10S\t | %-30S\t | %-10S\t", notice.getNoticeId(), notice.getNoticeDate(), notice.getNoticeTitle(), content);
            System.out.println();
        }
    }

    // 예, 아니오 --------------------------------------------------------------------------------------------------------
    public boolean yesOrNo(Character c) {
        return switch (c) {
            case 'y', 'Y' -> true;
            case 'n', 'N' -> false;
            default -> false;
        };
    }

    // 라인 -------------------------------------------------------------------------------------------------------------
    public void line() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
