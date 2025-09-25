package model.support.service.inputService;

import domain.support.Notice;

public interface NoticeInput {
    // '총관리자' 권한을 가진 유저에게 공지사항 데이터를 입력받는 기능이다.
    Notice noticeDataInput(String managerId);

    // '총관리자' 권한을 가진 유저에게 수정할 공지사항 데이터를 입력받는 기능이다.
    Notice noticeDataUpdate(Integer readChoice, String managerId);
}
