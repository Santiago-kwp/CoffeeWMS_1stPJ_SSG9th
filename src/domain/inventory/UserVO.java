package domain.inventory; // 패키지 경로는 프로젝트 구조에 맞게 조정하세요.

import constant.inventory.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 로그인한 사용자의 정보를 담는 VO(Value Object).
 * 테스트 목적으로 사용되며, 실제로는 로그인 시스템에서 생성됩니다.
 */
@Data
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동 생성
public class UserVO {

  private String userId; // 사용자 ID (e.g., "wmsAdmin", "manager1235", "member12347")
  private Role role;     // 사용자 역할 (총관리자, 창고관리자, 일반회원)

}