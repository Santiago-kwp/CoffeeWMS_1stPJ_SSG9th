package model.cargo.cargoDaoImpl;

import java.sql.*;

import config.DBUtil;
import domain.cargo.cargoVo.Cargo;
import model.cargo.CargoDao;

import java.util.ArrayList;
import java.util.List;

public class CargoDaoImpl implements CargoDao {
    private final Connection conn;

    public CargoDaoImpl() throws SQLException {
        this.conn = DBUtil.getConnection();
    }

    @Override
    public List<Cargo> addCargo(Cargo cargo) throws SQLException {
        String sql = "CALL cargo_add(?, ?, ?, ?, ?, ?, ?)";
        try (CallableStatement pstmt = conn.prepareCall(sql)) {
            pstmt.setString(1, cargo.getCargoCode());
            pstmt.setString(2, cargo.getCargoName());
            pstmt.setString(3, cargo.getCargoAddress());
            pstmt.setString(4, cargo.getCargoGrade());
            pstmt.setInt(5, cargo.getCargoField());
            pstmt.setInt(6, cargo.getCargoTotalCapa());
            pstmt.setInt(7, cargo.getCargoUseCapa());

            boolean hasResult = pstmt.execute();
            if (hasResult || pstmt.getUpdateCount() > 0) {
                return getAllCargos();
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Cargo> modifyCargo(Cargo cargo) throws SQLException {
        String sql = "CALL cargo_modify(?, ?, ?, ?, ?, ?, ?, ?)";
        try (CallableStatement pstmt = conn.prepareCall(sql)) {
            pstmt.setInt(1, cargo.getCargoId());
            pstmt.setString(2, cargo.getCargoCode());
            pstmt.setString(3, cargo.getCargoName());
            pstmt.setString(4, cargo.getCargoAddress());
            pstmt.setString(5, cargo.getCargoGrade());
            pstmt.setInt(6, cargo.getCargoField());
            pstmt.setInt(7, cargo.getCargoTotalCapa());
            pstmt.setInt(8, cargo.getCargoUseCapa());

            pstmt.executeUpdate();
            return getAllCargos();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int removeCargo(Cargo cargo) throws SQLException {
        String sql = "CALL cargo_remove(?)";
        try (CallableStatement pstmt = conn.prepareCall(sql)) {
            pstmt.setInt(1, cargo.getCargoId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int removeCargoName(String cargoName) throws SQLException {
        String sql = "DELETE FROM cargoes WHERE cargo_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cargoName);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Cargo> getAllCargos() throws SQLException {
        List<Cargo> cargoList = new ArrayList<>();
        String sql = "CALL cargo_total_get()";
        try (CallableStatement pstmt = conn.prepareCall(sql)) {
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cargoList;
    }

    @Override
    public List<Cargo> getCargoByCargoName(String cargoName) throws SQLException {
        List<Cargo> cargoList = new ArrayList<>();
        String sql = "CALL cargo_name_get(?)";
        try (CallableStatement pstmt = conn.prepareCall(sql)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cargoList;
    }

    @Override
    public List<Cargo> getCargoByCargoAddress(String cargoAddress) throws SQLException {
        List<Cargo> cargoList = new ArrayList<>();
        String sql = "CALL cargo_address_get(?)";
        try (CallableStatement pstmt = conn.prepareCall(sql)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cargoList;
    }

    @Override
    public List<Cargo> getCargoByCargoGrade(String cargoGrade) throws SQLException {
        List<Cargo> cargoList = new ArrayList<>();
        String sql = "CALL cargo_grade_get(?)";
        try (CallableStatement pstmt = conn.prepareCall(sql)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            try {

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return cargoList;
    }

    @Override
    public boolean addCargoToManager(String manangerId, int cargoId) throws SQLException {
        String sql = "Call add_cargo_to_manager(?, ?, ?, ?)";
        try (CallableStatement pstmt = conn.prepareCall(sql)) {
            pstmt.setString(1, manangerId);
            pstmt.setInt(2, cargoId);
            pstmt.setInt(3, 10); // 한 사람당 관리하는 최대 창고 갯수
            pstmt.registerOutParameter(4, Types.BOOLEAN);
            pstmt.execute();
            return pstmt.getBoolean(4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
