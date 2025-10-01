package service.support.inputService;

import domain.support.Board;

public interface BoardInput {
    Board dataInput(String managerId);

    Board dataUpdate(Integer readChoice, String managerId);
}
