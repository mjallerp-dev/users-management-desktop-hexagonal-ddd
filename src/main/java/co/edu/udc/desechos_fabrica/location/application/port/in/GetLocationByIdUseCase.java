package co.edu.udc.desechos_fabrica.location.application.port.in;

import co.edu.udc.desechos_fabrica.location.application.service.dto.query.GetLocationByIdQuery;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;

public interface GetLocationByIdUseCase {
    LocationModel execute(GetLocationByIdQuery query);
}
