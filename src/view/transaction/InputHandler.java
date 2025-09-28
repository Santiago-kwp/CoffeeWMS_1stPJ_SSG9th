package view.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import constant.transaction.ValidCheck;
import exception.transaction.TransactionException;

/**
 * 스캐너 쓴 InboundView 를 핸들러를 이용해 분리하고 bufferedReader 를 써서 대공사를 해보려고 놔둔 클래스...
 * 일단 시간이 모자라 나중에 리펙토링 해보는걸로
 */

public class InputHandler {

    private final ValidCheck validCheck;

    public InputHandler(ValidCheck validCheck) {
        this.validCheck = validCheck;
    }

    /**
     * 사용자로부터 상품 ID와 수량을 반복적으로 입력받아 JSON 형식으로 변환합니다.
     * 'exit' 입력 시 종료됩니다.
     * @return JSON 형식의 문자열
     */

    public String getJsonInput() {
        List<Map<String, Object>> items = new ArrayList<>();
        // Assuming 'coffees' list is available, e.g., fetched from a database
        List<String> coffees = List.of("Espresso", "Latte", "Cappuccino");

        // Use try-with-resources for automatic BufferedReader closing
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    // I/O operations are within a robust try block
                    System.out.printf("커피 번호를 입력하세요. (1 ~ %d) | 종료: 'exit' 입력): ", coffees.size());
                    String coffeeNumInput = br.readLine().trim();

                    if ("exit".equalsIgnoreCase(coffeeNumInput)) {
                        break;
                    }

                    // 커피 번호 유효성 검사
                    validCheck.isValidCoffeeNumber(coffeeNumInput, coffees.size());

                    System.out.print("수량을 입력하세요. (최소 1 ~ 최대 2,000 (단위: 포대)) :  ");
                    String quantityInput = br.readLine().trim();

                    // 수량 유효성 검사
                    int quantity = validCheck.isValidCoffeeQuantity(quantityInput);

                    int coffeeIndex = Integer.parseInt(coffeeNumInput) - 1;
                    String selectedCoffee = coffees.get(coffeeIndex);

                    // 사용자에게 확인 요청
                    System.out.printf("선택하신 상품: %s, 수량: %d. 맞습니까? (y/n)\n", selectedCoffee, quantity);
                    System.out.print(">> ");
                    String confirm = br.readLine().trim();

                    if ("y".equalsIgnoreCase(confirm)) {
                        Map<String, Object> item = new HashMap<>();
                        // Note: Replace with actual coffee_id if available
                        item.put("coffee_id", "some_id_" + coffeeIndex);
                        item.put("quantity", quantity);
                        items.add(item);
                        System.out.println("상품이 추가되었습니다. 계속 입력하세요.");
                    } else if ("n".equalsIgnoreCase(confirm)) {
                        System.out.println("상품 추가가 취소되었습니다. 다시 입력해주세요.");
                    } else {
                        System.out.println("유효하지 않은 입력입니다. 상품 추가가 취소되었습니다.");
                    }

                } catch (NumberFormatException | TransactionException e) {
                    // This block catches validation errors
                    System.out.println("입력 오류: " + e.getMessage());
                    continue; // Continue the loop to get a new input
                }
            }
        } catch (IOException e) {
            // This block handles critical I/O errors and terminates the method
            System.err.println("입력/출력 오류가 발생했습니다. 프로그램을 종료합니다.");
            e.printStackTrace();
            return "[]"; // Return an empty JSON string on failure
        }

        // 데이터 수집 후 JSON 변환
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

}
