package co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli;

import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.handler.CreateUserHandler;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.handler.DeleteUserHandler;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.handler.FindUserByEmailHandler;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.handler.ListUsersHandler;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.handler.LoginHandler;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.handler.OperationHandler;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.handler.UpdateUserHandler;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.menu.UserMenuOption;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.controller.UserController;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UserManagementCli {

  private static final String BANNER =
      """
      ===========================================
        EcoResidues - Residue Management System
      ===========================================""";

  private static final String MENU_BORDER = "  ===========================================";

  private final UserController userController;
  private final ConsoleIO console;

  public void start() {
    console.println(BANNER);
    final UserResponsePrinter printer = new UserResponsePrinter(console);
    runLoop(buildHandlers(printer));
  }

  private void runLoop(final Map<UserMenuOption, OperationHandler> handlers) {
    boolean running = true;
    while (running) {
      printMenu();
      final int choice = console.readInt("\n  Option: ");
      final Optional<UserMenuOption> option = UserMenuOption.fromNumber(choice);

      if (option.isEmpty()) {
        console.println("  Invalid option. Please try again.");
      } else if (option.get() == UserMenuOption.EXIT) {
        console.println("\n  Goodbye!\n");
        running = false;
      } else {
        executeHandler(handlers, option.get());
      }
    }
  }

  private void executeHandler(
          final Map<UserMenuOption, OperationHandler> handlers, final UserMenuOption option) {
    try {
      handlers.get(option).handle();
    } catch (final ConstraintViolationException exception) {
      console.println("  Validation errors:");
      exception.getConstraintViolations()
          .forEach(violation -> console.println("    - " + violation.getMessage()));
    } catch (final RuntimeException exception) {
      console.println("  Unexpected error: " + exception.getMessage());
    }
  }

  private Map<UserMenuOption, OperationHandler> buildHandlers(final UserResponsePrinter printer) {
    return Map.of(
        UserMenuOption.LIST_USERS,  new ListUsersHandler(userController, printer),
        UserMenuOption.FIND_USER,   new FindUserByEmailHandler(userController, console, printer),
        UserMenuOption.CREATE_USER, new CreateUserHandler(userController, console, printer),
        UserMenuOption.UPDATE_USER, new UpdateUserHandler(userController, console, printer),
        UserMenuOption.DELETE_USER, new DeleteUserHandler(userController, console),
        UserMenuOption.LOGIN,       new LoginHandler(userController, console, printer));
  }

  private void printMenu() {
    console.println();
    console.println(MENU_BORDER);
    console.println("    Main Menu");
    console.println(MENU_BORDER);
    for (final UserMenuOption option : UserMenuOption.values()) {
      console.printf("    [%d] %s%n", option.getNumber(), option.getDescription());
    }
    console.println(MENU_BORDER);
  }
}
