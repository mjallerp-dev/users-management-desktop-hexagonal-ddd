package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum LocationMenuOption {

    LIST_LOCATIONS(1, "List all Locations"),
    FIND_LOCATION(2, "Find Location by Id"),
    CREATE_LOCATION(3, "Create Location"),
    UPDATE_LOCATION(4, "Update Location"),
    ACTIVATE_LOCATION(5, "Activate Location"),
    DEACTIVATE_LOCATION(6, "Deactivate Location"),
    EXIT(0, "Back to Main Menu");

    private final int number;
    private final String description;

    public static Optional<LocationMenuOption> fromNumber(final int number) {
        for (final LocationMenuOption option : values()) {
            if (option.number == number) {
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }
}
