package exception.inventory;

public class InvalidInputException extends RuntimeException {

  public InvalidInputException(String message) {
    super(message);
  }

  /**
   * 다른 예외(e.g., NumberFormatException)를 원인으로 하여 생성할 때 사용합니다.
   * @param message 사용자에게 보여줄 메시지
   * @param cause 근본 원인이 된 예외 객체
   */
  public InvalidInputException(String message, Throwable cause) {
    super(message, cause);
  }
}
