package model.transaction;

import domain.transaction.Coffee;
import domain.transaction.LocationPlace;
import domain.transaction.dto.InboundRequestDTO;

import java.sql.SQLException;
import java.util.List;

public interface InboundSearchDAO {


    // 커피 상품 조회
    public List<Coffee> getAllCoffees();
    // 적치 가능한 위치 조회
    public List<LocationPlace> getAvailableLocationPlaces ();

    // 입고 요청 상세 검색
    public List<InboundRequestDTO> getInboundRequestDetails(
            String memberId, String requestStatus, String managerId) throws SQLException;

}