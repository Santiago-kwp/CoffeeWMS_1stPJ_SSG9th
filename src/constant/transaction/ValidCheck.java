package constant.transaction;

import exception.transaction.TransactionException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ValidCheck {
  private static final String MENU_NUMBER_1_4 = "^[1-4]$";
  private static final String MENU_NUMBER_1_3 = "^[1-3]$";
  private static final String CHECK_MENU_NUMBER = "^[1-2]$";

  //메인 메뉴 1-4, 1-3번 유효 검사
  public void isValidForMainMenu(String menu) {
    if (!menu.matches(MENU_NUMBER_1_4)) {
      throw new TransactionException(ErrorCode.INVALID_MENU_1_4_OPTION);
    }
  }

  public void isValidForSubMenu(String menu) {
    if (!menu.matches(MENU_NUMBER_1_3)) {
      throw new TransactionException(ErrorCode.INVALID_MENU_1_3_OPTION);
    }
  }

  // 1번 Create의 옵션 메뉴 1-2번 유효 검사
  public void isCheckMenuValid(String menu) {
    if(!(menu.matches(CHECK_MENU_NUMBER))) {
      throw new TransactionException(ErrorCode.INVALID_CHECK_OPTION);
    }
  }


  public int isValidCoffeeNumber(String coffeeNumber, int coffeeProductsSize) {
    // read 입력값이 비어 있는 경우
    if(coffeeNumber.isEmpty()) {
      throw new TransactionException(ErrorCode.INVALID_COFFEE_NUMBER);
    }

    // 숫자가 아니거나 현재 상품 범위를 초과하는 커피번호인 경우
    try {
      int num = Integer.parseInt(coffeeNumber);
      if(num<1 || num > coffeeProductsSize)
        throw new TransactionException(ErrorCode.INVALID_COFFEE_SIZE_NUMBER);
      return num-1;
    } catch (NumberFormatException e) {
      throw new TransactionException(ErrorCode.INVALID_COFFEE_NUMBER);
    }
  }


  public int isValidCoffeeQuantity(String quantityInput) {
    int num = Integer.parseInt(quantityInput);
    if(num < 1 || num > 2000)
      throw new TransactionException(ErrorCode.INVALID_COFFEE_QUANTITY_NUMBER);
    return num;
  }

  public int isValidOutboundCoffeeQuantity(String quantityInput, int maxQuantity) {
    int num = Integer.parseInt(quantityInput);
    if(num < 1 || num > maxQuantity)
      throw new TransactionException(ErrorCode.INVALID_COFFEE_QUANTITY_NUMBER);
    return num;
  }


  /**
   * 입고 요청 날짜가 유효한지 검사합니다.
   * 현재 날짜보다 이전일 수 없습니다.
   * @param date 검사할 날짜
   */
  /**
   * 입고 요청 날짜가 유효한지 검사합니다.
   * 현재 날짜로부터 최소 한 달 이후여야 합니다.
   * @param date 검사할 날짜
   */
  public void isValidInboundDate(Date date) {
    // 현재 날짜와 입력된 날짜의 시간 정보를 모두 제거하여 날짜만 비교
    Calendar todayCal = Calendar.getInstance();
    todayCal.setTime(new Date());
    todayCal.set(Calendar.HOUR_OF_DAY, 0);
    todayCal.set(Calendar.MINUTE, 0);
    todayCal.set(Calendar.SECOND, 0);
    todayCal.set(Calendar.MILLISECOND, 0);

    Calendar oneMonthLaterCal = (Calendar) todayCal.clone();
    oneMonthLaterCal.add(Calendar.MONTH, 1);

    Calendar inboundCal = Calendar.getInstance();
    inboundCal.setTime(date);
    inboundCal.set(Calendar.HOUR_OF_DAY, 0);
    inboundCal.set(Calendar.MINUTE, 0);
    inboundCal.set(Calendar.SECOND, 0);
    inboundCal.set(Calendar.MILLISECOND, 0);

    // 입력된 날짜가 '한 달 후'보다 이전이면 예외를 던집니다.
    if (inboundCal.before(oneMonthLaterCal)) {
      throw new TransactionException(ErrorCode.INVALID_INBOUND_DATE);
    }
  }

  public void isValidOutboundDate(Date date, Date inboundDate) {

    // 입력된 날짜가 입고 날짜 이전이면 예외를 던집니다.
    if (date.before(inboundDate)) {
      throw new TransactionException(ErrorCode.INVALID_OUTBOUND_DATE);
    }
  }

  // 유효한 회원 ID 입력인지 검사하는 메소드
  public void isValidMemberId(Map<String, Integer> requests, String memberId) {
    if (!requests.containsKey(memberId))
      throw new TransactionException(ErrorCode.INVALID_MEMBER_ID);
  }

  public String isValidRequestId(String requestId, List<String> unapprovedRequestIds) {
    if (!unapprovedRequestIds.contains(requestId))
      throw new TransactionException(ErrorCode.INVALID_REQUEST_ID);
    return requestId;
  }

  // 유효한 요청 상품의 ID 입력인지 검사하고, 상품의 ID를 정수로 반환
  public long isValidRequestItemId(String input, List<Long> unapprovedRequestItemIds) {
    if (!unapprovedRequestItemIds.stream().anyMatch(num -> num == Long.parseLong(input)))
      throw new TransactionException(ErrorCode.INVALID_REQUEST_ITEM_ID);
    return Long.parseLong(input);
  }

  public int isValidLocationPlaceNum(String locationPlaceNum, int size) {
    int num = Integer.parseInt(locationPlaceNum);
    if(num < 1 || num > size)
      throw new TransactionException(ErrorCode.INVALID_LOCATION_PLACE_NUMBER);
    return num-1; // 1번 부터 시작
  }

  public int isValidMonth(String numberInput) {
    int num = Integer.parseInt(numberInput);
    if(num < 1 || num > 12)
      throw new TransactionException(ErrorCode.INVALID_MONTH_NUMBER);
    return num;
  }
}
