package co.edu.udc.desechos_fabrica.location.application.port.out;

import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.LocationId;

public interface UpdateLocationPort {
    LocationModel update(LocationId id, LocationModel locationToUpdate);
}
