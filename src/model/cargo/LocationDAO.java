package model.cargo;

import domain.cargo.dto.LocationDTO;
import java.sql.SQLException;
import java.util.List;

public interface LocationDAO {
    /**
     * 할당 가능한 모든 위치 목록을 조회하여 LocationDTO 리스트로 반환합니다.
     * @return LocationDTO 리스트
     */
    List<LocationDTO> findAvailableLocations() throws SQLException;
}