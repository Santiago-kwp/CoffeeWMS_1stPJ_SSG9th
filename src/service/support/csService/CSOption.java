package service.support.csService;

public interface CSOption {
    // 뒤로가기 여부를 확인
    void backOption();

    // 공지사항에서 상단 고정 여부에 대해서 'Y' 와 'N' 으로 여부를 결정
    boolean yesOrNo(Character c);
}