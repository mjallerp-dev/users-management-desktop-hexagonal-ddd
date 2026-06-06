package co.edu.udc.desechos_fabrica.location.application.port.in;

import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import java.util.Optional;

public interface GetAllLocationsUseCase {
    Optional<LocationModel> execute();
}
