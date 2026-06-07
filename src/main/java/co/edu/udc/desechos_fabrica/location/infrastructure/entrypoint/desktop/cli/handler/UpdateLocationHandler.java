package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.handler;

import co.edu.udc.desechos_fabrica.location.domain.exception.LocationAlreadyExistsException;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.controller.LocationController;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.io.LocationResponsePrinter;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.LocationResponse;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.UpdateLocationRequest;
import co.edu.udc.desechos_fabrica.shared.infrastructure.ConsoleIO;
import co.edu.udc.desechos_fabrica.shared.infrastructure.OperationHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateLocationHandler implements OperationHandler {

    private final LocationController locationController;
    private final ConsoleIO console;
    private final LocationResponsePrinter printer;


    @Override
    public void handle() {
        final Long id = Long.valueOf(console.readRequired           ("Location Id   : "));
        final String name = console.readRequired                    ("Name          : ");
        final String address = console.readRequired                 ("Address       : ");
        final String country = console.readRequired                 ("Country       : ");
        final String state = console.readRequired                   ("State         : ");
        final String city = console.readRequired                    ("City          : ");
        final Double latitude = Double.valueOf(console.readRequired ("Latitude      : "));
        final Double longitude = Double.valueOf(console.readRequired("Longitude     : "));

        try {
            final LocationResponse updated =
                    locationController.updateLocation(
                            new UpdateLocationRequest(
                                    id,
                                    name,
                                    address,
                                    country,
                                    state,
                                    city,
                                    latitude,
                                    longitude));
            console.println("\n  Location updated successfully.");
            printer.print(updated);
        } catch (final LocationAlreadyExistsException exception) {
            console.println("  Error: " + exception.getMessage());
        }
    }
}
