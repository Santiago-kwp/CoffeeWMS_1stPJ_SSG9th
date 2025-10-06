package controller.user;

import constant.user.MemberPage;
import constant.user.UserPage;
import domain.user.Member;
import domain.user.User;
import exception.user.InvalidUserDataException;
import java.io.IOException;
import model.user.MemberDAO;

public class MemberManageMenu implements UserManageMenu {

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
        String yesOrNo = inputView.promptAndRead(UserPage.USER_UPDATE_TITLE.toString());
        if (!yesOrNo.equalsIgnoreCase("Y")) {
            System.out.println(UserPage.TO_PREVIOUS_MENU);
            return;
        }
        User newUserInfo = inputView.inputNewMemberInfo();
        Member updatedMember = dao.updateUserInfo(newUserInfo);
        validCheck.checkUserUpdated(updatedMember);
        System.out.println(UserPage.USER_UPDATE);
    }

    @Override
    public boolean delete() {
        try {
            String yesOrNo = inputView.promptAndRead(UserPage.USER_DELETE_TITLE.toString());
            if (!yesOrNo.equalsIgnoreCase("Y")) {
                System.out.println(UserPage.USER_NOT_DELETE);
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
