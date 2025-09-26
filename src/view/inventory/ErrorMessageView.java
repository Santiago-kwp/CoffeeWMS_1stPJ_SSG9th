package view.inventory;

import constant.inventory.ErrorMessage;

public class ErrorMessageView {

  /**
   * Enum에 정의된 메시지를 표준 오류 형식으로 출력합니다.
   * @param error 출력할 오류 메시지 Enum
   */
  public static void printError(ErrorMessage error) {
    System.out.println("\n>> [오류] " + error.getMessage() + " 다시 입력해주세요.\n");
  }
}