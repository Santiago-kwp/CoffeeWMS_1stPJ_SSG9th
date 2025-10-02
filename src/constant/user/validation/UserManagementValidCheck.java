package constant.user.validation;

import constant.user.ManagerPage;
import constant.user.UserPage;
import domain.user.User;
import exception.user.*;

import java.util.List;

public class UserManagementValidCheck extends MenuNumberValidCheck {

    public void checkUserType(String userType) {
        if (userType == null) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }
    }

    public void checkUserFound(User foundUser) {
        if (foundUser == null) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }
    }
    public void checkUserFound(List<User> users) {
        if (users.isEmpty()) {
            throw new UnableToReadUserException(UserPage.CANNOT_SEARCH_USER.toString());
        }
    }

    public void checkPermission(boolean notHavePermission) {
        if (notHavePermission) {
            throw new NotAllowedUserException(ManagerPage.NOT_HAVE_PERMISSION.toString());
        }
    }

    public void checkPermission(String position, String allowedPosition, boolean deleteOption) {
        switch (allowedPosition) {
            case "총관리자":
                if (!position.equals(allowedPosition)) {
                    throw new NotAllowedUserException(ManagerPage.NOT_HAVE_PERMISSION.toString());
                }
            case "창고관리자":
                if (!position.equals(allowedPosition) && deleteOption) {
                    throw new NotAllowedUserException(ManagerPage.CHIEF_MANAGER_CANNOT_DELETE.toString());
                }
        }
    }

    public void checkUserUpdated(boolean isUserUpdated) {
        if (!isUserUpdated) {
            throw new FailedToUserUpdateException(UserPage.USER_UPDATE_FAILED.toString());
        }
    }

    public void checkUserApproved(boolean isUserApproved, boolean restoreOption) {
        if (!isUserApproved) {
            if (!restoreOption) {
                throw new FailedToApproveUserException(ManagerPage.APPROVE_FAILED.toString());
            } else {
                throw new FailedToUserRestoreException(ManagerPage.RESTORE_FAILED.toString());
            }
        }
    }

    public void checkUserDeleted(boolean isUserDeleted) {
        if (!isUserDeleted) {
            throw new UserDeleteFailedException(UserPage.USER_DELETE_FAILED.toString());
        }
    }

    public void checkRoleExist(String userRole) {
        if (userRole == null) {
            throw new FailedToUserRoleUpdateException(ManagerPage.ALREADY_DELETED_OR_NOT_EXIST.toString());
        }
    }

    public void checkRoleUpdated(boolean isRoleUpdated) {
        if (!isRoleUpdated) {
            throw new FailedToUserRoleUpdateException(ManagerPage.ROLE_UPDATE_FAILED.toString());
        }
    }

    public void checkCargoAdded(boolean isAdded) {
        if (!isAdded) {
            throw new CargoAddToManagerFailedException(ManagerPage.CARGO_ADD_FAILED.toString());
        }
    }

    public void checkRoleDeleted(boolean isRoleDeleted) {
        if (!isRoleDeleted) {
            throw new FailedToDeleteUserRoleException(ManagerPage.ROLE_DELETE_FAILED.toString());
        }
    }

    public void checkRoleDeleted(String userRole) {
        if (userRole != null) {
            throw new FailedToDeleteUserRoleException(ManagerPage.ALREADY_HAVE_ROLE.toString());
        }
    }

    public void checkDeleteRoleAvailable(String userRole) {
        if (userRole == null) {
            throw new FailedToDeleteUserRoleException(ManagerPage.ALREADY_DELETED_OR_NOT_EXIST.toString());
        }
        if (userRole.equals("총관리자")) {
            throw new NotAllowedUserException(ManagerPage.CHIEF_MANAGER_CANNOT_DELETE.toString());
        }
    }
}
