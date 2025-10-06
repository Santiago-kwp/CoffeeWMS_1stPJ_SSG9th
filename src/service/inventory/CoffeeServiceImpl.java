package service.inventory;

import domain.transaction.Coffee;
import model.inventory.CoffeeDAO; // CoffeeDAO 인터페이스
import model.inventory.CoffeeDAOImpl; // CoffeeDAO 구현체

import java.sql.SQLException;
import java.util.List;

public class CoffeeServiceImpl implements CoffeeService {

    private final CoffeeDAO coffeeDAO;

    public CoffeeServiceImpl(CoffeeDAO coffeeDAO) {
        this.coffeeDAO = coffeeDAO;
    }

    @Override
    public List<Coffee> getAvailableCoffees() throws SQLException {
        // 현재는 단순히 모든 커피를 가져오지만,
        // 나중에 "단종 상품 제외" 등의 비즈니스 로직이 여기에 추가될 수 있습니다.
        return coffeeDAO.getAllCoffees();
    }
}