package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.util;

import co.edu.udc.desechos_fabrica.location.domain.enums.LocationStatus;
import co.edu.udc.desechos_fabrica.shared.infrastructure.ConsoleIO;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class LocationMenuHandler {

    private final ConsoleIO console;

    public LocationStatus selectStatusFromConsole() {
        while (true) {
            console.println("\nPlease select a new status:");
            console.println("1. ACTIVE");
            console.println("2. INACTIVE");
            console.println("3. PENDING");
            console.println("4. BLOCKED");

            final int choice = console.readInt("Enter the status number: ");
            final Optional<LocationStatus> statusOptional = getStatusByNumber(choice);

            if (statusOptional.isPresent()) {
                return statusOptional.get();
            } else {
                console.println("\n  Error: Invalid selection. Please try again.");
            }
        }
    }

    private Optional<LocationStatus> getStatusByNumber(final int number) {
        LocationStatus[] status = LocationStatus.values();
        if (number > 0 && number <= status.length) {
            return Optional.of(status[number - 1]);
        }
        return Optional.empty();
    }
}
