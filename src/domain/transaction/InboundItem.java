package domain.transaction;

public class InboundItem {
  private long inboundRequestItemId;
  private String inboundRequestId;
  private String coffeeId;
  private String locationPlaceId;
  private int quantity;

  public String getInboundRequestId() {
    return inboundRequestId;
  }

  public void setInboundRequestId(String inboundRequestId) {
    this.inboundRequestId = inboundRequestId;
  }

  // 생성자, getter 및 setter
  public InboundItem(String coffeeId, String locationPlaceId, int quantity, long inboundRequestItemId) {
    this.coffeeId = coffeeId;
    this.locationPlaceId = locationPlaceId;
    this.quantity = quantity;
    this.inboundRequestItemId = inboundRequestItemId;
  }

  public String getCoffeeId() {
    return coffeeId;
  }

  public void setCoffeeId(String coffeeId) {
    this.coffeeId = coffeeId;
  }

  public String getLocationPlaceId() {
    return locationPlaceId;
  }

  public void setLocationPlaceId(String locationPlaceId) {
    this.locationPlaceId = locationPlaceId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public long getInboundRequestItemId() {
    return inboundRequestItemId;
  }

  public void setInboundRequestItemId(long inboundRequestItemId) {
    this.inboundRequestItemId = inboundRequestItemId;
  }


}
