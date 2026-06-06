package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.mapper;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.location.domain.enums.LocationStatus;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.*;
import co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.dto.LocationPersistenceDto;
import co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.entity.LocationEntity;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public LocationEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
        return new LocationEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("address"),
                resultSet.getLong("enterprise_id"),
                resultSet.getString("country"),
                resultSet.getString("state"),
                resultSet.getString("city"),
                resultSet.getString("coordinate"),
                resultSet.getString("status"),
                resultSet.getString("created_at"),
                resultSet.getString("updated_at")
        );
    }

    public LocationModel fromEntityToModel(final LocationEntity entity) {
        return new LocationModel(
                entity.id() != null ? new LocationId(entity.id()) : null,
                new LocationName(entity.name()),
                new LocationAddress(entity.address()),
                new EnterpriseId(entity.enterpriseId()),
                new LocationCountry(entity.country()),
                new LocationState(entity.state()),
                new LocationCity(entity.city()),
                parseCoordinate(entity.coordinate()),
                LocationStatus.valueOf(entity.status())
        );
    }

    public LocationModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
        return fromEntityToModel(fromResultSetToEntity(resultSet));
    }

    public List<LocationModel> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
        final List<LocationModel> locations = new ArrayList<>();
        while (resultSet.next()) {
            locations.add(fromResultSetToModel(resultSet));
        }
        return locations;
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
