package controller.user;

import constant.user.LoginPage;
import constant.user.MemberPage;
import constant.user.UserPage;
import domain.user.Member;
import domain.user.User;
import exception.user.UnableToReadUserException;
import exception.user.UserNotDeletedException;
import exception.user.UserNotUpdatedException;
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
        if (member == null) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }
        MemberPage.details(member);
    }

    @Override
    public void update() throws IOException {
        User newUserInfo = inputNewMember();
        boolean ack = dao.updateUserInfo(newUserInfo);
        if (!ack) {
            throw new UserNotUpdatedException(UserPage.USER_UPDATE_FAILED.toString());
        }
        System.out.println(UserPage.USER_UPDATE);
    }

    private User inputNewMember() throws IOException {
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

        User user = new User();
        user.setPwd(userPwd);
        user.setName(companyName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setCompanyCode(companyCode);
        user.setAddress(address);
        return user;
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
            if (!ack) {
                throw new UserNotDeletedException(UserPage.USER_DELETE_FAILED.toString());
            }
            System.out.println(UserPage.USER_DELETE);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
