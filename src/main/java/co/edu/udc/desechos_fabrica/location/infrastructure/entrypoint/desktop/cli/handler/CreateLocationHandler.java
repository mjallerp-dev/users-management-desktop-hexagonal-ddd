package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.handler;

import co.edu.udc.desechos_fabrica.location.domain.exception.LocationAlreadyExistsException;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.controller.LocationController;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.io.LocationResponsePrinter;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.CreateLocationRequest;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.LocationResponse;
import co.edu.udc.desechos_fabrica.shared.infrastructure.OperationHandler;
import co.edu.udc.desechos_fabrica.shared.infrastructure.ConsoleIO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateLocationHandler implements OperationHandler {

    private final LocationController locationController;
    private final ConsoleIO console;
    private final LocationResponsePrinter printer;


    @Override
    public void handle() {
        final String name = console.readRequired                    ("Name          : ");
        final String address = console.readRequired                 ("Address       : ");
        final Long enterpriseId = Long.valueOf(console.readRequired ("EnterpriseId  : "));
        final String country = console.readRequired                 ("Country       : ");
        final String state = console.readRequired                   ("State         : ");
        final String city = console.readRequired                    ("City          : ");
        final Double latitude = Double.valueOf(console.readRequired ("Latitude      : "));
        final Double longitude = Double.valueOf(console.readRequired("Longitude     : "));

        try {
            final LocationResponse created =
                locationController.createLocation(
                    new CreateLocationRequest(
                            name,
                            address,
                            enterpriseId,
                            country,
                            state,
                            city,
                            latitude,
                            longitude));
            console.println("\n  Location created successfully.");
            printer.print(created);
        } catch (final LocationAlreadyExistsException exception) {
            console.println("  Error: " + exception.getMessage());
        }
    }
}
