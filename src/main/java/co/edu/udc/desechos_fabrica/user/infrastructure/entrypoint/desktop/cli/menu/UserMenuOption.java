package co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.menu;

import java.util.Optional;

import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.menu.MenuOption;
import co.edu.udc.desechos_fabrica.shared.infrastructure.session.SessionManager;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto.UserResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserMenuOption implements MenuOption {

  LIST_USERS(1, "List all users"),
  FIND_USER(2, "Find user by Email"),
  CREATE_USER(3, "Create user"),
  UPDATE_USER(4, "Update user"),
  DELETE_USER(5, "Delete user"),
  BACK(0, "Back to Main Menu");

  private final int number;
  private final String description;

  public static Optional<UserMenuOption> fromNumber(final int number) {
    for (final UserMenuOption option : values()) {
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
      case LIST_USERS, FIND_USER, UPDATE_USER, DELETE_USER, BACK -> loggedIn;
      case CREATE_USER -> loggedIn && "ADMIN".equals(SessionManager.getCurrentUser().map(UserResponse::role).orElse(""));
    };
  }
}

