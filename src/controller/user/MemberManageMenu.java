package controller.user;

import constant.user.MemberPage;
import constant.user.UserPage;
import domain.user.Member;
import domain.user.User;
import exception.user.InvalidUserDataException;
import java.io.IOException;
import model.user.MemberDAO;

public class MemberManageMenu extends AbstractUserManageMenu {

    private final MemberDAO dao;

    public MemberManageMenu(Member member) {
        this.dao = new MemberDAO(member);
    }

    @Override
    public void printMenu() {
        System.out.print(MemberPage.MEMBER_MANAGEMENT_MENU_TITLE);
    }

    @Override
    public void read() {
        System.out.println(UserPage.CURRENT_USER_SELECT);
        Member member = dao.searchUserDetails();
        validCheck.checkUserFound(member);
        MemberPage.details(member);
    }

    @Override
    public void update() throws IOException, InvalidUserDataException {
        if (consoleView.checkCancel(UserPage.USER_UPDATE_TITLE.toString(), UserPage.TO_PREVIOUS_MENU.toString())) {
            return;
        }
        User newUserInfo = consoleView.inputMemberInfo(true);
        Member updatedMember = dao.updateUserInfo(newUserInfo);
        validCheck.checkUserUpdated(updatedMember);
        System.out.println(UserPage.USER_UPDATE);
    }

    @Override
    public boolean delete() {
        try {
            if (consoleView.checkCancel(UserPage.USER_DELETE_TITLE.toString(), UserPage.USER_NOT_DELETE.toString())) {
                return false;
            }
            boolean ack = dao.deleteUserInfo();
            validCheck.checkUserDeleted(ack);
            System.out.println(UserPage.USER_DELETE);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
