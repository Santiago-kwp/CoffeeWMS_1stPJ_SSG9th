package domain.inventory;

import java.util.Date;
import lombok.Data;

@Data
public class InventoryVO {
  private String coffeeId;
  private String coffeeName;
  private String coffeeOrigin;
  private String coffeeRoasted;
  private String decaf;
  private int price;
  private String coffeeGrade;
  private String coffeeType;
  private int totalQuantity;
  private Date minInboundDate;
}
