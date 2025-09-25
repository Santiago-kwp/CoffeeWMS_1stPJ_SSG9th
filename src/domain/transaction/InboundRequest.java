package domain.transaction;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InboundRequest {
  private String inboundRequestId;
  private String memberId;
  private String managerId;
  private String requestItemsJson;
  private Date inboundRequestDate;

  public void setInboundRequestId(String inboundRequestId) {
    this.inboundRequestId = inboundRequestId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public void setManagerId(String managerId) {
    this.managerId = managerId;
  }

  public void setRequestItemsJson(String requestItemsJson) {
    this.requestItemsJson = requestItemsJson;
  }

  public void setInboundRequestDate(Date inboundRequestDate) {
    this.inboundRequestDate = inboundRequestDate;
  }
}
