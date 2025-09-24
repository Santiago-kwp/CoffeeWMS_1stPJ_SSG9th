package model.support.service.input;

import domain.support.Notice;

import java.io.IOException;

public interface NoticeInput {
    Notice noticeDataInput() throws IOException;

    Notice noticeDataUpdate(Integer readChoice) throws IOException;
}
