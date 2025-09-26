package constant.user;

import domain.user.Manager;
import domain.user.Member;
import domain.user.User;

public enum UserPage {

    // 일반회원, 관리자 공통 기능
    MEMBER_MEMBER_MENU_TITLE("""
            ------------------<< 일반회원 회원관리 >>------------------
            1.내 정보 조회 | 2.내 정보 수정 | 3.탈퇴 | 4.뒤로가기
            ---------------------------------------------------------
            메뉴를 선택해주세요.
            """),
    CURRENT_USER_SELECT("현재 회원정보를 조회합니다."),

    MEMBER_UPDATE_TITLE("--------------------<< 일반회원 회원정보 수정 >>--------------------------"),
    MEMBER_UPDATE("현재 회원정보의 변경이 완료되었습니다."),
    MEMBER_UPDATE_FAILED("현재 회원정보의 변경에 실패했습니다."),

    USER_DELETE_TITLE("""
            --------------------<< 회원탈퇴 >>--------------------------
            회원탈퇴를 진행하시겠습니까?(Y 입력 시 진행)
            """),
    USER_DELETE("회원 탈퇴가 완료되었습니다. 로그인 메뉴로 되돌아갑니다."),
    USER_NOT_DELETE("회원탈퇴를 진행하지 않습니다. 회원관리 메뉴로 되돌아갑니다."),
    USER_DELETE_FAILED("회원탈퇴를 진행할 수 없습니다. 작업을 중단합니다."),


    // 관리자 전용 회원관리 기능
    MANAGER_MEMBER_MENU_TITLE("""
            -------------------<< 관리자 회원관리 >>-------------------
            1.회원 조회 | 2.회원 수정 | 3.회원 삭제 | 4.뒤로가기
            ---------------------------------------------------------
            메뉴를 선택해주세요.
            """),
    MANAGER_SELECT_TITLE("""
            -------------------<< 관리자 전용 조회 >>------------------
            1.회원상세보기 | 2.전체회원조회 | 3.권한별 회원목록 | 4.뒤로가기
            ---------------------------------------------------------
            메뉴를 선택해주세요.
            """),
    MANAGER_DETAIL_INFO_TITLE("""
            -------------------<< 회원상세보기 >>-----------------------
            1.내 정보 조회 | 2.다른 회원 조회 | 3.뒤로가기
            ----------------------------------------------------------
            메뉴를 선택해주세요.
            """),
    INPUT_ID_FOR_SEARCH("""
            -------------------<< 다른 회원정보 조회 >>------------------
            조회할 회원의 아이디를 입력해주세요.
            """),
    MANAGER_DETAIL("""
            ---------------<< 관리자 정보 상세조회 >>--------------------
            아이디: %1$s
            이름: %2$s
            연락처: %3$s
            이메일: %4$s
            입사일: %5$s
            직급: %6$s
            """),
    MEMBER_DETAIL("""
            ----------------<< 일반 회원정보 상세조회 >>-----------------
            아이디: %1$s
            소속사: %2$s
            연락처: %3$s
            이메일: %4$s
            사업자번호: %5$s
            주소: %6$s
            계약시작일: %7$s
            계약종료일: %8$s
            """),

    MANAGER_SEARCH_ALL("""
            -----------------------------------<< 전체회원조회 >>-----------------------------------
            """),
    SEARCHED_COMMON_INFO("%-16s\t%-21s\t%-8s\t%-14s\t%-31s\t%-5s\n"),
    MANAGER_SEARCH_BY_ROLE_TITLE("""
            -------------------<< 권한별 회원조회 >>--------------------
            1.일반회원 | 2.관리자
            ----------------------------------------------------------
            특정 권한을 보유한 회원 목록을 조회할 수 있습니다.
            어떤 권한을 보유한 회원을 조회할 것인지 선택해주세요.(1, 2 중 택1)
            """),
    MANAGER_SEARCH_MEMBERS(""),
    MANAGER_UPDATE_TITLE("""
            -------------------<< 관리자 전용 수정 >>------------------
            1.내 정보 수정 | 2.관리자 권한 수정 | 3.뒤로가기
            ---------------------------------------------------------
            메뉴를 선택해주세요.
            """),
    MANAGER_UPDATE_SUBTITLE("--------------------<< 관리자 회원정보 수정 >>--------------------------"),
    INPUT_ID_FOR_UPDATE_ROLE("권한을 변경할 일반회원의 아이디를 입력해주세요."),

    MANAGER_DELETE_TITLE("""
            -------------------<< 관리자 전용 삭제 >>--------------------
            1.회원 탈퇴 | 2.관리자 권한 삭제 | 3.뒤로가기
            -----------------------------------------------------------
            메뉴를 선택해주세요.
            """),
    INPUT_ID_FOR_DELETE_INFO("탈퇴시킬 회원의 아이디를 입력해주세요."),
    INPUT_ID_FOR_DELETE_ROLE("권한을 삭제할 일반회원 및 관리자의 아이디를 입력해주세요."),

    NOT_HAVE_PERMISSION("권한이 낮아 해당 작업을 수행할 수 없습니다."),
    CHIEF_MANAGER_CANNOT_DELETE("총관리자는 삭제할 수 없습니다."),
    USER_MENU_PREVIOUS("이전 메뉴로 돌아갑니다.");

    private final String page;

    UserPage(String page) {
        this.page = page;
    }

    public static void memberDetails(Member member) {
        System.out.printf(MEMBER_DETAIL.toString(),
                member.getId(), member.getName(),
                member.getPhone(), member.getEmail(), member.getCompanyCode(),
                member.getAddress(), member.getStart_date(), member.getExpired_date());
    }

    public static void managerDetails(Manager manager) {
        System.out.printf(MANAGER_DETAIL.toString(),
                manager.getId(), manager.getPwd(), manager.getName(),
                manager.getPhone(), manager.getEmail(), manager.getHireDate(), manager.getType());
    }

    public static void userCommonInfoTitle() {
        System.out.printf(SEARCHED_COMMON_INFO.toString(), "아이디", "비밀번호", "소속사/이름", "연락처", "이메일", "회원유형");
    }
    public static void userCommonInfo(User user) {
        System.out.printf(SEARCHED_COMMON_INFO.toString(), user.getId(), user.getPwd(), user.getName(), user.getPhone(), user.getEmail(), user.getType());
    }

    @Override
    public String toString() {
        return page;
    }
}
