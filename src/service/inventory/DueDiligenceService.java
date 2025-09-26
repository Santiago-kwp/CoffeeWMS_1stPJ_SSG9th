package service.inventory;



import domain.inventory.DueDiligenceVO;
import domain.inventory.UserVO;
import java.util.List;
import model.inventory.DueDiligenceDAO;

public class DueDiligenceService {
  private final DueDiligenceDAO diligenceDAO = new DueDiligenceDAO();

  public List<DueDiligenceVO> getList(UserVO user, String coffeeName, String status, int sortKey, boolean isFlipped) {
    return diligenceDAO.getDueDiligenceList(user, coffeeName, status, sortKey, isFlipped);
  }

  public int create(UserVO user, DueDiligenceVO vo) {
    return diligenceDAO.createDueDiligence(user, vo);
  }

  public int update(UserVO user, DueDiligenceVO vo) {
    return diligenceDAO.updateDueDiligence(user, vo);
  }

  public int delete(UserVO user, String id) {
    return diligenceDAO.deleteDueDiligence(user, id);
  }

  public int approve(UserVO user, String id) {
    return diligenceDAO.approveDueDiligence(user, id);
  }

  public int reject(UserVO user, String id) {
    return diligenceDAO.rejectDueDiligence(user, id);
  }
}