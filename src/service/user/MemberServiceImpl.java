package service.user;

import domain.user.Member;
import domain.user.User;
import model.user.MemberDAO;

public class MemberServiceImpl implements MemberService {

    private final MemberDAO dao;

    public MemberServiceImpl(MemberDAO memberDAO) {
        this.dao = memberDAO;
    }

    @Override
    public Member findMyDetails(String memberID) {
        Member member = dao.searchUserDetails(memberID);
        validCheck.checkUserFound(member);
        return member;
    }

    @Override
    public Member updateMyInfo(User original, User newUserInfo) {
        Member updatedMember = dao.updateUserInfo(original, newUserInfo);
        validCheck.checkUserUpdated(updatedMember);
        return updatedMember;
    }

    @Override
    public void deleteMyAccount(String memberID) {
        boolean ack = dao.deleteUserInfo(memberID);
        validCheck.checkUserDeleted(ack);
    }
}
