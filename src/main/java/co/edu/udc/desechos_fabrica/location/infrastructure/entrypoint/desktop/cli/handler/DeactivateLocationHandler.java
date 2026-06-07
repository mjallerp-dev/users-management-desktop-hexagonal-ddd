package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.handler;

import co.edu.udc.desechos_fabrica.location.domain.exception.LocationAlreadyExistsException;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.controller.LocationController;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.io.LocationResponsePrinter;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.DeactivateLocationRequest;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.LocationResponse;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.handler.OperationHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeactivateLocationHandler implements OperationHandler {

    private final LocationController locationController;
    private final ConsoleIO console;
    private final LocationResponsePrinter printer;

    @Override
    public void handle(){
        final Long id = Long.valueOf(console.readRequired("Location Id   : "));
                try {
                    final LocationResponse deactivated =
                            locationController.deactivateLocation(
                                    new DeactivateLocationRequest(id));
                    console.println("\n  Location deactivated successfully.");
                    printer.print(deactivated);
                } catch (final LocationAlreadyExistsException exception) {
                    console.println("  Error: " + exception.getMessage());
                }
    }
}
