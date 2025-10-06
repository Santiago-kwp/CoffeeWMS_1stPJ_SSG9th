package service.inventory;

import domain.transaction.Coffee;
import java.sql.SQLException;
import java.util.List;

public interface CoffeeService {
    /**
     * 주문 가능한 모든 커피 상품 목록을 조회합니다.
     * @return Coffee DTO 리스트
     */
    List<Coffee> getAvailableCoffees() throws SQLException;
}