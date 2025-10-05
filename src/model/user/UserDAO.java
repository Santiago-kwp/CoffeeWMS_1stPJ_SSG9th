package model.user;

import domain.user.User;

public interface UserDAO {

    // 관리자, 일반회원 공통 기능 정의: 자신의 정보를 조회
    User searchUserDetails();
    User updateUserInfo(User newUserInfo);
    boolean deleteUserInfo();
}
