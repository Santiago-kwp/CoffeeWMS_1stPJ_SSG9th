package view.user;

import constant.user.InputMessage;
import constant.user.LoginPage;
import constant.user.ManagerPage;
import constant.user.MemberPage;
import domain.user.User;
import exception.user.InvalidUserDataException;
import java.io.BufferedReader;
import java.io.IOException;

public class InputView {

    private final BufferedReader input;

    public InputView(BufferedReader input) {
        this.input = input;
    }

    public String promptAndRead(String message) throws IOException {
        System.out.print(message);
        return input.readLine();
    }

    public User inputMemberInfo() throws IOException, InvalidUserDataException {
        LoginPage.print(LoginPage.MEMBER_REGISTER);

        String userID = promptAndRead(InputMessage.INPUT_ID.toString());
        String userPwd = promptAndRead(InputMessage.INPUT_PWD.toString());
        String companyName = promptAndRead(InputMessage.INPUT_COMPANY_NAME.toString());
        String phone = promptAndRead(InputMessage.INPUT_PHONE.toString());
        String email = promptAndRead(InputMessage.INPUT_EMAIL.toString());
        String companyCode = promptAndRead(InputMessage.INPUT_COMPANY_CODE.toString());
        String address = promptAndRead(InputMessage.INPUT_ADDRESS.toString());

        return User.Builder.create(userID, userPwd, companyName)
                .phone(phone)
                .email(email)
                .registerType("일반회원")
                .companyCode(companyCode)
                .address(address)
                .build();
    }

    public User inputManagerInfo() throws IOException, InvalidUserDataException {
        LoginPage.print(LoginPage.MANAGER_REGISTER);
        String userID = promptAndRead(InputMessage.INPUT_ID.toString());
        String userPwd = promptAndRead(InputMessage.INPUT_PWD.toString());
        String name = promptAndRead(InputMessage.INPUT_NAME.toString());
        String phone = promptAndRead(InputMessage.INPUT_PHONE.toString());
        String email = promptAndRead(InputMessage.INPUT_EMAIL.toString());

        String option = promptAndRead(LoginPage.INPUT_MANAGER_POSITION.toString());
        String position = null;
        switch (option) {
            case "1" -> position = "창고관리자";
            case "2" -> position = "총관리자";
        }

        return User.Builder.create(userID, userPwd, name)
                .phone(phone)
                .email(email)
                .registerType(position)
                .build();
    }

    public User inputNewMemberInfo() throws IOException, InvalidUserDataException {
        System.out.println(MemberPage.MEMBER_UPDATE_TITLE);

        String userPwd = promptAndRead(InputMessage.INPUT_PWD.toString());
        String companyName = promptAndRead(InputMessage.INPUT_COMPANY_CODE.toString());
        String phone = promptAndRead(InputMessage.INPUT_PHONE.toString());
        String email = promptAndRead(InputMessage.INPUT_EMAIL.toString());
        String companyCode = promptAndRead(InputMessage.INPUT_COMPANY_CODE.toString());
        String address = promptAndRead(InputMessage.INPUT_ADDRESS.toString());

        return User.Builder.update(userPwd, companyName)
                .phone(phone)
                .email(email)
                .companyCode(companyCode)
                .address(address)
                .build();
    }

    public User inputNewManagerInfo() throws IOException, InvalidUserDataException {
        System.out.println(ManagerPage.MANAGER_UPDATE_SUBTITLE);

        String userPwd = promptAndRead(InputMessage.INPUT_PWD.toString());
        String name = promptAndRead(InputMessage.INPUT_NAME.toString());
        String phone = promptAndRead(InputMessage.INPUT_PHONE.toString());
        String email = promptAndRead(InputMessage.INPUT_EMAIL.toString());

        return User.Builder.update(userPwd, name)
                .phone(phone)
                .email(email)
                .build();
    }
}
