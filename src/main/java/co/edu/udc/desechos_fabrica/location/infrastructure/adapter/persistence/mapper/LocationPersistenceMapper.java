package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.mapper;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.location.domain.enums.LocationStatus;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.*;
import co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.dto.LocationPersistenceDto;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public LocationModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
        return new LocationModel(
                new LocationId(resultSet.getLong("id")),
                new LocationName(resultSet.getString("name")),
                new LocationAddress(resultSet.getString("address")),
                new EnterpriseId(resultSet.getLong("enterprise_id")),
                new LocationCountry(resultSet.getString("country")),
                new LocationState(resultSet.getString("state")),
                new LocationCity(resultSet.getString("city")),
                parseCoordinate(resultSet.getString("coordinate")),
                LocationStatus.valueOf(resultSet.getString("status"))
        );
    }

    private LocationCoordinate parseCoordinate(final String coordinate) {
        if (coordinate == null || coordinate.isBlank()) {
            return null;
        }

        final String[] parts = coordinate.split(",");
        final double latitude = Double.parseDouble(parts[0].trim());
        final double longitude = Double.parseDouble(parts[1].trim());

        return new LocationCoordinate(latitude, longitude);
    }
}
