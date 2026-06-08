package co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.handler;

import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.handler.OperationHandler;
import co.edu.udc.desechos_fabrica.shared.infrastructure.session.SessionManager;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserStatus;
import co.edu.udc.desechos_fabrica.user.domain.exception.PermissionDeniedException;
import co.edu.udc.desechos_fabrica.user.domain.exception.UserNotFoundException;
import co.edu.udc.desechos_fabrica.user.domain.exception.UserAlreadyExistsException;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.util.UserMenuHandler;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.controller.UserController;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto.UpdateUserRequest;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto.UserResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateUserHandler implements OperationHandler {

  private final UserController userController;
  private final ConsoleIO console;
  private final UserResponsePrinter printer;

  @Override
  public void handle() {
    try {

      if (!SessionManager.isLoggedIn()){
        throw PermissionDeniedException.becauseSessionIsInactive();
      }

      final String targetEmail = console.readRequired("Enter the email of the user to update: ");

      console.println("\nEnter the new data for the user:");
      final String newFirstName = console.readRequired              ("New first name                                 : ");
      final String newLastName = console.readRequired               ("New last name                                  : ");
      final String newEmail = console.readOptional                  ("New email (leave blank to keep current)        : ");
      final String newPassword = console.readOptional               ("New password (leave blank to keep current)     : ");
      final Long newEnterpriseId = console.readLong                 ("New enterprise ID (leave blank to keep current): ");
      
      final UserMenuHandler menuHandler = new UserMenuHandler(console);
      final UserRole newRole = menuHandler.selectRoleFromConsole();
      final UserStatus newStatus = menuHandler.selectStatusFromConsole();

      final Long enterpriseIdToSend = (newEnterpriseId == null || newEnterpriseId == 0) ? null : newEnterpriseId;

      final UpdateUserRequest request = new UpdateUserRequest(
              targetEmail,
              newFirstName,
              newLastName,
              newEmail,
              newPassword,
              newRole.name(),
              newStatus.name(),
              enterpriseIdToSend
      );

      final UserResponse updated = userController.updateUser(request);

      console.println("\n  User updated successfully.");
      printer.print(updated);

    } catch (final UserNotFoundException | PermissionDeniedException | UserAlreadyExistsException exception) {
      console.println("  Error: " + exception.getMessage());
    }
  }
}
