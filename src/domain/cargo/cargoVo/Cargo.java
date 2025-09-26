package domain.cargo.cargoVo;

// 창고 정보를 담는 vo 클래스
public class Cargo {

    //각각의 칼럼에 들어갈 데이터를 담을 변수 선언
    private int CargoId; // 창고 고유 번호
    private String CargoCode; // 창고 코드 Ex BU,GJ,DD
    private String CargoName; // 창고 이름
    private String CargoAddress;// 창고 소재지
    private String CargoGrade; // 창고 등급 Ex 메인,서브
    private int CargoField; // 창고 평수
    private int CargoTotalCapa; // 창고 총 용량
    private int CargoUseCapa;// 창고 사용중인 용량
    private double Utilization; // 창고 사용률 프리시저로 계산한 값을 받는 것 단위는 PLT 1PLT = 5포대

    // 객체를 문자열로 표현 할 때 쓰는 메서드
    @Override
    public String toString() {
        return String.format(
                "[%s] \n -이름: %s\n - 주소: %s\n - 등급: %s\n - 평수: %d평\n - 총량: %dPLT\n - 사용량: %dPLT\n - 사용률: %.2f%%",
                CargoCode, CargoName, CargoAddress,
                CargoGrade, CargoField, CargoTotalCapa, CargoUseCapa, Utilization
        );
    }



    public Cargo() {
    }

    //cargo 생성자 생성 한방에 모든 값을 받기 위해서 오버로딩 씀
    public Cargo(int CargoId, String CargoCode, String CargoName, String CargoAddress, String CargoGrade, int CargoField, int CargoTotalCapa, int CargoUseCapa) {
        // 전체 필드 초기화
        this.CargoId = CargoId;
        this.CargoCode = CargoCode;
        this.CargoName = CargoName;
        this.CargoAddress = CargoAddress;
        this.CargoGrade = CargoGrade;
        this.CargoField = CargoField;
        this.CargoTotalCapa = CargoTotalCapa;
        this.CargoUseCapa = CargoUseCapa;
    }

    public int getCargoId() {// get set 쓰는 이유 넣다 뺏다 하기 위해서
        return CargoId;
    }

    public void setCargoId(int CargoId) {
        this.CargoId = CargoId;
    }

    public String getCargoCode() {
        return CargoCode;
    }

    public void setCargoCode(String CargoCode) {
        this.CargoCode = CargoCode;
    }

    public String getCargoName() {
        return CargoName;
    }

    public void setCargoName(String CargoName) {
        this.CargoName = CargoName;
    }

    public String getCargoAddress() {
        return CargoAddress;
    }

    public void setCargoAddress(String CargoAddress) {
        this.CargoAddress = CargoAddress;
    }

    public String getCargoGrade() {
        return CargoGrade;
    }

    public void setCargoGrade(String CargoGrade) {
        this.CargoGrade = CargoGrade;
    }

    public int getCargoField() {
        return CargoField;
    }

    public void setCargoField(int CargoField) {
        this.CargoField = CargoField;
    }

    public int getCargoTotalCapa() {
        return CargoTotalCapa;
    }

    public void setCargoTotalCapa(int CargoTotalCapa) {
        this.CargoTotalCapa = CargoTotalCapa;
    }

    public int getCargoUseCapa() {
        return CargoUseCapa;
    }

    public void setCargoUseCapa(int CargoUseCapa) {
        this.CargoUseCapa = CargoUseCapa;
    }

    public double getUtilization() {
        return Utilization;
    }

    public void setUtilization(double Utilization) {
        this.Utilization = Utilization;
    }


}
