package domain.inventory;

import java.util.Date;
import lombok.Data;

@Data
public class DueDiligenceVO {
  private String dueDiligenceId;
  private String inventoryId;
  private String coffeeName;
  private String log;
  private Date date;
  private String approvalStatus;
}
