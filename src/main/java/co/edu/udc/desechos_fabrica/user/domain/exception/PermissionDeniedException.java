package co.edu.udc.desechos_fabrica.user.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class PermissionDeniedException extends DomainException {

    private static final String MESSAGE_PERMISSION_UPDATED = "You cannot assign a role that is equal to or higher than your own.";
    private static final String MESSAGE_PERMISSION_DELETED = "You cannot delete the user. It will remain unchanged.";
    private static final String MESSAGE_INACTIVE_ACCOUNT = "Your account is inactive.";
    private static final String MESSAGE_PENDING_ACCOUNT = "Your account is pending approval. You cannot perform this action until your account is active.";
    private static final String MESSAGE_BLOCKED_ACCOUNT = "Your account is blocked.";
    private static final String MESSAGE_PERMISSION_MODIFY = "You can not modify this user.";
    private static final String MESSAGE_INACTIVE_SESSION = "No active session found";

    public PermissionDeniedException(String message) {
        super(message);
    }

    public static PermissionDeniedException becauseHaveNotUpdatePermission() {
        return new PermissionDeniedException(MESSAGE_PERMISSION_UPDATED);
    }

    public static PermissionDeniedException becauseHaveNotDeletePermission(final String email) {
        return new PermissionDeniedException(MESSAGE_PERMISSION_DELETED);
    }

    public static PermissionDeniedException becauseUserIsInactive() {
        return new PermissionDeniedException(MESSAGE_INACTIVE_ACCOUNT);
    }

    public static PermissionDeniedException becauseUserIsPending() {
        return new PermissionDeniedException(MESSAGE_PENDING_ACCOUNT);
    }

    public static PermissionDeniedException becauseUserIdBlocked() {
        return new PermissionDeniedException(MESSAGE_BLOCKED_ACCOUNT);
    }

    public static PermissionDeniedException becauseUserIsHigher() {
        return new PermissionDeniedException(MESSAGE_PERMISSION_MODIFY);
    }

    public static PermissionDeniedException becauseSessionIsInactive() {
        return new PermissionDeniedException(MESSAGE_INACTIVE_SESSION);
    }
}
