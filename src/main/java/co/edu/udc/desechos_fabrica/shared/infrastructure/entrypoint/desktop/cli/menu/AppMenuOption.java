package co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.menu;

import co.edu.udc.desechos_fabrica.shared.infrastructure.session.SessionManager;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppMenuOption implements MenuOption {

    LOGIN(1, "Login"),
    CREATE_USER(2, "Create User"),
    MANAGE_USERS(3, "Manage Users"),
    MANAGE_LOCATIONS(4, "Manage Locations"),
    LOGOUT(5, "Logout"),
    EXIT(0, "Exit Application");

    private final int number;
    private final String description;

    public static Optional<AppMenuOption> fromNumber(final int number) {
        for (final AppMenuOption option : values()) {
            if (option.number == number) {
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isVisible() {
        boolean loggedIn = SessionManager.isLoggedIn();

        return switch (this) {
            case LOGIN, CREATE_USER -> !loggedIn;
            case MANAGE_USERS, MANAGE_LOCATIONS, LOGOUT -> loggedIn;
            case EXIT -> true;
        };
    }
}