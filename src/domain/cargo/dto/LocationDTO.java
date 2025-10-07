package domain.cargo.dto; // DTO는 dto 패키지에

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 위치 정보를 계층 간에 전달하기 위한 데이터 전송 객체(DTO)입니다.
 * cargoes, locations, location_places 테이블의 조인 결과를 담습니다.
 */
public class LocationDTO {
    private String locationPlaceId;
    private String cargoName;
    private String zoneName;
    private String rackName;
    private String cellName;

    public LocationDTO() {} // 기본 생성자

    // ResultSet으로부터 객체를 생성하는 생성자
    public LocationDTO(ResultSet rs) throws SQLException {
        this.locationPlaceId = rs.getString("location_place_id");
        this.cargoName = rs.getString("cargo_name");
        this.zoneName = rs.getString("zone_name");
        this.rackName = rs.getString("rack_name");
        this.cellName = rs.getString("cell_name");
    }

    // Getters and Setters
    public String getLocationPlaceId() { return locationPlaceId; }
    public void setLocationPlaceId(String locationPlaceId) { this.locationPlaceId = locationPlaceId; }
    public String getCargoName() { return cargoName; }
    public void setCargoName(String cargoName) { this.cargoName = cargoName; }
    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }
    public String getRackName() { return rackName; }
    public void setRackName(String rackName) { this.rackName = rackName; }
    public String getCellName() { return cellName; }
    public void setCellName(String cellName) { this.cellName = cellName; }

    @Override
    public String toString() {
        // 뷰에서 목록을 보여줄 때 사용될 형식
        return String.format("창고: %s, 구역: %s, 랙: %s, 셀: %s (ID: %s)",
                cargoName, zoneName, rackName, cellName, locationPlaceId);
    }
}