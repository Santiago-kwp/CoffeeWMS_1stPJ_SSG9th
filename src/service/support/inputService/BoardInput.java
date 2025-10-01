package service.support.inputService;

import domain.support.Board;

public interface BoardInput {
    // 데이터 입력
    Board dataInput(String managerId);

    // 데이터 수정
    Board dataUpdate(Integer readChoice, String managerId);
}
