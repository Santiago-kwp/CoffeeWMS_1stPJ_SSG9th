package domain.cargo.vo; // VO는 vo 패키지에

import java.util.Objects;

/**
 * 창고의 구체적인 위치를 나타내는 불변(Immutable) 값 객체(VO)입니다.
 */
public final class LocationPlaceVO {
    private final String locationPlaceId;
    private final String zoneId;
    private final String zoneName;
    private final String rackId;
    private final String rackName;
    private final String cellId;
    private final String cellName;

    public LocationPlaceVO(String locationPlaceId, String zoneId, String zoneName, String rackId,
                           String rackName, String cellId, String cellName) {
        this.locationPlaceId = locationPlaceId;
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.rackId = rackId;
        this.rackName = rackName;
        this.cellId = cellId;
        this.cellName = cellName;
    }

    // Getter만 제공 (Setter 없음)
    public String getLocationPlaceId() { return locationPlaceId; }
    public String getZoneName() { return zoneName; }
    // ... 다른 모든 필드에 대한 Getter ...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationPlaceVO that = (LocationPlaceVO) o;
        return Objects.equals(locationPlaceId, that.locationPlaceId); // ID가 같으면 같은 객체로 취급
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationPlaceId);
    }

    @Override
    public String toString() {
        return String.format("| %-12s | %-5s | %-20s | %-12s | %-20s | %-12s | %-20s |",
                locationPlaceId, zoneId, zoneName, rackId, rackName, cellId, cellName);
    }
}