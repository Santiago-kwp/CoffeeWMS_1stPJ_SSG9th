package service;

import domain.inventory.UserVO;
import model.inventory.UserDAO;

public class UserService {
  private final UserDAO userDAO = new UserDAO();

  /**
   * 사용자 ID를 받아 로그인을 시도하고, 성공 시 사용자 정보를 반환합니다.
   * @param userId 로그인할 사용자 ID
   * @return 로그인 성공 시 UserVO 객체, 실패 시 null
   */
  public UserVO login(String userId) {
    // DAO를 통해 사용자 정보를 조회하여 그대로 반환
    return userDAO.findUserById(userId);
  }
}
