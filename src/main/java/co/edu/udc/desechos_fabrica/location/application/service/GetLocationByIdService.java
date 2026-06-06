package co.edu.udc.desechos_fabrica.location.application.service;

import co.edu.udc.desechos_fabrica.location.application.port.in.GetLocationByIdUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.out.GetLocationByIdPort;
import co.edu.udc.desechos_fabrica.location.application.service.dto.query.GetLocationByIdQuery;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class GetLocationByIdService implements GetLocationByIdUseCase {

    private final GetLocationByIdPort getLocationByIdPort;
    private final Validator validator;

    @Override
    public Optional<LocationModel> getById(Long id) {
        return getLocationByIdPort.getById(id);
    }

    private void validateQuery(final GetLocationByIdQuery query){
        final Set<ConstraintViolation<GetLocationByIdQuery>> violations = validator.validate(query);
    }
}
