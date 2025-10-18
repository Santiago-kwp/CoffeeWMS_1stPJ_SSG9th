package handler.support.inputHandler;

public interface InputHandler {
    // 2가지 메뉴 중 선택
    String twoMenuChoice();

    // 3가지 메뉴 중 선택
    String threeMenuChoice();

    // 4가지 메뉴 중 선택
    String fourMenuChoice();

    // 상세보기 번호 선택
    int choiceBoard();
}
