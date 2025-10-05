package controller.user;

import constant.user.LoginPage;
import constant.user.MemberPage;
import constant.user.UserPage;
import constant.user.validation.InputValidCheck;
import domain.user.Manager;
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
        System.out.print(UserPage.USER_UPDATE_TITLE);
        String yesOrNo = input.readLine();
        if (!yesOrNo.equalsIgnoreCase("Y")) {
            System.out.println(UserPage.TO_PREVIOUS_MENU);
            return;
        }
        User newUserInfo = inputNewMemberInfo();
        Member updatedMember = dao.updateUserInfo(newUserInfo);
        validCheck.checkUserUpdated(updatedMember);
        System.out.println(UserPage.USER_UPDATE);
    }

    private User inputNewMemberInfo() throws IOException, InvalidUserDataException {
        System.out.println(MemberPage.MEMBER_UPDATE_TITLE);

        System.out.println(LoginPage.INPUT_PWD);
        String userPwd = input.readLine();
        System.out.println(LoginPage.INPUT_COMPANY_NAME);
        String companyName = input.readLine();
        System.out.println(LoginPage.INPUT_PHONE);
        String phone = input.readLine();
        System.out.println(LoginPage.INPUT_EMAIL);
        String email = input.readLine();
        System.out.println(LoginPage.INPUT_COMPANY_CODE);
        String companyCode = input.readLine();
        System.out.println(LoginPage.INPUT_ADDRESS);
        String address = input.readLine();

        return User.Builder.update(userPwd, companyName)
                .phone(phone)
                .email(email)
                .companyCode(companyCode)
                .address(address)
                .build();
    }

    @Override
    public boolean delete() {
        try {
            System.out.print(UserPage.USER_DELETE_TITLE);
            String yesOrNo = input.readLine();
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
