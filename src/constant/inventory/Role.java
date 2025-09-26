package constant.inventory;

public enum Role {
  총관리자,
  창고관리자,
  일반회원;

  public static Role fromString(String roleString) {
    if (roleString == null) return null;
    try {
      return Role.valueOf(roleString);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}