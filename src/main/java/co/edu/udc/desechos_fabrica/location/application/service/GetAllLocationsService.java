package co.edu.udc.desechos_fabrica.location.application.service;

import co.edu.udc.desechos_fabrica.location.application.port.in.GetAllLocationsUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.out.GetAllLocationsPort;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllLocationsService implements GetAllLocationsUseCase {

    private final GetAllLocationsPort getAllLocationsPort;

    @Override
    public List<LocationModel> execute() {
        return getAllLocationsPort.getAll();
    }
}
