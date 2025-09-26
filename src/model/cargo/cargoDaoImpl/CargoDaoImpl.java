package model.cargo.cargoDaoImpl;

import java.sql.*;

import config.cargo.DBUtil;
import domain.cargo.cargoVo.Cargo;

import model.cargo.CargoDao;

import java.util.ArrayList;
import java.util.List;

public class CargoDaoImpl implements CargoDao {
    //데이터 베이스 연결 객체
    private final Connection conn;
    // 창고 데이터 관리 리스트
    private final List<Cargo> cargoList = new ArrayList<>();

    // 생성자에서 DB 연결 초기화
    public CargoDaoImpl() throws SQLException {
        this.conn = DBUtil.getConnection();
    }


    // 창고 추가
    @Override
    public List<Cargo> addCargo(Cargo cargo) throws SQLException {
        String sql = "call cargo_add(?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cargo.getCargoCode());
            pstmt.setString(2, cargo.getCargoName());
            pstmt.setString(3, cargo.getCargoAddress());
            pstmt.setString(4, cargo.getCargoGrade());
            pstmt.setInt(5, cargo.getCargoField());
            pstmt.setInt(6, cargo.getCargoTotalCapa());
            pstmt.setInt(7, cargo.getCargoUseCapa());

            //서버로 전송 후 결과값
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cargo.setCargoId(rs.getInt(1)); // 생성된 ID 설정
                }

                return getAllCargos();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    // 창고 수정
    @Override
    public List<Cargo> modifyCargo(Cargo cargo) throws SQLException {
        String sql = "call cargo_modify(?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cargo.getCargoId());
            pstmt.setString(2, cargo.getCargoCode());
            pstmt.setString(3, cargo.getCargoName());
            pstmt.setString(4, cargo.getCargoAddress());
            pstmt.setString(5, cargo.getCargoGrade());
            pstmt.setInt(6, cargo.getCargoField());
            pstmt.setInt(7, cargo.getCargoTotalCapa());
            pstmt.setInt(8, cargo.getCargoUseCapa());

            //서버로 전송 후 결과값
            pstmt.executeUpdate();

            return getAllCargos();





            }
        }


    // 삭제
    @Override
    public int removeCargo(Cargo cargo) throws SQLException {
        String sql = "call cargo_remove(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cargo.getCargoId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }

    }

    // 전체조회
    @Override
    public List<Cargo> getAllCargos() throws SQLException {
        List<Cargo> cargoList = new ArrayList<>();
        String sql = "call cargo_total_get";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cargo cargo = new Cargo();
                cargo.setCargoId(rs.getInt("cargo_id"));
                cargo.setCargoCode(rs.getString("cargo_code"));
                cargo.setCargoName(rs.getString("cargo_name"));
                cargo.setCargoAddress(rs.getString("cargo_address"));
                cargo.setCargoGrade(rs.getString("cargo_grade"));
                cargo.setCargoField(rs.getInt("cargo_field"));
                cargo.setCargoTotalCapa(rs.getInt("cargo_total_capa"));
                cargo.setCargoUseCapa(rs.getInt("cargo_use_capa"));
                cargo.setUtilization(rs.getDouble("utilization"));

                cargoList.add(cargo);
            }

        }

        return cargoList;
    }


    // 이름별 조회
    @Override
    public List<Cargo> getCargoByCargoName(String cargoName) throws SQLException {
        List<Cargo> cargoList = new ArrayList<>();
        String sql = "call cargo_name_get(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cargoName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cargo cargo = new Cargo();
                    cargo.setCargoId(rs.getInt("cargo_id"));
                    cargo.setCargoCode(rs.getString("cargo_code"));
                    cargo.setCargoName(rs.getString("cargo_name"));
                    cargo.setCargoAddress(rs.getString("cargo_address"));
                    cargo.setCargoGrade(rs.getString("cargo_grade"));
                    cargo.setCargoField(rs.getInt("cargo_field"));
                    cargo.setCargoTotalCapa(rs.getInt("cargo_total_capa"));
                    cargo.setCargoUseCapa(rs.getInt("cargo_use_capa"));
                    cargo.setUtilization(rs.getDouble("utilization"));
                    cargoList.add(cargo);
                }
            }
        }
        return cargoList;
    }

    // 주소별 조회
    @Override
    public List<Cargo> getCargoByCargoAddress(String cargoAddress) throws SQLException {
        List<Cargo> cargoList = new ArrayList<>();
        String sql = "call cargo_address_get(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cargoAddress);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cargo cargo = new Cargo();
                    cargo.setCargoId(rs.getInt("cargo_id"));
                    cargo.setCargoCode(rs.getString("cargo_code"));
                    cargo.setCargoName(rs.getString("cargo_name"));
                    cargo.setCargoAddress(rs.getString("cargo_address"));
                    cargo.setCargoGrade(rs.getString("cargo_grade"));
                    cargo.setCargoField(rs.getInt("cargo_field"));
                    cargo.setCargoTotalCapa(rs.getInt("cargo_total_capa"));
                    cargo.setCargoUseCapa(rs.getInt("cargo_use_capa"));
                    cargo.setUtilization(rs.getDouble("utilization"));
                    cargoList.add(cargo);

                }
            }
        }
        return cargoList;
    }

    // 등급별 조회
    @Override
    public List<Cargo> getCargoByCargoGrade(String cargoGrade) throws SQLException {
        List<Cargo> cargoList = new ArrayList<>();
        String sql = "call cargo_grade_get(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cargoGrade);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cargo cargo = new Cargo();
                    cargo.setCargoId(rs.getInt("cargo_id"));
                    cargo.setCargoCode(rs.getString("cargo_code"));
                    cargo.setCargoName(rs.getString("cargo_name"));
                    cargo.setCargoAddress(rs.getString("cargo_address"));
                    cargo.setCargoGrade(rs.getString("cargo_grade"));
                    cargo.setCargoField(rs.getInt("cargo_field"));
                    cargo.setCargoTotalCapa(rs.getInt("cargo_total_capa"));
                    cargo.setCargoUseCapa(rs.getInt("cargo_use_capa"));
                    cargo.setUtilization(rs.getDouble("utilization"));
                    cargoList.add(cargo);

                }
            }
        }
        return cargoList;
    }
}

