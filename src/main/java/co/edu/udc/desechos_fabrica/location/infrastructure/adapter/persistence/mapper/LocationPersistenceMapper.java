package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.mapper;

import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.*;
import co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.dto.LocationPersistenceDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LocationPersistenceMapper {

     public LocationPersistenceDto fromModelToDto(final LocationModel location) {

         final String combinateCoordinate = location.getCoordinate().latitude() + "," + location.getCoordinate().longitude();

         return new LocationPersistenceDto(
            location.getId() != null ? location.getId().value() : null,
            location.getName().value(),
            location.getAddress().value(),
            location.getEnterpriseId().value(),
            location.getCountry().value(),
            location.getState().value(),
            location.getCity().value(),
            combinateCoordinate,
            location.getStatus().name(),
            null,
            null);
    }
}
