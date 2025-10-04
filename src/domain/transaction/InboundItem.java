package domain.transaction;

import java.sql.Date;

// 6. InboundItem (inbound_request_items 테이블)
public class InboundItem {
  private Long inboundRequestItemId; // bigint Auto_Increment
  private String inboundRequestId;
  private String memberId;
  private String managerId;
  private String coffeeId;
  private Integer inboundRequestQuantity;
  private Date inboundRequestDate;

  // Constructors
  public InboundItem() {}

  public InboundItem(Long inboundRequestItemId, String inboundRequestId, String memberId,
                              String managerId, String coffeeId, Integer inboundRequestQuantity,
                              Date inboundRequestDate) {
    this.inboundRequestItemId = inboundRequestItemId;
    this.inboundRequestId = inboundRequestId;
    this.memberId = memberId;
    this.managerId = managerId;
    this.coffeeId = coffeeId;
    this.inboundRequestQuantity = inboundRequestQuantity;
    this.inboundRequestDate = inboundRequestDate;
  }

  // Getters and Setters
  public Long getInboundItemId() { return inboundRequestItemId; }
  public void setInboundItemId(Long inboundRequestItemId) { this.inboundRequestItemId = inboundRequestItemId; }
  public String getInboundRequestId() { return inboundRequestId; }
  public void setInboundRequestId(String inboundRequestId) { this.inboundRequestId = inboundRequestId; }
  public String getMemberId() { return memberId; }
  public void setMemberId(String memberId) { this.memberId = memberId; }
  public String getManagerId() { return managerId; }
  public void setManagerId(String managerId) { this.managerId = managerId; }
  public String getCoffeeId() { return coffeeId; }
  public void setCoffeeId(String coffeeId) { this.coffeeId = coffeeId; }
  public Integer getInboundRequestQuantity() { return inboundRequestQuantity; }
  public void setInboundRequestQuantity(Integer inboundRequestQuantity) { this.inboundRequestQuantity = inboundRequestQuantity; }
  public Date getInboundRequestDate() { return inboundRequestDate; }
  public void setInboundRequestDate(Date inboundRequestDate) { this.inboundRequestDate = inboundRequestDate; }


  @Override
  public String toString() {
    return "InboundItem{" +
            "inboundRequestItemId=" + inboundRequestItemId +
            ", inboundRequestId='" + inboundRequestId + '\'' +
            ", coffeeId='" + coffeeId + '\'' +
            ", inboundRequestQuantity=" + inboundRequestQuantity +
            '}';
  }
}