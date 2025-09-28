package controller.user;

import constant.user.LoginPage;
import constant.user.MemberPage;
import constant.user.UserPage;
import domain.user.Member;
import domain.user.User;
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

    public void read() throws IOException {
        // 일반회원이면 자신만 조회
        System.out.println(UserPage.CURRENT_USER_SELECT);
        Member member = dao.searchUserDetails();
        // 현재 회원 정보 조회
        MemberPage.details(member);
    }

    public void update() throws IOException {
        User newUserInfo = inputNewMember();
        boolean ack = dao.updateUserInfo(newUserInfo);
        System.out.println(ack ? UserPage.USER_UPDATE : UserPage.USER_UPDATE_FAILED);
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

    public boolean delete() {
        try {
            System.out.print(UserPage.USER_DELETE_TITLE);
            String yesOrNo = input.readLine();
            if (!yesOrNo.equalsIgnoreCase("Y")) {
                System.out.println(UserPage.USER_NOT_DELETE);
                return false;
            }

            boolean ack = dao.deleteUserInfo();
            if (ack) {
                System.out.println(UserPage.USER_DELETE);
                return true;
            }
            System.out.println(UserPage.USER_DELETE_FAILED);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
