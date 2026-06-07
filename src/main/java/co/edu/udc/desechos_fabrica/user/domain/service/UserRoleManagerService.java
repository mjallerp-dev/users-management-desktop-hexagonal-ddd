package co.edu.udc.desechos_fabrica.user.domain.service;

import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserStatus;
import co.edu.udc.desechos_fabrica.user.domain.exception.PermissionDeniedException;
import co.edu.udc.desechos_fabrica.user.domain.model.UserModel;
import java.util.Objects;

public class UserRoleManagerService implements UserRoleManager {

    @Override
    public void checkUpdatePermissions(UserModel actor, UserModel targetUser, UserRole newRole) {

        validateActorStatus(actor.getStatus());
        validateAccessToTarget(actor, targetUser);
            if (actor.getRole() != UserRole.ADMIN && newRole.getLevel() >= actor.getRole().getLevel()) {
                throw PermissionDeniedException.becauseHaveNotUpdatePermission();
            }
    }

    @Override
    public void checkDeletePermissions(UserModel actor, UserModel targetUser) {
        validateAccessToTarget(actor, targetUser);
    }

    private void validateAccessToTarget(UserModel actor, UserModel targetUser) {

        validateActorStatus(actor.getStatus());
        if (actor.getRole() == UserRole.ADMIN) {
            return;
        }

        if (actor.getRole().getLevel() <= targetUser.getRole().getLevel()) {
            throw PermissionDeniedException.becauseUserIsHigher();
        }

        if (actor.getRole() == UserRole.ENTERPRISE_ADMIN) {
            if (!Objects.equals(actor.getEnterpriseId(), targetUser.getEnterpriseId())) {
                throw PermissionDeniedException.becauseUserIsHigher();
            }
        }
    }

    private void validateActorStatus(UserStatus status) {
        switch (status) {
            case INACTIVE -> throw PermissionDeniedException.becauseUserIsInactive();
            case PENDING  -> throw PermissionDeniedException.becauseUserIsPending();
            case BLOCKED  -> throw PermissionDeniedException.becauseUserIdBlocked();
            case ACTIVE   -> {}
            default       -> throw new PermissionDeniedException("Invalid user status");
        }
    }
}
