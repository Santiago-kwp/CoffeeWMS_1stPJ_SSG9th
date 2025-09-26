package controller.cargo;

import domain.cargo.cargoVo.Cargo;
import model.cargo.cargoDaoImpl.CargoDaoImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CargoController {
    private final CargoDaoImpl dao;
    private final Scanner scanner;
//    private Manager manager;

//    public CargoController(Manager manager) throws SQLException {
//        this.dao = new CargoDaoImpl();
//        this.scanner = new Scanner(System.in);
//        this.manager = manager;
//    }

    public CargoController() throws SQLException {
        this.dao = new CargoDaoImpl();
        this.scanner = new Scanner(System.in);

    }

    private void showMenu() {
        printf("""
                ------------------------------------<<창고관리메뉴>>------------------------------
                메뉴: 1.창고 등록(총관리자전용) 2.창고 수정(총관리자전용) 3.창고 삭제(총관리자전용) 
                     4.창고 전체 조회 5.창고 이름조회 6.창고 소재지별 조회 7. 창고 등급별 조회 0.종료
                --------------------------------------------------------------------------------
                
                """);

    }

    private void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    private void println(String message) {
        System.out.println(message);
    }

    public void start() {
        while (true) {
            showMenu();

            println("번호를 입력하세요");
            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1 -> {

                        addCargo();
                        break;
                    }
                    case 2 -> {
                        modifyCargo();
                        break;
                    }
                    case 3 -> {
                        removeCargo();
                        break;
                    }
                    case 4 -> {
                        getAllCargos();
                        break;
                    }
                    case 5 -> {
                        getCargoByName();
                        break;
                    }
                    case 6 -> {
                        getCargoByAddress();
                        break;
                    }
                    case 7 -> {
                        getCargoByGrade();
                        break;
                    }
                    case 0 -> {
                        println("종료합니다.");
                        return;
                    }
                    default -> println("잘못된 선택입니다.");
                }
            } catch (Exception e) {
                println("오류 발생: " + e.getMessage());
            }
        }
    }


    private void addCargo() throws SQLException {
        println("------------------------------------<<창고 등록>>------------------------------");
//        checkAdmin();
        Cargo cargo = new Cargo(
                0,
                read("창고 코드"),
                read("창고 이름"),
                read("창고 주소"),
                read("창고 등급"),
                Integer.parseInt(read("평수")),
                Integer.parseInt(read("총 용량")),
                Integer.parseInt(read("사용 용량"))
        );
        List<Cargo> result = dao.addCargo(cargo);
        result.forEach(System.out::println);

    }

    private void modifyCargo() throws SQLException {
        println("창고 수정:");
        Cargo cargo = new Cargo(
                Integer.parseInt(read("ID")),
                read("코드"),
                read("이름"),
                read("주소"),
                read("등급"),
                Integer.parseInt(read("평수")),
                Integer.parseInt(read("총 용량")),
                Integer.parseInt(read("사용 용량"))
        );
        List<Cargo> result = dao.modifyCargo(cargo);
        result.forEach(System.out::println);
    }

    private void removeCargo() throws SQLException {
        int id = Integer.parseInt(read("삭제할 창고 ID"));
        Cargo cargo = new Cargo();
        cargo.setCargoId(id);
        int deleted = dao.removeCargo(cargo);
        println(deleted > 0 ? "삭제 성공" : "삭제 실패");
    }

    private void getAllCargos() throws SQLException {
        List<Cargo> all = dao.getAllCargos();
        println("조회된 창고 수: " + all.size());
        all.forEach(System.out::println);
    }

    private void getCargoByName() throws SQLException {
        String name = read("조회할 이름");
        List<Cargo> result = dao.getCargoByCargoName(name);
        result.forEach(System.out::println);
    }

    private void getCargoByAddress() throws SQLException {
        String address = read("조회할 주소");
        List<Cargo> result = dao.getCargoByCargoAddress(address);
        result.forEach(System.out::println);
    }

    private void getCargoByGrade() throws SQLException {
        String grade = read("조회할 등급");
        List<Cargo> result = dao.getCargoByCargoGrade(grade);
        result.forEach(System.out::println);
    }

    private String read(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine();
    }
    //private boolean login = false; // 로그인 시 설정

//    private void checkAdmin() throws SQLException {
//        if (!manager.getPositon().equals("총관리자")) {
//            println("총관리자만 접근 가능한 기능입니다.");
//            throw new SecurityException("관리자 권한이 필요합니다.");
//        }
//    }


}
