package exception.inventory;

/**
 * 조회, 수정, 삭제하려는 대상 데이터를 찾을 수 없을 때 발생하는 예외입니다.
 * (예: 존재하지 않는 재고 ID, 존재하지 않는 실사 ID)
 */
public class DataNotFoundException extends RuntimeException {
  public DataNotFoundException(String message) {
    super(message);
  }
}
