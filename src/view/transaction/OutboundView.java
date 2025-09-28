package view.transaction;

import constant.transaction.ErrorCode;
import constant.transaction.TransactionText;
import constant.transaction.ValidCheck;
import controller.transaction.InboundController;
import controller.transaction.OutboundController;
import domain.transaction.Coffee;
import domain.transaction.InboundItem;
import domain.transaction.OutboundItem;
import exception.transaction.TransactionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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


  public void displayOutboundRequestsMenu() {
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.COMMON_OUTBOUND_REQUESTS_HEADER.getText());
    System.out.println(TransactionText.BORDER_LINE.getText());
  }

  public void displayManagerOutboundMenu() {
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MANAGER_OUTBOUND_HEADER.getText());
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MANAGER_OUTBOUND.getText());
  }

  public void displayInboundApprovedItems(List<InboundItem> inboundItems) {
    AtomicInteger outboundRequestCoffeeNum = new AtomicInteger(1);
    if (inboundItems.isEmpty()) {
      System.out.println(TransactionText.EMPTY_CURRENT_INBOUND.getText());
    } else {
      System.out.println(TransactionText.APPROVED_INBOUNDS_LIST.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      System.out.printf(TransactionText.APPROVED_OUTBOUND_LIST_HEADER.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());

      inboundItems.forEach(inboundItem -> {
        System.out.printf("%-4d | %-15s | %-12s | %-15s | %-10d | %-15s\n",
                outboundRequestCoffeeNum.getAndIncrement(),
                inboundItem.getMemberId(),
                inboundItem.getCoffeeName(),
                inboundItem.getCoffeeId(),
                inboundItem.getQuantity(),
                inboundItem.getInboundRequestDate());

      });

    }
  }

  /**
   * 사용자로부터 상품 ID와 수량을 반복적으로 입력받아 JSON 형식으로 변환합니다.
   * 'exit' 입력 시 종료됩니다.
   * @param prompt 사용자에게 보여줄 안내 메시지
   * @return JSON 형식의 문자열
   */
  public List<OutboundItem> getOutboundInput(List<InboundItem> inboundItems, String prompt) {
    System.out.println(prompt);

    List<OutboundItem> outboundItems = new ArrayList<>();

    while (true) {
      // 예외가 발생할 수 있는 코드
      try {
        // 출고 가능한 커피 번호, 수량 및 날짜를 보여줌
        displayInboundApprovedItems(inboundItems);

        System.out.printf("출고 요청 번호를 입력하세요. ( 1 ~ %d ) | 종료: 'exit' 입력): ", inboundItems.size());
        String coffeeNumInput = scanner.nextLine().trim();

        if ("exit".equalsIgnoreCase(coffeeNumInput)) {
          break;
        }

        // 커피 번호 유효성 검사
        int i = validCheck.isValidCoffeeNumber(coffeeNumInput, inboundItems.size());

        System.out.printf("수량을 입력하세요. (최소 1 ~ 최대 %d (단위: 포대)) : ", inboundItems.get(i).getQuantity());
        String quantityInput = scanner.nextLine().trim();

        // 수량 유효성 검사
        int quantity = validCheck.isValidOutboundCoffeeQuantity(quantityInput, inboundItems.get(i).getQuantity());


        // 출고 요청 날짜
        Date requestOutboundDate = getDateInput("출고 요청 날짜를 입력하세요. (yyyy-MM-dd) ", inboundItems.get(i).getInboundRequestDate());


        // 2. yyyy-MM-dd 형식으로 포맷하기 위한 SimpleDateFormat 생성
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 사용자에게 확인 요청
        System.out.printf("선택하신 상품: %s, 수량: %d, 출고 요청 날짜 : %s 맞습니까? (y/n)\n",
                inboundItems.get(i).getCoffeeName(), quantity, formatter.format(requestOutboundDate));
        System.out.print(">> ");
        String confirm = scanner.nextLine().trim();

        if ("y".equalsIgnoreCase(confirm)) {
          OutboundItem outboundItem = new OutboundItem();
          outboundItem.setQuantity(quantity);
          outboundItem.setOutboundRequestDate(requestOutboundDate);
          outboundItem.setCoffeeId(inboundItems.get(i).getCoffeeId());
          outboundItem.setCoffeeName(inboundItems.get(i).getCoffeeName());

          outboundItems.add(outboundItem);
          System.out.println("출고 상품이 추가되었습니다. 계속 입력하세요.");

          inboundItems.get(i).setQuantity(inboundItems.get(i).getQuantity() - quantity);
          if (inboundItems.get(i).getQuantity() <= 0) {
           inboundItems.remove(i);
          }

        } else if ("n".equalsIgnoreCase(confirm)) {
          System.out.println("출고 상품 추가가 취소되었습니다. 다시 입력해주세요.");
        } else {
          System.out.println("유효하지 않은 입력입니다. 출고 상품 추가가 취소되었습니다.");
        }
      } catch (TransactionException | NumberFormatException e) {
        // 예외가 발생하면 예외 메시지를 출력합니다.
        System.out.println(e.getMessage());
        continue;
      }
    }

    return outboundItems;
  }

  /**
   * 사용자에게 날짜 입력을 요청하고 반환합니다.
   * "yyyy-MM-dd" 형식의 날짜만 허용합니다.
   * @param prompt 사용자에게 보여줄 안내 메시지
   * @return 입력받은 Date 객체
   */
  public Date getDateInput(String prompt, Date inboundDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false); // 형식 검사
    Date date = null;
    while (date == null) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      try {
        date = sdf.parse(input);
        validCheck.isValidOutboundDate(date, inboundDate); // 새로운 유효성 검사 메소드 호출
      } catch (ParseException e) {
        System.out.println("잘못된 날짜 형식입니다. 'yyyy-MM-dd' 형식으로 다시 입력해주세요.");
        date = null; // 날짜를 다시 null로 설정하여 루프를 계속함
      } catch (TransactionException e) {
        System.out.println(e.getMessage());
        // 유효하지 않은 날짜인 경우, 한 달 후의 날짜를 계산하여 출력
        if (e.error == ErrorCode.INVALID_OUTBOUND_DATE) {
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(inboundDate); // Calendar 객체에 Date 설정
          calendar.add(Calendar.DATE, 1); // 1일 더하기
          Date tomorrow = calendar.getTime(); // Date 객체로 변환
          SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
          System.out.println("입력 가능한 최소 날짜는 " + outputFormat.format(tomorrow.getTime()) + "입니다.");
        }
        date = null;
      }
    }
    return date;
  }



  /**
   * 사용자에게 메시지를 표시합니다.
   * @param message 표시할 메시지
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }

  public int getFourIntegerInput(String s) {
    System.out.println(s);
    String numberInput = scanner.nextLine();
    try {
      validCheck.isValidForMainMenu(numberInput);
    }
    catch (TransactionException e) {
      System.out.println(e.getMessage());
      getFourIntegerInput(s);
    }
    return Integer.parseInt(numberInput);
  }

  public int getThreeIntegerInput(String s) {
    System.out.println(s);
    String numberInput = scanner.nextLine();
    try {
      validCheck.isValidForSubMenu(numberInput);
    }
    catch (TransactionException e) {
      System.out.println(e.getMessage());
      getThreeIntegerInput(s);
    }
    return Integer.parseInt(numberInput);
  }


  public void displayOutboundRequestItems(List<OutboundItem> outboundItems) {
    if (outboundItems.isEmpty()) {
      System.out.println(TransactionText.EMPTY_CURRENT_OUTBOUND.getText());
    } else {
      System.out.println(TransactionText.UNAPPROVED_OUTBOUNDS_LIST.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      System.out.printf(TransactionText.UNAPPROVED_OUTBOUND_LIST_HEADER.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      outboundItems.forEach(outboundItem -> {
        System.out.printf("%-12s | %-5d | %-15s | %-15s | %-10s | %-15s\n",
                outboundItem.getOutboundRequestId(),
                outboundItem.getOutboundRequestItemId(),
                outboundItem.getMemberId(),
                outboundItem.getCoffeeName(),
                outboundItem.getQuantity(),
                outboundItem.getOutboundRequestDate());

      });

    }
  }
}
