package domain.transaction;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InboundRequest {
  String inboundRequestId;
  String memberId;
  String managerId;
  boolean inboundRequestApproved = false;
  Date inboundDate;
  String inboundReceipt;

  public InboundRequest(String inboundRequestId, String memberId, String managerId) {
    this.inboundRequestId = inboundRequestId;
    this.memberId = memberId;
    this.managerId = managerId;
  }




}
