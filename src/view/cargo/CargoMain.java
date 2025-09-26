package view.cargo;
import controller.cargo.CargoController;

import java.sql.SQLException;

public class CargoMain {
    public static void main(String[] args) {
        try {
            CargoController cargoController = new CargoController();
            cargoController.start(); // 창고 관리 메뉴 실행
        } catch (SQLException e) {
            System.out.println("시스템 오류: " + e.getMessage());
        }
    }
}
