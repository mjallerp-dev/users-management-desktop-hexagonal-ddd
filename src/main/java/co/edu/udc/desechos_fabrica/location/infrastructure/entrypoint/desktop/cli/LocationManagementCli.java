package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli;

import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.io.LocationResponsePrinter;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.controller.LocationController;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.LocationResponse;
import co.edu.udc.desechos_fabrica.shared.infrastructure.ConsoleIO;
import lombok.RequiredArgsConstructor;

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

}
