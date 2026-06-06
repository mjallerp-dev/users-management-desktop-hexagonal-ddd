package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.controller;

import co.edu.udc.desechos_fabrica.location.application.port.in.*;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.*;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.mapper.LocationDesktopMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocationController {

    private final CreateLocationUseCase createLocationUseCase;
    private final UpdateLocationUseCase updateLocationUseCase;
    private final ActivateLocationUseCase activateLocationUseCase;
    private final DeactivateLocationUseCase deactivateLocationUseCase;
    private final GetLocationByIdUseCase getLocationByIdUseCase;
    private final GetAllLocationsUseCase getAllLocationsUseCase;

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

    public LocationResponse getLocationById(final Long id){
        final var query = LocationDesktopMapper.toGetByIdQuery(id);
        final var location = getLocationByIdUseCase.execute(query);
        return LocationDesktopMapper.toResponse(location);
    }

    public List<LocationResponse> listAllLocations(){
        final var locations = getAllLocationsUseCase.execute();
        return LocationDesktopMapper.toResponseList(locations);
    }
}
