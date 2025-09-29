package domain.transaction;

public class LocationPlace {
  private String locationPlaceId;
  private String zoneId;
  private String zoneName;
  private String rackId;
  private String rackName;
  private String cellId;
  private String cellName;

  public LocationPlace(String locationPlaceId, String zoneId, String zoneName, String rackId,
      String rackName, String cellId, String cellName) {
    this.locationPlaceId = locationPlaceId;
    this.zoneId = zoneId;
    this.zoneName = zoneName;
    this.rackId = rackId;
    this.rackName = rackName;
    this.cellId = cellId;
    this.cellName = cellName;
  }

  @Override
  public String toString() {
    return String.format("| %-12s\t | %-5s\t | %-20s\t | %-12s\t | %-20s\t | %-12s\t |",
        locationPlaceId, zoneId, zoneName, rackId, rackName, cellId, cellName);
  }

  public String getLocationPlaceId() {
    return locationPlaceId;
  }

  public void setLocationPlaceId(String locationPlaceId) {
    this.locationPlaceId = locationPlaceId;
  }

  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  public String getZoneName() {
    return zoneName;
  }

  public void setZoneName(String zoneName) {
    this.zoneName = zoneName;
  }

  public String getRackId() {
    return rackId;
  }

  public void setRackId(String rackId) {
    this.rackId = rackId;
  }

  public String getRackName() {
    return rackName;
  }

  public void setRackName(String rackName) {
    this.rackName = rackName;
  }

  public String getCellId() {
    return cellId;
  }

  public void setCellId(String cellId) {
    this.cellId = cellId;
  }

  public String getCellName() {
    return cellName;
  }

  public void setCellName(String cellName) {
    this.cellName = cellName;
  }
}
