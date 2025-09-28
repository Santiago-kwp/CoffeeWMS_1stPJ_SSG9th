package domain.transaction;

import java.util.Date;
import java.util.Objects;

public class InboundItem {
  private String memberId;
  private String coffeeId;
  private String coffeeName;
  private String inboundRequestId;
  private long inboundRequestItemId;
  private String locationPlaceId;
  private int quantity;
  private Date inboundRequestDate;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof InboundItem that)) return false;
      return inboundRequestItemId == that.inboundRequestItemId;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(inboundRequestItemId);
  }

  public InboundItem() {}

  // 생성자, getter 및 setter
  public InboundItem(String memberId, String coffeeId, String coffeeName, String inboundRequestId,
                     long inboundRequestItemId, int quantity, Date inboundRequestDate) {
    this.memberId = memberId;
    this.coffeeId = coffeeId;
    this.coffeeName = coffeeName;
    this.inboundRequestId = inboundRequestId;
    this.inboundRequestItemId = inboundRequestItemId;
    this.quantity = quantity;
    this.inboundRequestDate = inboundRequestDate;


  }

  public Date getInboundRequestDate() {
    return inboundRequestDate;
  }

  public void setInboundRequestDate(Date inboundRequestDate) {
    this.inboundRequestDate = inboundRequestDate;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getCoffeeName() {
    return coffeeName;
  }

  public void setCoffeeName(String coffeeName) {
    this.coffeeName = coffeeName;
  }



  public String getInboundRequestId() {
    return inboundRequestId;
  }

  public void setInboundRequestId(String inboundRequestId) {
    this.inboundRequestId = inboundRequestId;
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
