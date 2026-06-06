package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.controller;

import co.edu.udc.desechos_fabrica.location.application.port.in.ActivateLocationUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.in.CreateLocationUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.in.DeactivateLocationUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.in.UpdateLocationUseCase;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.*;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.mapper.LocationDesktopMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocationController {

    private final CreateLocationUseCase createLocationUseCase;
    private final UpdateLocationUseCase updateLocationUseCase;
    private final ActivateLocationUseCase activateLocationUseCase;
    private final DeactivateLocationUseCase deactivateLocationUseCase;

    public LocationResponse createLocation(final CreateLocationRequest request) {
        final var command = LocationDesktopMapper.toCreateCommand(request);
        final var location = createLocationUseCase.execute(command);
        return LocationDesktopMapper.toResponse(location);
    }

    public LocationResponse updateLocation(final UpdateLocationRequest request) {
        final var command = LocationDesktopMapper.toUpdateCommand(request);
        final var locationToUpdate = updateLocationUseCase.execute(command);
        return LocationDesktopMapper.toResponse(locationToUpdate);
    }

    public LocationResponse activateLocation(final ActivateLocationRequest request) {
        final var command = LocationDesktopMapper.toActivateCommand(request);
        final var locationToActivate = activateLocationUseCase.execute(command);
        return LocationDesktopMapper.toResponse(locationToActivate);
    }

    public LocationResponse deactivateLocation(final DeactivateLocationRequest request) {
        final var command = LocationDesktopMapper.toDeactivateCommand(request);
        final var locationToActivate = deactivateLocationUseCase.execute(command);
        return LocationDesktopMapper.toResponse(locationToActivate);
    }

}
