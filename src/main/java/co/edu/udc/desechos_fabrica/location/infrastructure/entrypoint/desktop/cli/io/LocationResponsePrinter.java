package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.io;

import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.LocationResponse;
import co.edu.udc.desechos_fabrica.shared.infrastructure.ConsoleIO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocationResponsePrinter {

    private static final String SEPARATOR = "-".repeat(52);
    private static final String ROW_FORMAT = "  %-10s : %s%n";

    private final ConsoleIO console;

    public void print(final LocationResponse response) {
        console.println(SEPARATOR);
        console.printf(ROW_FORMAT, "Location Name", response.name());
        console.printf(ROW_FORMAT, "Address", response.address());
        console.printf(ROW_FORMAT, "EnterpriseId", response.enterpriseId());
        console.printf(ROW_FORMAT, "Country", response.country());
        console.printf(ROW_FORMAT, "State", response.state());
        console.printf(ROW_FORMAT, "City", response.city());
        console.printf(ROW_FORMAT, "Latitude", response.latitude());
        console.printf(ROW_FORMAT, "Longitude", response.longitude());
        console.printf(ROW_FORMAT, "Status", response.status());
        console.println(SEPARATOR);
    }

    public void printList(final List<LocationResponse> locations) {
        if (locations.isEmpty()) {
            console.println("  No locations found.");
            return;
        }
        console.printf("%n  Total: %d location(s)%n", locations.size());
        locations.forEach(this::print);
    }
}
