package handler.support.validHandler;

import domain.user.Manager;

public interface ValidHandler {
    // 관리자가 '총관리자'인지 확인
    void managerCheck (Manager manager);
}
