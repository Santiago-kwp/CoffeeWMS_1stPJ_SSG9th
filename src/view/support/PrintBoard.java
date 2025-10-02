package view.support;

import domain.support.Board;
import java.util.List;

public interface PrintBoard {
    void printAll(List<Board> boards);

    void printOne(Board board);

    void printTopNotices(List<Board> boards);
}
