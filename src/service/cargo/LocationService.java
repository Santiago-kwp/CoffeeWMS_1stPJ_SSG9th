package service.cargo;

import domain.cargo.dto.LocationDTO;
import java.sql.SQLException;
import java.util.List;

public interface LocationService {
    /**
     * 할당 가능한 모든 위치 목록을 조회합니다.
     * @return LocationDTO 리스트
     */
    List<LocationDTO> getAvailableLocations() throws SQLException;
}