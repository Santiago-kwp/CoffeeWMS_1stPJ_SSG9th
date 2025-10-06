package view.user;

import constant.user.InputMessage;
import constant.user.LoginPage;
import constant.user.ManagerPage;
import constant.user.MemberPage;
import domain.user.User;
import exception.user.InvalidUserDataException;
import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleView {

    private final BufferedReader input;

    public ConsoleView(BufferedReader input) {
        this.input = input;
    }

    public String promptAndRead(String message) throws IOException {
        System.out.print(message);
        return input.readLine();
    }

    public User inputMemberInfo(boolean isUpdated) throws IOException, InvalidUserDataException {
        System.out.println(isUpdated ? MemberPage.MEMBER_UPDATE_TITLE : LoginPage.MEMBER_REGISTER);
        String userID = isUpdated ? null : promptAndRead(InputMessage.INPUT_ID.toString());
        String userPwd = promptAndRead(InputMessage.INPUT_PWD.toString());
        String companyName = promptAndRead(InputMessage.INPUT_COMPANY_CODE.toString());
        String phone = promptAndRead(InputMessage.INPUT_PHONE.toString());
        String email = promptAndRead(InputMessage.INPUT_EMAIL.toString());
        String companyCode = promptAndRead(InputMessage.INPUT_COMPANY_CODE.toString());
        String address = promptAndRead(InputMessage.INPUT_ADDRESS.toString());

        return (isUpdated ? User.Builder.update(userPwd, companyName) : User.Builder.create(userID, userPwd, companyName))
                .phone(phone)
                .email(email)
                .companyCode(companyCode)
                .address(address)
                .build();
    }

    public User inputManagerInfo(boolean isUpdate) throws IOException, InvalidUserDataException {
        System.out.println(isUpdate ? ManagerPage.MANAGER_UPDATE_SUBTITLE : LoginPage.MANAGER_REGISTER);
        String userID = isUpdate ? null : promptAndRead(InputMessage.INPUT_ID.toString());
        String userPwd = promptAndRead(InputMessage.INPUT_PWD.toString());
        String name = promptAndRead(InputMessage.INPUT_NAME.toString());
        String phone = promptAndRead(InputMessage.INPUT_PHONE.toString());
        String email = promptAndRead(InputMessage.INPUT_EMAIL.toString());

        return (isUpdate ? User.Builder.update(userPwd, name) : User.Builder.create(userID, userPwd, name))
                .phone(phone)
                .email(email)
                .build();
    }

    public boolean checkCancel(String message, String messageIfCancel) throws IOException {
        String yesOrNo = promptAndRead(message);
        if (!yesOrNo.equalsIgnoreCase("Y")) {
            System.out.println(messageIfCancel);
            return true;
        }
        return false;
    }
}
