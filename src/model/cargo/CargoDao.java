package model.cargo;

import domain.cargo.cargoVo.Cargo;


import java.sql.SQLException;
import java.util.List;

public interface CargoDao {
    /**
     * 창고등록
     * 새로운 창고 데이터를 입력하고, 생성된 ID가 포함된 객체를 반환
     *
     * @param CargoId가 없는 신규 Cargo 객체 (ID없음)
     * @return DB에 저장된 후 ID까지 부여 받은 Cargo 객체(ID 있음)
     * @throws SQLException
     */
    List<Cargo> addCargo(Cargo cargo) throws SQLException;

    /**
     * 창고수정
     * 입력 받은 창고 ID에 해당하는 객체 정보 수정
     *
     * @param cargo 수정할 Cargo 객체
     * @return 입력 받은 CargoID와 DB에 저장된 CargoID와 비교 후 동일시 수정 데이터
     * @throws SQLException
     */
    List<Cargo> modifyCargo(Cargo cargo) throws SQLException;


    /**
     * 창고 삭제
     * 입력 받은 창고 ID에 해당하는 객체 삭제
     *
     * @param cargo 삭제할 Cargo 객체
     * @return 지운 행
     * @throws SQLException
     */
    int removeCargo(Cargo cargo) throws SQLException;

    /**
     * 전체조회
     * 데이터가 있는 모든 창고 정보 출력
     *
     * @return 싹다
     * @throws SQLException
     */
    List<Cargo> getAllCargos() throws SQLException;

    /**
     * 창고 이름 조회
     * 입력받은 창고 이름으로 그에 해당하는 창고 데이터 출력
     *
     * @param cargoName을 검색할 창고이름
     * @return 검색 조건에 맞는 Cargo 객체 정보 전부 출력
     * @throws SQLException
     */
    List<Cargo> getCargoByCargoName(String cargoName) throws SQLException;

    /**
     * 창고 소재지 조회
     * 입력 받은 창고 소재지로 그에 해당하는 창고 데이터 출력
     *
     * @param cargoAddress를 검색할 소재지
     * @return 검색 조건에 맞는 Cargo 객체 정보 전부 출력
     * @throws SQLException
     */
    List<Cargo> getCargoByCargoAddress(String cargoAddress) throws SQLException;


    /**
     * 창고 등급별 조회
     * 입력 받은 창고 등급으로 그에 해당하는 창고 데이터 출력
     *
     * @param cargoGrade를 검색할 등급
     * @return 검색 조건에 맞는 Cargo 객체 정보 전부 출력
     * @throws SQLException
     */
    List<Cargo> getCargoByCargoGrade(String cargoGrade) throws SQLException;

}
