package co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli;

import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.LocationManagementCli;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.handler.OperationHandler;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.menu.AppMenuOption;
import co.edu.udc.desechos_fabrica.shared.infrastructure.session.SessionManager;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.handler.CreateUserHandler;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.handler.LoginHandler;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.controller.UserController;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ManagementCli {

    private static final String BANNER =
            """
            ===========================================
              EcoResidues - Residue Management System
            ===========================================""";

    private static final String MENU_BORDER = "  ===========================================";

    private static final String MENU_BANNER =
            """
                             Main Menu
            ===========================================""";

    private final UserController userController;
    private final UserManagementCli userManagementCli;
    private final LocationManagementCli locationManagementCli;
    private final ConsoleIO console;

    public void start() {
        console.println(BANNER);
        final UserResponsePrinter printer = new UserResponsePrinter(console);
        runLoop(buildHandlers(printer));
    }

    private void runLoop(final Map<AppMenuOption, OperationHandler> handlers) {
        boolean running = true;
        while (running) {
            printMenu();
            final int choice = console.readInt("\n  Option: ");
            final Optional<AppMenuOption> option = AppMenuOption.fromNumber(choice);

            if (option.isEmpty()) {
                console.println("  Invalid option. Please try again.");
            } else if (option.get() == AppMenuOption.EXIT) {
                console.println("\n  Closing Application. Goodbye\n");
                running = false;
            } else {
                executeHandler(handlers, option.get());
            }
        }
    }

    private void executeHandler(final Map<AppMenuOption, OperationHandler> handlers, final AppMenuOption option) {
        try {
            if (handlers.containsKey(option)) {
                handlers.get(option).handle();
            } else {
                if (option == AppMenuOption.LOGOUT) {
                    SessionManager.logout();
                    console.println("\n  Session closed successfully.");
                }
            }
        } catch (final RuntimeException exception) {
            console.println("\n  Validation Error: " + exception.getMessage());
        }
    }

    private Map<AppMenuOption, OperationHandler> buildHandlers(final UserResponsePrinter printer) {
        return Map.of(
                AppMenuOption.LOGIN,            new LoginHandler(userController, console, printer),
                AppMenuOption.CREATE_USER,      new CreateUserHandler(userController, console, printer),
                AppMenuOption.MANAGE_USERS,     userManagementCli::start,
                AppMenuOption.MANAGE_LOCATIONS, locationManagementCli::start
        );
    }

    private void printMenu() {
        console.println();
        console.println(MENU_BANNER);
        for (final AppMenuOption option : AppMenuOption.values()) {
            if (option.isVisible()) {
                console.printf("    [%d] %s%n", option.getNumber(), option.getDescription());
            }
        }
        console.println(MENU_BORDER);
    }
}