package model.inventory;

import config.DBUtil;
import domain.transaction.Coffee;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoffeeDAOImpl implements CoffeeDAO {


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
