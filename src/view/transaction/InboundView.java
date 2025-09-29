package view.transaction;


import constant.transaction.ErrorCode;
import constant.transaction.TransactionText;
import constant.transaction.ValidCheck;
import controller.transaction.InboundController;
import domain.transaction.Coffee;
import domain.transaction.InboundItem;
import domain.transaction.LocationPlace;
import exception.transaction.TransactionException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.DefaultListSelectionModel;
import java.text.DecimalFormat;

/**
 * InboundView
 * 사용자와 상호작용하는 뷰 역할을 담당하는 클래스.
 * 컨트롤러로부터 받은 데이터를 화면에 표시합니다.
 */
public class InboundView {
  private static ValidCheck validCheck = new ValidCheck();
  private InboundController controller;

  private final Scanner scanner;



  public InboundView(InboundController controller) {
    this.controller = controller;
    this.scanner = new Scanner(System.in);
  }

  public void setController(InboundController controller) {
    this.controller = controller;
  }


  /**
   * 로그인 -> 입고화면으로 이동시 웰컴 화면 표시
   */
  public void displayMemberInboundMenu() {
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MEMBER_INBOUND_HEADER.getText());
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MEMBER_INBOUND.getText());
  }

  public void displayInboundRequestsMenu() {
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.COMMON_INBOUND_REQUESTS_HEADER.getText());
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.COMMON_INBOUND_REQUESTS_LIST.getText());
  }
  public void displayManagerInboundRequestsMenu() {
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.COMMON_INBOUND_REQUESTS_HEADER.getText());
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MANAGER_INBOUND_LIST.getText());
  }

  public void displayManagerInboundMenu() {
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MANAGER_INBOUND_HEADER.getText());
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MANAGER_INBOUND.getText());
  }

  /**
   * 입고 가능한 커피 목록을 화면에 표시합니다.
   * @param coffees 컨트롤러로부터 전달받은 커피 목록
   */
  public void displayCoffees(List<Coffee> coffees) {
    AtomicInteger inboundRequestCoffeeNum = new AtomicInteger(1);
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.MEMBER_INBOUND_HEADER.getText());
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println(TransactionText.COFFEES_HEADER.getText());
    System.out.println(TransactionText.COFFEE_ATTRIBUTES_HEADER.getText());

    if (coffees.isEmpty()) {
      System.out.println(TransactionText.NO_AVAILABLE_COFFEE.getText());
    } else {
      coffees.forEach(coffee -> System.out.printf("|   %-4d %s\n",inboundRequestCoffeeNum.getAndIncrement(), coffee.toString()));
    }
    System.out.println(TransactionText.BORDER_LINE.getText());
  }


  public int getFourIntegerInput(String s) {
    while(true){
      System.out.println(s);
      String numberInput = scanner.nextLine();
      try {
        validCheck.isValidForMainMenu(numberInput);
      }
      catch (TransactionException e) {
        System.out.println(e.getMessage());
      }
      return Integer.parseInt(numberInput);
    }

  }

  public int getThreeIntegerInput(String s) {
    while(true){
      System.out.println(s);
      String numberInput = scanner.nextLine();
      try {
        validCheck.isValidForSubMenu(numberInput);
      }
      catch (TransactionException e) {
        System.out.println(e.getMessage());
      }
      return Integer.parseInt(numberInput);
    }
  }

  /**
   * 사용자에게 메시지를 표시합니다.
   * @param message 표시할 메시지
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }



  /**
   * 사용자로부터 상품 ID와 수량을 반복적으로 입력받아 JSON 형식으로 변환합니다.
   * 'exit' 입력 시 종료됩니다.
   * @param prompt 사용자에게 보여줄 안내 메시지
   * @return JSON 형식의 문자열
   */
  public String getJsonInput(String prompt) {
    System.out.println(prompt);

    List<Map<String, Object>> items = new ArrayList<>();
    List<Coffee> coffees = controller.showAvailableCoffees();

    while (true) {
      // 예외가 발생할 수 있는 코드
      try {
        System.out.printf("커피 번호를 입력하세요. ( 1 ~ %d ) | 종료: 'exit' 입력): ", coffees.size());
        String coffeeNumInput = scanner.nextLine().trim();

        if ("exit".equalsIgnoreCase(coffeeNumInput)) {
          break;
        }

        // 커피 번호 유효성 검사
        validCheck.isValidCoffeeNumber(coffeeNumInput, coffees.size());

        System.out.print("수량을 입력하세요. (최소 1 ~ 최대 2,000 (단위: 포대)) :  ");
        String quantityInput = scanner.nextLine().trim();

        // 수량 유효성 검사
        int quantity = validCheck.isValidCoffeeQuantity(quantityInput);

        int coffeeIndex = Integer.parseInt(coffeeNumInput) - 1;
        Coffee selectedCoffee = coffees.get(coffeeIndex);

        // 사용자에게 확인 요청
        System.out.printf("선택하신 상품: %s, 수량: %d. 맞습니까? (y/n)\n", selectedCoffee.getCoffeeName(), quantity);
        System.out.print(">> ");
        String confirm = scanner.nextLine().trim();

        if ("y".equalsIgnoreCase(confirm)) {
          Map<String, Object> item = new HashMap<>();
          item.put("coffee_id", selectedCoffee.getCoffeeId());
          item.put("quantity", quantity);
          items.add(item);
          System.out.println("상품이 추가되었습니다. 계속 입력하세요.");
        } else if ("n".equalsIgnoreCase(confirm)) {
          System.out.println("상품 추가가 취소되었습니다. 다시 입력해주세요.");
        } else {
          System.out.println("유효하지 않은 입력입니다. 상품 추가가 취소되었습니다.");
        }
      } catch (TransactionException | NumberFormatException e) {
        // 예외가 발생하면 예외 메시지를 출력합니다.
        System.out.println(e.getMessage());
        continue;
      }
    }

    // 수집된 데이터를 수동으로 JSON 문자열로 변환
    StringBuilder jsonBuilder = new StringBuilder("[");
    for (int i = 0; i < items.size(); i++) {
      Map<String, Object> item = items.get(i);
      jsonBuilder.append(String.format("{\"coffee_id\":\"%s\", \"quantity\":%d}",
          item.get("coffee_id"), item.get("quantity")));
      if (i < items.size() - 1) {
        jsonBuilder.append(",");
      }
    }
    jsonBuilder.append("]");

    return jsonBuilder.toString();
  }

  /**
   * 사용자에게 날짜 입력을 요청하고 반환합니다.
   * "yyyy-MM-dd" 형식의 날짜만 허용합니다.
   * @param prompt 사용자에게 보여줄 안내 메시지
   * @return 입력받은 Date 객체
   */
  public Date getDateInput(String prompt) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false); // 형식 검사
    Date date = null;
    while (date == null) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      try {
        date = sdf.parse(input);
        validCheck.isValidInboundDate(date); // 새로운 유효성 검사 메소드 호출
      } catch (ParseException e) {
        System.out.println("잘못된 날짜 형식입니다. 'yyyy-MM-dd' 형식으로 다시 입력해주세요.");
        date = null; // 날짜를 다시 null로 설정하여 루프를 계속함
      } catch (TransactionException e) {
        System.out.println(e.getMessage());
        // 유효하지 않은 날짜인 경우, 한 달 후의 날짜를 계산하여 출력
        if (e.error == ErrorCode.INVALID_INBOUND_DATE) {
          Calendar cal = Calendar.getInstance();
          cal.add(Calendar.MONTH, 1);
          SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
          System.out.println("입력 가능한 최소 날짜는 " + outputFormat.format(cal.getTime()) + "입니다.");
        }
        date = null;
      }
    }
    return date;
  }



  public void displayInboundRequestItems(List<InboundItem> inboundItems) {
    if (inboundItems.isEmpty()) {
      System.out.println(TransactionText.EMPTY_CURRENT_INBOUND.getText());
    } else {
      System.out.println(TransactionText.UNAPPROVED_INBOUNDS_LIST.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      System.out.printf(TransactionText.UNAPPROVED_INBOUND_LIST_HEADER.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      inboundItems.forEach(inboundItem -> {
        System.out.printf("%-12s | %-5d | %-15s | %-15s | %-10s | %-15s\n",
                inboundItem.getInboundRequestId(),
                inboundItem.getInboundRequestItemId(),
                inboundItem.getMemberId(),
                inboundItem.getCoffeeName(),
                inboundItem.getQuantity(),
                inboundItem.getInboundRequestDate());

      });

    }
  }

  public void displayInboundApprovedItems(List<InboundItem> inboundItems) {
    if (inboundItems.isEmpty()) {
      System.out.println(TransactionText.EMPTY_CURRENT_INBOUND.getText());
    } else {
      System.out.println(TransactionText.APPROVED_INBOUNDS_LIST.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      System.out.printf(TransactionText.APPROVED_INBOUND_LIST_HEADER.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      inboundItems.forEach(inboundItem -> {
        System.out.printf("%-12s | %-5d | %-15s | %-15s | %-10s | %-15s\n",
                inboundItem.getInboundRequestId(),
                inboundItem.getInboundRequestItemId(),
                inboundItem.getMemberId(),
                inboundItem.getCoffeeName(),
                inboundItem.getQuantity(),
                inboundItem.getInboundRequestDate());

      });

    }
    System.out.println(TransactionText.COMMON_APPROVED_INBOUND_LIST.getText());
  }


  public void displayMemberUnapprovedInboundRequests(Map<String, Integer> requests) {
    if (requests.isEmpty()) {
      System.out.println(TransactionText.EMPTY_MEMBER_UNAPPROVED_INBOUND.getText());
    } else {
      System.out.println(TransactionText.BORDER_LINE.getText());
      System.out.println(TransactionText.MEMBER_UNAPPROVED_INBOUNDS_HEADER.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      System.out.println(TransactionText.MEMBER_UNAPPROVED_INBOUNDS_LIST.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      requests.keySet().stream().forEach(
          key -> System.out.printf("%-15s | %-15s\n",
              key, requests.get(key)));

    }
  }

  public void displayMemberApprovedInboundRequests(Map<String, Integer> requests) {
    if (requests.isEmpty()) {
      System.out.println(TransactionText.EMPTY_MEMBER_APPROVED_INBOUND.getText());
    } else {
      System.out.println(TransactionText.BORDER_LINE.getText());
      System.out.println(TransactionText.MEMBER_APPROVED_INBOUNDS_HEADER.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      System.out.println(TransactionText.MEMBER_APPROVED_INBOUNDS_LIST.getText());
      System.out.println(TransactionText.BORDER_LINE.getText());
      requests.keySet().stream().forEach(
              key -> System.out.printf("%-15s | %-15s\n",
                      key, requests.get(key)));

    }
  }

  public String getMemberId(Map<String, Integer> requests, String prompt) {
    String input;
    while (true) {
      System.out.print(prompt);
      input = scanner.nextLine().trim();
      try {
        validCheck.isValidMemberId(requests, input); // 새로운 유효성 검사 메소드 호출
      } catch (TransactionException e) {
        System.out.println(e.getMessage());
        // 유효하지 않은 회원ID를 입력한 경우 다시 받음
        if (e.error == ErrorCode.INVALID_MEMBER_ID) {
          continue;
        }
      }
      break;
    }
    return input;
  }


  public int getInboundRequestGrant(String memberId, String requestId) {

    try {
      while (true) {
        // 사용자에게 입고 승인 여부 확인 요청
        System.out.printf("선택하신 회원 ID : %s 의 입고 요청 ID : %s을 최종 승인하시겠습니까? (y/n)\n", memberId, requestId);
        System.out.print(">> ");
        String confirm = scanner.nextLine().trim();
        if ("y".equalsIgnoreCase(confirm)) {
          return 1;
        } else if ("n".equalsIgnoreCase(confirm)) {
          return 0;
        } else {
          System.out.println("유효하지 않은 입력입니다.");
        }
      }
    } catch (TransactionException e) {
      System.out.println(e.getMessage());
      // 예외가 발생할 경우 다시 승인 여부 재귀 호출
      getInboundRequestGrant(memberId, requestId);
    }
    return 0;
  }

  public long getInboundRequestItemIdByMember(String memberId, List<Long> unapprovedRequestIds, String prompt) {
    String input;
    long inboundRequestItemId = 0;
    while (true) {
      System.out.print(prompt);
      input = scanner.nextLine().trim();
      try {
        inboundRequestItemId = validCheck.isValidRequestItemId(input, unapprovedRequestIds); // 새로운 유효성 검사 메소드 호출
      } catch (TransactionException e) {
        System.out.println(e.getMessage());
        // 유효하지 않은 입고 요청ID를 입력한 경우 다시 받음
        if (e.error == ErrorCode.INVALID_REQUEST_ID) {
          continue;
        }
      }
      break;
    }
    return inboundRequestItemId;
  }

  public String showAvailableLocationPlaces(List<LocationPlace> locationPlaces) {
    AtomicInteger availableLocationCount = new AtomicInteger(1);
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println("입고 가능한 창고의 위치 목록");
    System.out.println(TransactionText.BORDER_LINE.getText());
    System.out.println();

    if (locationPlaces.isEmpty()) {
      System.out.println(TransactionText.NO_AVAILABLE_COFFEE.getText());
    } else {
      locationPlaces.forEach(locationPlace ->
          System.out.printf("|  %-4d %s\n",availableLocationCount.getAndIncrement(), locationPlace.toString()));
    }
    System.out.println(TransactionText.BORDER_LINE.getText());

    while (true) {
      // 예외가 발생할 수 있는 코드
      try {
        System.out.print("보관할 창고의 번호를 입력하세요 : ");
        String locationPlaceNum = scanner.nextLine().trim();

        // 창고 위치 번호 유효성 검사
        int num = validCheck.isValidLocationPlaceNum(locationPlaceNum, locationPlaces.size());

        // 사용자에게 한번 더 확인 요청
        System.out.printf("선택하신 위치가 아래의 위치가 맞습니까? (y/n)\n%s\n", locationPlaces.get(num).toString());
        System.out.print(">> ");
        String confirm = scanner.nextLine().trim();

        if ("y".equalsIgnoreCase(confirm)) {
          System.out.println("재고 위치가 지정되었습니다.");
          return locationPlaces.get(num).getLocationPlaceId();
        } else if ("n".equalsIgnoreCase(confirm)) {
          System.out.println("재고 위치 지정이 취소되었습니다. 다시 입력해주세요.");
        } else {
          System.out.println("유효하지 않은 입력입니다. 재고 위치 지정이 취소되었습니다.");
        }

      } catch (TransactionException e) {
        System.out.println(e.getMessage());
        if (e.error == ErrorCode.INVALID_LOCATION_PLACE_NUMBER)
          continue;
      }
    }
  }

  // 한 회원의 여러 건의 입고 요청 중 한 건의 입고 요청을 받음.
  public String getOneRequestId(List<String> unapprovedRequestIds, String prompt) {
    String requestId;
    while (true) {
      try {
        System.out.println(prompt);
        requestId = scanner.nextLine().trim();
        requestId = validCheck.isValidRequestId(requestId, unapprovedRequestIds);
        return requestId;
      } catch (TransactionException e) {
        System.out.println(e.getMessage());
        if (e.error == ErrorCode.INVALID_REQUEST_ID) {
          getOneRequestId(unapprovedRequestIds, prompt);
        }
      }
    }
  }

  public int getMonthInput(String s) {
    System.out.println(s);
    String numberInput = scanner.nextLine();
    try {
      validCheck.isValidMonth(numberInput);
    }
    catch (TransactionException e) {
      System.out.println(e.getMessage());
      getMonthInput(s);
    }
    return Integer.parseInt(numberInput);
  }

  public Date getDateStartInput(String prompt) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false); // 형식 검사
    Date date = null;
    while (date == null) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      try {
        date = sdf.parse(input);
        validCheck.isValidInboundDate(date); // 새로운 유효성 검사 메소드 호출
      } catch (ParseException e) {
        System.out.println("잘못된 날짜 형식입니다. 'yyyy-MM-dd' 형식으로 다시 입력해주세요.");
        date = null; // 날짜를 다시 null로 설정하여 루프를 계속함
      } catch (TransactionException e) {
        System.out.println(e.getMessage());
        // 유효하지 않은 날짜인 경우, 한 달 후의 날짜를 계산하여 출력
        if (e.error == ErrorCode.INVALID_INBOUND_DATE) {
          Calendar cal = Calendar.getInstance();
          cal.add(Calendar.MONTH, 1);
          SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
          System.out.println("입력 가능한 최소 날짜는 " + outputFormat.format(cal.getTime()) + "입니다.");
        }
        date = null;
      }
    }
    return date;
  }

  public Date getDateEndInput(Date startDate, String prompt) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return new Date();
  }
}

//// DecimalFormat 객체 생성.
//// #,##0 패턴은 천 단위 구분자를 사용하고, 정수가 0일 경우 0을 출력하도록 지정
//DecimalFormat formatter = new DecimalFormat("###,###");
//
//// format() 메서드로 숫자 포맷팅
//String formattedNumber = formatter.format(number);
//
//// 포맷팅된 결과 출력
//        System.out.println(formattedNumber); // 출력: 1,234,567