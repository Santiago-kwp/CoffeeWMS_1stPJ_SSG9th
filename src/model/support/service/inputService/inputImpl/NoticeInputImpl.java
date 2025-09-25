package model.support.service.inputService.inputImpl;

import constant.support.CSMenuMessage;
import domain.support.Notice;
import model.support.service.inputService.NoticeInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NoticeInputImpl implements NoticeInput {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // 공지사항 데이터 입력 (총관리자)------------------------------------------------------------------------------------------
    public Notice noticeDataInput(String managerId) throws IOException {
        Notice notice = new Notice();

        System.out.println(CSMenuMessage.NOTICE_CREATE.getMessage());

        System.out.print(CSMenuMessage.TITLE.getMessage());
        String title = input.readLine();
        notice.setNoticeTitle(title);

        System.out.print(CSMenuMessage.CONTENT.getMessage());
        String content = input.readLine();
        notice.setNoticeContent(content);

        System.out.print(CSMenuMessage.FIXED.getMessage());
        Character C = input.readLine().charAt(0);
        boolean fixed = yesOrNo(C);
        notice.setNoticeFixed(fixed);

        notice.setNoticeManagerId(managerId);

        return notice;
    }

    // 공지사항 데이터 수정 (총관리자)-----------------------------------------------------------------------------------------
    public Notice noticeDataUpdate(Integer readChoice, String managerId) throws IOException {
        Notice notice = new Notice();

        notice.setNoticeId(readChoice);

        System.out.println(CSMenuMessage.NOTICE_UPDATE.getMessage());

        System.out.print(CSMenuMessage.TITLE.getMessage());
        String title = input.readLine();
        notice.setNoticeTitle(title);

        System.out.print(CSMenuMessage.CONTENT.getMessage());
        String content = input.readLine();
        notice.setNoticeContent(content);

        System.out.print(CSMenuMessage.FIXED.getMessage());
        Character C = input.readLine().charAt(0);
        boolean fixed = yesOrNo(C);
        notice.setNoticeFixed(fixed);

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
