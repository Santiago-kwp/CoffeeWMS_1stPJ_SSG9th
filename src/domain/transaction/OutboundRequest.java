package domain.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OutboundRequest {
  private String outboundRequestId;
  private String memberId;
  private String managerId;
  private String coffeeId;
  private int quantity;
  private Date outboundRequestDate;
  private String jsonItems;

  public String getJsonItems() {
    return jsonItems;
  }

  public void setJsonItems(String jsonItems) {
    this.jsonItems = jsonItems;
  }

  public String getOutboundRequestId() {
    return outboundRequestId;
  }

  public void setOutboundRequestId(String outboundRequestId) {
    this.outboundRequestId = outboundRequestId;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getManagerId() {
    return managerId;
  }

  public void setManagerId(String managerId) {
    this.managerId = managerId;
  }

  public String getCoffeeId() {
    return coffeeId;
  }

  public void setCoffeeId(String coffeeId) {
    this.coffeeId = coffeeId;
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
}
