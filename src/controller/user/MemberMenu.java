package controller.user;

import domain.user.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MemberMenu {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final User user;

    public MemberMenu(User user) {
        this.user = user;
    }

    public void run() {

    }
}
