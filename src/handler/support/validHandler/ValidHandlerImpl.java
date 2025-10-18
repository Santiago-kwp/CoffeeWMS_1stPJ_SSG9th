package handler.support.validHandler;

import constant.support.ValidCheck;
import domain.user.Manager;
import exception.support.InputException;

public class ValidHandlerImpl implements ValidHandler {

    public void managerCheck (Manager manager) {
            try {
                ValidCheck.managerCheck(manager);
            } catch (InputException e) {
                System.out.println(e.getMessage());
        }
    }
}
