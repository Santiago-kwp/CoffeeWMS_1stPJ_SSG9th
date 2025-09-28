package constant.user.validation;

import constant.user.InputMessage;
import domain.user.User;
import exception.user.InvalidUserDataException;

public class InputValidCheck {

    private static final String ID_FORMAT = "^[a-zA-Z0-9]{8,15}$";
    private static final String PASSWORD_FORMAT = "^[^a-zA-Z0-9\\s]{7,20}$";
    private static final String PHONE_FORMAT = "^\\d{3}-\\d{3,4}-\\d{4}$";
    private static final String COMPANY_CODE_FORMAT = "^\\d{3}-\\d{2}-\\d{5}$";
    private static final String EMAIL_FORMAT = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public void checkUserID(String userID) {
        if (!userID.matches(ID_FORMAT)) {
            throw new InvalidUserDataException(InputMessage.INVALID_ID.toString());
        }
    }

    public void checkPwd(String pwd) {
        if (!pwd.matches(PASSWORD_FORMAT)) {
            throw new InvalidUserDataException(InputMessage.INVALID_PASSWORD.toString());
        }
    }

    public void checkPhoneFormat(String phone) {
        if (!phone.matches(PHONE_FORMAT)) {
            throw new InvalidUserDataException(InputMessage.INVALID_PHONE_FORMAT.toString());
        }
    }

    public void checkCompanyCode(String companyCode) {
        if (!companyCode.matches(COMPANY_CODE_FORMAT)) {
            throw new InvalidUserDataException(InputMessage.INVALID_COMPANY_CODE_FORMAT.toString());
        }
    }

    public void checkEmailFormat(String email) {
        if (!email.matches(EMAIL_FORMAT)) {
            throw new InvalidUserDataException(InputMessage.INVALID_EMAIL_FORMAT.toString());
        }
    }

    public void checkMemberData(User user, boolean updateOption) {
        if (!updateOption) {
            checkUserID(user.getId());
        }
        checkPwd(user.getPwd());
        checkPhoneFormat(user.getPhone());
        checkEmailFormat(user.getEmail());
        checkCompanyCode(user.getCompanyCode());
    }

    public void checkManagerData(User user, boolean updateOption) {
        if (!updateOption) {
            checkUserID(user.getId());
        }
        checkPwd(user.getPwd());
        checkPhoneFormat(user.getPhone());
        checkEmailFormat(user.getEmail());
    }
}
