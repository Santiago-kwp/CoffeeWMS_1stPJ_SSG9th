package controller.user;

import constant.user.MemberPage;
import constant.user.UserPage;
import domain.user.Member;
import domain.user.User;
import java.io.IOException;
import service.user.MemberService;

public class MemberManageMenu extends AbstractUserManageMenu {

    private final MemberService memberService;
    private Member currentMember;

    public MemberManageMenu(Member member, MemberService memberService) {
        super();
        this.currentMember = member;
        this.memberService = memberService;
    }

    @Override
    public void printMenu() {
        System.out.print(MemberPage.MEMBER_MANAGEMENT_MENU_TITLE);
    }

    @Override
    public void read() {
        System.out.println(UserPage.CURRENT_USER_SELECT);
        currentMember = (Member) memberService.findMyDetails(currentMember.getId());
        MemberPage.details(currentMember);
    }

    @Override
    public void update() throws IOException {
        if (consoleView.checkCancel(UserPage.USER_UPDATE_TITLE.toString(), UserPage.TO_PREVIOUS_MENU.toString())) {
            return;
        }
        User newUserInfo = consoleView.inputMemberInfo(true);
        currentMember = (Member) memberService.updateMyInfo(currentMember, newUserInfo);
        System.out.println(UserPage.USER_UPDATE);
    }

    @Override
    public boolean delete() throws IOException {
        if (consoleView.checkCancel(UserPage.USER_DELETE_TITLE.toString(), UserPage.USER_NOT_DELETE.toString())) {
            return false;
        }
        memberService.deleteMyAccount(currentMember.getId());
        System.out.println(UserPage.USER_DELETE);
        return true;
    }
}
