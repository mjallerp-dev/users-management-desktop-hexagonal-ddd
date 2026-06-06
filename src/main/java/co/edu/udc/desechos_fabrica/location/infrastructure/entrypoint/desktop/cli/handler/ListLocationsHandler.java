package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.handler;

import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.controller.LocationController;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.io.LocationResponsePrinter;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.LocationResponse;
import co.edu.udc.desechos_fabrica.shared.infrastructure.OperationHandler;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListLocationsHandler implements OperationHandler {

    private final LocationController locationController;
    private final LocationResponsePrinter printer;

    @Override
    public void handle(){
        final List<LocationResponse> locations = locationController.listAllLocations();
        printer.printList(locations);
    }
}
