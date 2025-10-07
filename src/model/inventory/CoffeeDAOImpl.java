package model.inventory;

import config.DBUtil;
import domain.transaction.Coffee;
import model.transaction.InboundRequestItemDAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoffeeDAOImpl implements CoffeeDAO {

    // 1. private static final 인스턴스 변수 선언 및 초기화
    private static final CoffeeDAOImpl instance = new CoffeeDAOImpl();

    // 2. private 생성자 선언하여 외부에서 new 키워드로 생성하는 것을 방지
    private CoffeeDAOImpl() {}

    // 3. public static getInstance() 메서드로 유일한 인스턴스에 접근하도록 함
    public static CoffeeDAOImpl getInstance() {
        return instance;
    }


    /**
     * `coffees` 테이블에서 모든 커피 목록을 조회합니다.
     * @return 커피 DTO 리스트
     */
    @Override
    public List<Coffee> getAllCoffees() {
        List<Coffee> coffeeList = new ArrayList<>();
        String sql = "SELECT * FROM coffees";
        try (Connection conn = DBUtil.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Coffee coffee = new Coffee();
                coffee.setCoffeeId(rs.getString("coffee_id"));
                coffee.setCoffeeName(rs.getString("coffee_name"));
                coffee.setCoffeeOrigin(rs.getString("coffee_origin"));
                coffee.setDecaf(rs.getString("decaf"));
                coffee.setRoasted(rs.getString("coffee_roasted"));
                coffee.setCoffeeGrade(rs.getString("coffee_grade"));
                coffee.setCoffeeType(rs.getString("coffee_type"));
                coffee.setPrice(rs.getInt("price"));
                coffeeList.add(coffee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coffeeList;
    }

}
