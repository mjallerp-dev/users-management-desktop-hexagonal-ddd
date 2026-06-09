package co.edu.udc.desechos_fabrica.location.domain.model;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.*;
import co.edu.udc.desechos_fabrica.location.domain.enums.*;
import lombok.Value;
import lombok.With;

import java.util.Objects;

@Value
@With
public class LocationModel {

    Long id;
    LocationName name;
    LocationAddress address;
    EnterpriseId enterpriseId;
    LocationCountry country;
    LocationState state;
    LocationCity city;
    LocationCoordinate coordinate;
    LocationStatus status;

    public LocationModel(Long id, LocationName name, LocationAddress address, EnterpriseId enterpriseId,
                         LocationCountry country, LocationState state, LocationCity city,
                         LocationCoordinate coordinate, LocationStatus status) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.address = Objects.requireNonNull(address, "Address cannot be null");
        this.country = Objects.requireNonNull(country, "Country cannot be null");
        this.state = Objects.requireNonNull(state, "State cannot be null");
        this.city = Objects.requireNonNull(city, "City cannot be null");
        this.coordinate = Objects.requireNonNull(coordinate, "Coordinate cannot be null");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.enterpriseId = Objects.requireNonNull(enterpriseId, "Enterprise ID cannot be null");
    }

    public static LocationModel create(
            final Long id,
            final LocationName name,
            final LocationAddress address,
            final EnterpriseId enterpriseId,
            final LocationCountry country,
            final LocationState state,
            final LocationCity city,
            final LocationCoordinate coordinate) {

        return new LocationModel(id, name, address, enterpriseId, country, state, city, coordinate, LocationStatus.ACTIVE);
    }

    public LocationModel updateWith(
            final LocationName newName,
            final LocationAddress newAddress,
            final LocationCity newCity,
            final LocationState newState,
            final LocationCountry newCountry,
            final LocationCoordinate newCoordinate) {
        return new LocationModel(id, newName, newAddress, enterpriseId, newCountry, newState, newCity, newCoordinate, status);
    }

    public LocationModel activate() {
        return new LocationModel(id, name, address, enterpriseId, country, state, city, coordinate, LocationStatus.ACTIVE);
    }

    public LocationModel deactivate() {
        return new LocationModel(id, name, address, enterpriseId, country, state, city, coordinate, LocationStatus.INACTIVE);
    }
}
