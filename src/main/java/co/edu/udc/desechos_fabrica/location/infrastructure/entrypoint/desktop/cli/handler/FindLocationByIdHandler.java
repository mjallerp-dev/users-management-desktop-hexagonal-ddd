package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.handler;

import co.edu.udc.desechos_fabrica.location.domain.exception.LocationNotFoundException;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.controller.LocationController;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.io.LocationResponsePrinter;
import co.edu.udc.desechos_fabrica.shared.infrastructure.ConsoleIO;
import co.edu.udc.desechos_fabrica.shared.infrastructure.OperationHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindLocationByIdHandler implements OperationHandler {

    private final LocationController locationController;
    private final ConsoleIO console;
    private final LocationResponsePrinter locationResponsePrinter;

    @Override
    public void handle() {
        try {final Long id = Long.valueOf(console.readRequired("Location Id   : "));
            final var location = locationController.getLocationById(id);
            locationResponsePrinter.print(location);
        } catch (LocationNotFoundException exception) {
            console.println("  Error: " + exception.getMessage());
        } catch (NumberFormatException e) {
            console.println("  Error: please enter a valid number");
        }
    }
}
