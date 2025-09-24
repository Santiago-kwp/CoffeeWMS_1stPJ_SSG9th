package controller.support;

import domain.support.Notice;
import model.support.service.dao.NoticeDAO;
import model.support.service.dao.daoImpl.NoticeDaoImpl;
import model.support.service.input.NoticeInput;
import model.support.service.input.inputImpl.NoticeInputImpl;
import model.support.service.read.NoticeRead;
import model.support.service.read.readImpl.NoticeReadImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class NoticeMenu {
    NoticeDAO noticeDAO = new NoticeDaoImpl();
    NoticeInput noticeInput = new NoticeInputImpl();
    NoticeRead noticeRead = new NoticeReadImpl();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 메인 & 창고 관리자 & 회원 <공지사항> 메뉴 ---------------------------------------------------------------------------------------------
    public void memberNoticeMenu() throws IOException {
        KI:
        while (true) {
            noticeRead.noticeReadAll();

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
            noticeRead.noticeReadAll();

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
                    Notice notice = noticeInput.noticeDataInput();
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
                Notice notice = noticeInput.noticeDataUpdate(readChoice);
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

    // 라인 -------------------------------------------------------------------------------------------------------------
    public void line() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
