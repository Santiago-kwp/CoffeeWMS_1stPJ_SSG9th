package model.cargo;

import config.DBUtil;
import domain.cargo.dto.LocationDTO;
import model.transaction.InboundRequestItemDAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImpl implements LocationDAO {

    // 1. private static final 인스턴스 변수 선언 및 초기화
    private static final LocationDAOImpl instance = new LocationDAOImpl();

    // 2. private 생성자 선언하여 외부에서 new 키워드로 생성하는 것을 방지
    private LocationDAOImpl() {}

    // 3. public static getInstance() 메서드로 유일한 인스턴스에 접근하도록 함
    public static LocationDAOImpl getInstance() {
        return instance;
    }

    @Override
    public List<LocationDTO> findAvailableLocations() throws SQLException {
        List<LocationDTO> locations = new ArrayList<>();
        // 3개 테이블을 조인하여 사람이 읽기 쉬운 위치 정보를 가져옵니다.
        String sql = "SELECT lp.location_place_id, c.cargo_name, lp.zone_name, lp.rack_name, lp.cell_name " +
                "FROM locations l " +
                "JOIN location_places lp ON l.location_place_id = lp.location_place_id " +
                "JOIN cargoes c ON l.cargo_id = c.cargo_id " +
                "ORDER BY c.cargo_name, lp.zone_name, lp.rack_name, lp.cell_name";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // ResultSet 생성자를 사용하여 DTO 객체 생성
                locations.add(new LocationDTO(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding available locations: " + e.getMessage());
            throw e;
        }
        return locations;
    }
}