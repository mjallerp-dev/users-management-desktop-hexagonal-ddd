package co.edu.udc.desechos_fabrica.location.application.service.mapper;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.location.application.service.dto.command.CreateLocationCommand;
import co.edu.udc.desechos_fabrica.location.application.service.dto.command.UpdateLocationCommand;
import co.edu.udc.desechos_fabrica.location.application.service.dto.query.GetLocationByIdQuery;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LocationApplicationMapper {

    public LocationModel fromCreateCommandToModel(CreateLocationCommand command) {
        return LocationModel.create(
                new LocationName(command.name()),
                new LocationAddress(command.address()),
                new EnterpriseId(command.enterpriseId()),
                new LocationCountry(command.country()),
                new LocationState(command.state()),
                new LocationCity(command.city()),
                new LocationCoordinate(command.coordinate().latitude(), command.coordinate().longitude()));
    }

    public LocationModel fromUpdateCommandToModel(UpdateLocationCommand command, final LocationModel currentLocation) {
        final LocationName newName = new LocationName(command.newName());
        final LocationAddress newAddress = new LocationAddress(command.newAddress());
        final LocationCity newCity = new LocationCity(command.newCity());
        final LocationState newState = new LocationState(command.newState());
        final LocationCountry newCountry = new LocationCountry(command.newCountry());
        final LocationCoordinate newCoordinate = new LocationCoordinate(command.newCoordinate().latitude(), command.newCoordinate().longitude());

        return currentLocation.updateWith(newName, newAddress, newCity, newState, newCountry, newCoordinate);
    }

    public LocationId fromGetLocationByIdQueryToLocationId(final GetLocationByIdQuery query) {
        return new LocationId(query.id());
    }

}
