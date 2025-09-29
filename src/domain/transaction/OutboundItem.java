package domain.transaction;

import java.util.Date;
import java.util.Objects;

public class OutboundItem {
  private String memberId;
  private String coffeeId;
  private String coffeeName;
  private String outboundRequestId;
  private long outboundRequestItemId;
  private String locationPlaceId;
  private int quantity;
  private Date outboundRequestDate;

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getCoffeeId() {
    return coffeeId;
  }

  public void setCoffeeId(String coffeeId) {
    this.coffeeId = coffeeId;
  }

  public String getCoffeeName() {
    return coffeeName;
  }

  public void setCoffeeName(String coffeeName) {
    this.coffeeName = coffeeName;
  }

  public String getOutboundRequestId() {
    return outboundRequestId;
  }

  public void setOutboundRequestId(String outboundRequestId) {
    this.outboundRequestId = outboundRequestId;
  }

  public long getOutboundRequestItemId() {
    return outboundRequestItemId;
  }

  public void setOutboundRequestItemId(long outboundRequestItemId) {
    this.outboundRequestItemId = outboundRequestItemId;
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

  public Date getOutboundRequestDate() {
    return outboundRequestDate;
  }

  public void setOutboundRequestDate(Date outboundRequestDate) {
    this.outboundRequestDate = outboundRequestDate;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof OutboundItem that)) return false;
      return outboundRequestItemId == that.outboundRequestItemId;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(outboundRequestItemId);
  }

  public OutboundItem() {}

  // 생성자, getter 및 setter
  public OutboundItem(String memberId, String coffeeId, String coffeeName, String outboundRequestId,
                      long outboundRequestItemId, int quantity, Date outboundRequestDate) {
    this.memberId = memberId;
    this.coffeeId = coffeeId;
    this.coffeeName = coffeeName;
    this.outboundRequestId = outboundRequestId;
    this.outboundRequestItemId = outboundRequestItemId;
    this.quantity = quantity;
    this.outboundRequestDate = outboundRequestDate;


  }



}
