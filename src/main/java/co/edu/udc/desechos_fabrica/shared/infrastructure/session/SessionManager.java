package co.edu.udc.desechos_fabrica.shared.infrastructure.session;

import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto.UserResponse;
import java.util.Optional;

public class SessionManager {

    private static UserResponse currentUser;

    private SessionManager() {}

    public static void login(UserResponse user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static Optional<UserResponse> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static Long getCurrentEnterpriseId() {
        return isLoggedIn() ? currentUser.enterpriseId() : null;
    }
}
