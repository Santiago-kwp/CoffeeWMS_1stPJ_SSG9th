package model.support.service.input.inputImpl;

import domain.support.Notice;
import model.support.service.input.NoticeInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NoticeInputImpl implements NoticeInput {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
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
    // 예, 아니오 --------------------------------------------------------------------------------------------------------
    public boolean yesOrNo(Character c) {
        return switch (c) {
            case 'y', 'Y' -> true;
            case 'n', 'N' -> false;
            default -> false;
        };
    }
}
