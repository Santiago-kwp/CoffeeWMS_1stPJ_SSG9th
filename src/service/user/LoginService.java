package service.user;

import constant.user.validation.LoginValidCheck;
import domain.user.User;
import exception.user.FailedToUserRegisterException;
import exception.user.FailedToUserUpdateException;
import exception.user.LoginException;
import exception.user.UserNotFoundException;

public interface LoginService {

    LoginValidCheck loginValidCheck = new LoginValidCheck();

    /**
     * 로그인을 시도하고 성공 시 User 객체를 반환합니다.
     * @param userID 사용자 ID
     * @param userPwd 사용자 비밀번호
     * @return 로그인한 User 객체
     * @throws LoginException 로그인 실패 시 발생
     */
    User login(String userID, String userPwd) throws LoginException;

    /**
     * 사용자 정보를 시스템에 등록합니다.
     * @param user 등록할 User 객체
     * @throws FailedToUserRegisterException 등록 실패 시 발생
     */
    void register(User user) throws FailedToUserRegisterException;

    /**
     * 이메일 주소로 사용자 ID를 찾습니다.
     * @param email 사용자 이메일
     * @return 찾은 사용자 ID
     * @throws UserNotFoundException 사용자를 찾지 못했을 경우 발생
     */
    String findIdByEmail(String email) throws UserNotFoundException;

    /**
     * 사용자의 비밀번호를 업데이트합니다.
     * @param userID 사용자 ID
     * @param newPassword 새로운 비밀번호
     * @throws FailedToUserUpdateException 업데이트 실패 시 발생
     */
    void updatePassword(String userID, String newPassword) throws FailedToUserUpdateException;
}
