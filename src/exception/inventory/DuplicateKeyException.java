package exception.inventory;

/**
 * 데이터베이스의 기본 키(Primary Key) 또는 고유 키(Unique Key) 제약 조건을
 * 위반했을 때 발생하는 예외입니다. (예: ID 중복)
 */
public class DuplicateKeyException extends RuntimeException {
  public DuplicateKeyException(String message) {
    super(message);
  }
}