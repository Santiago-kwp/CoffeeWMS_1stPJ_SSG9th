package handler.support.input.inputImpl;

import constant.support.BoardErrorCode;
import constant.support.BoardText;
import domain.support.Board;
import domain.support.Notice;
import handler.support.cs.csServiceImpl.CSOptionImpl;
import handler.support.cs.CSOption;
import handler.support.input.BoardInput;
//import service.support.inputService.NoticeInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Character.toUpperCase;

public class NoticeInputImpl implements BoardInput {
    private static final CSOption csOption = new CSOptionImpl();
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));


    // 공지사항 데이터 입력
    @Override
    public Board dataInput(String managerId) {
        Notice notice = new Notice();

        System.out.println(BoardText.NOTICE_CREATE.getMessage());

        try {
            System.out.print(BoardText.TITLE.getMessage());
            String title = input.readLine();
            notice.setNoticeTitle(title);

            System.out.print(BoardText.CONTENT.getMessage());
            String content = input.readLine();
            notice.setNoticeContent(content);

            while (true) {
                System.out.print(BoardText.FIXED.getMessage());
                Character C = input.readLine().charAt(0);
                if (toUpperCase(C) == 'Y' || toUpperCase(C) == 'N') {
                    boolean fixed = csOption.yesOrNo(C);
                    notice.setNoticeFixed(fixed);
                    break;
                } else {
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardErrorCode.NOT_INPUT_OPTION.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        notice.setNoticeManagerId(managerId);

        return notice;
    }

    // 공지사항 데이터 수정
    @Override
    public Board dataUpdate(Integer readChoice, String managerId) {
        Notice notice = new Notice();

        notice.setNoticeId(readChoice);

        System.out.println(BoardText.NOTICE_UPDATE.getMessage());

        try {
            System.out.print(BoardText.TITLE.getMessage());
            String title = input.readLine();
            notice.setNoticeTitle(title);

            System.out.print(BoardText.CONTENT.getMessage());
            String content = input.readLine();
            notice.setNoticeContent(content);

            while (true) {
                System.out.print(BoardText.FIXED.getMessage());
                char C = input.readLine().charAt(0);
                if (toUpperCase(C) == 'Y' || toUpperCase(C) == 'N') {
                    boolean fixed = csOption.yesOrNo(C);
                    notice.setNoticeFixed(fixed);
                    break;
                } else {
                    System.out.println(BoardText.LINE.getMessage());
                    System.out.println(BoardErrorCode.NOT_INPUT_OPTION.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        notice.setNoticeManagerId(managerId);

        return notice;
    }
}
