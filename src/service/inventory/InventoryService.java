package service.inventory;


import domain.inventory.InventoryVO;
import domain.inventory.UserVO;
import java.util.List;
import model.inventory.InventoryDAO;

public class InventoryService {

//  private final InventoryDAO inventoryDAO = new InventoryDAO();
  private final InventoryDAO inventoryDAO = InventoryDAO.getInstance(); // kwp - singleton으로 변경

  public List<InventoryVO> searchInventory(UserVO user, String coffeeName, String beanType,
      String isDecaf, String cargoName, String companyName, int sortKey, boolean isFlipped) {
    return inventoryDAO.searchInventory(user, coffeeName, beanType, isDecaf, cargoName, companyName,
        sortKey, isFlipped);
  }
}