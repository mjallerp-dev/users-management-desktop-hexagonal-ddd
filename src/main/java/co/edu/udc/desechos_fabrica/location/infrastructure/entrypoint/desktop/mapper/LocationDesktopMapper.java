package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.mapper;

import co.edu.udc.desechos_fabrica.location.application.service.dto.command.ActivateLocationCommand;
import co.edu.udc.desechos_fabrica.location.application.service.dto.command.CreateLocationCommand;
import co.edu.udc.desechos_fabrica.location.application.service.dto.command.DeactivateLocationCommand;
import co.edu.udc.desechos_fabrica.location.application.service.dto.command.UpdateLocationCommand;
import co.edu.udc.desechos_fabrica.location.application.service.dto.query.GetLocationByIdQuery;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto.*;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;

import java.util.List;

public final class LocationDesktopMapper {

    private LocationDesktopMapper() {}

    public static CreateLocationCommand toCreateCommand(final CreateLocationRequest request){
        return new CreateLocationCommand(
                request.name(),
                request.address(),
                request.enterpriseId(),
                request.country(),
                request.state(),
                request.city(),
                new CreateLocationCommand.CoordinateCommand(request.latitude(), request.longitude()))
                ;
    }

    public static UpdateLocationCommand toUpdateCommand(final UpdateLocationRequest request) {
        return new UpdateLocationCommand(
                request.id(),
                request.newName(),
                request.newAddress(),
                request.newCountry(),
                request.newState(),
                request.newCity(),
                new UpdateLocationCommand.CoordinateCommand(request.newLatitude(), request.newLongitude())
        );
    }

    public static ActivateLocationCommand toActivateCommand(final ActivateLocationRequest request) {
        return new ActivateLocationCommand(
                request.id()
        );
    }

    public static DeactivateLocationCommand toDeactivateCommand(final DeactivateLocationRequest request){
        return new DeactivateLocationCommand(
                request.id()
        );
    }

    public static GetLocationByIdQuery toGetByIdQuery(final Long id) {
        return new GetLocationByIdQuery(id);
    }

    public static LocationResponse toResponse(final LocationModel Location) {
        return new LocationResponse(
                Location.getId(),
                Location.getName().value(),
                Location.getAddress().value(),
                Location.getEnterpriseId().value(),
                Location.getCountry().value(),
                Location.getState().value(),
                Location.getCity().value(),
                Location.getCoordinate().latitude(),
                Location.getCoordinate().longitude(),
                Location.getStatus().name());
    }

    public static List<LocationResponse> toResponseList(final List<LocationModel> Locations) {
        return Locations.stream().map(LocationDesktopMapper::toResponse).toList();
    }

}
