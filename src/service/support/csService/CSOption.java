package service.support.csService;

public interface CSOption {
    // 선택지가 뒤로가기 밖에 없을 경우 뒤로가리 여부를 확인할때 사용하는 기능이다.
    void backOption();

    // 공지사항에 상단 고정 여부에 대해서 'Y' 와 'N' 으로 여부를 결정하는 기능이다.
    boolean yesOrNo(Character c);
}