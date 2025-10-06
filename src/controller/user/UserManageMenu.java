package controller.user;

import java.io.IOException;

public interface UserManageMenu {

    boolean run();

    boolean delete() throws IOException;

    void update() throws IOException;

    void read() throws IOException;
}
