package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli;

import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.handler.*;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.io.LocationResponsePrinter;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.menu.LocationMenuOption;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.controller.LocationController;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.handler.OperationHandler;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class LocationManagementCli {

    private static final String BANNER =
        """
        ===========================================
             EcoResidues - Location Management
        ===========================================""";

    private static final String MENU_BORDER = "  ===========================================";

    private final LocationController locationController;
    private final ConsoleIO console;

    public void start(){
        console.println(BANNER);
        final LocationResponsePrinter printer = new LocationResponsePrinter(console);
    }

    private void runLoop(final Map<LocationMenuOption, OperationHandler> handlers) {
        boolean running = true;
        while (running) {
            printMenu();
            final int choice = console.readInt("\n  Option: ");
            final Optional<LocationMenuOption> option = LocationMenuOption.fromNumber(choice);

            if (option.isEmpty()) {
                console.println("  Invalid option. Please try again.");
            } else if (option.get() == LocationMenuOption.BACK) {
                running = false;
            } else {
                executeHandler(handlers, option.get());
            }
        }
    }

    private void executeHandler(
    final Map<LocationMenuOption, OperationHandler> handlers, final LocationMenuOption option) {
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

    private Map<LocationMenuOption, OperationHandler> buildHandlers(final LocationResponsePrinter printer) {
        return Map.of(
            LocationMenuOption.LIST_LOCATIONS,  new ListLocationsHandler(locationController, printer),
            LocationMenuOption.FIND_LOCATION,   new FindLocationByIdHandler(locationController, console, printer),
            LocationMenuOption.CREATE_LOCATION, new CreateLocationHandler(locationController, console, printer),
            LocationMenuOption.UPDATE_LOCATION, new UpdateLocationHandler(locationController, console, printer),
            LocationMenuOption.ACTIVATE_LOCATION, new ActivateLocationHandler(locationController, console, printer),
            LocationMenuOption.DEACTIVATE_LOCATION, new DeactivateLocationHandler(locationController, console, printer));
    }

    private void printMenu(){
        console.println();
        console.println(MENU_BORDER);
        console.println("     Location Menu");
        console.println(MENU_BORDER);
        for (final LocationMenuOption option : LocationMenuOption.values()) {
            console.printf("    [%d] %s%n", option.getNumber(), option.getDescription());
        }
        console.println(MENU_BORDER);
    }

}
