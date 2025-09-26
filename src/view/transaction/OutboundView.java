package view.transaction;

import constant.transaction.TransactionText;
import constant.transaction.ValidCheck;
import controller.transaction.InboundController;
import controller.transaction.OutboundController;
import java.util.Scanner;

public class OutboundView {
  private static ValidCheck validCheck = new ValidCheck();
  private OutboundController controller;
  private final Scanner scanner;

  public OutboundView(OutboundController controller) {
    this.controller = controller;
    this.scanner = new Scanner(System.in);
  }

  public void setController(OutboundController controller) {
    this.controller = controller;
  }

  /**
   * 회원 로그인 -> 출고화면으로 이동시 웰컴 화면 표시
   */
  public void displayMemberOutboundMenu() {
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MEMBER_OUTBOUND_HEADER.getText());
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MEMBER_OUTBOUND.getText());
  }




}
