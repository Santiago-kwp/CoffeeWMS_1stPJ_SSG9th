package service.cargo;

import domain.cargo.dto.LocationDTO;
import model.cargo.LocationDAO;
import model.cargo.LocationDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class LocationServiceImpl implements LocationService {

    // 1. private static final 인스턴스 변수 선언
    private static final LocationServiceImpl instance = new LocationServiceImpl();

    // 2. private 생성자
    private LocationServiceImpl() {}

    // 3. public static getInstance() 메서드
    public static LocationServiceImpl getInstance() {
        return instance;
    }

    private final LocationDAO locationDAO = LocationDAOImpl.getInstance();


    @Override
    public List<LocationDTO> getAvailableLocations() throws SQLException {
        // 현재는 단순히 DAO를 호출하지만,
        // 나중에 "이미 재고가 가득 찬 위치는 제외"와 같은 비즈니스 로직이 추가될 수 있습니다.
        List<LocationDTO> locations = locationDAO.findAvailableLocations();

        // 예: 비즈니스 로직 추가
        // if (locations.isEmpty()) {
        //     throw new BusinessException("사용 가능한 위치가 없습니다.");
        // }

        return locations;
    }
}