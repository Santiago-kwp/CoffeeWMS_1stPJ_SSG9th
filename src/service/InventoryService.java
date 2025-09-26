package service;


import domain.inventory.InventoryVO;
import domain.inventory.UserVO;
import java.util.List;
import model.inventory.InventoryDAO;

public class InventoryService {

  private final InventoryDAO inventoryDAO = new InventoryDAO();

  public List<InventoryVO> searchInventory(UserVO user, String coffeeName, String beanType,
      String isDecaf, String cargoName, String companyName, int sortKey, boolean isFlipped) {
    return inventoryDAO.searchInventory(user, coffeeName, beanType, isDecaf, cargoName, companyName,
        sortKey, isFlipped);
  }
}