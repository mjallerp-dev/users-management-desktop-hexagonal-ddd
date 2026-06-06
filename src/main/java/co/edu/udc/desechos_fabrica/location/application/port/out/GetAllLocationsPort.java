package co.edu.udc.desechos_fabrica.location.application.port.out;

import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import java.util.List;

public interface GetAllLocationsPort {
    List<LocationModel> getAll();
}
