package co.edu.udc.desechos_fabrica.location.application.port.in;

import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import java.util.List;

public interface GetAllLocationsUseCase {
    List<LocationModel> execute();
}
