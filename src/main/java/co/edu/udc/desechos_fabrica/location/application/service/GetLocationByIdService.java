package co.edu.udc.desechos_fabrica.location.application.service;

import co.edu.udc.desechos_fabrica.location.application.port.in.GetLocationByIdUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.out.GetLocationByIdPort;
import co.edu.udc.desechos_fabrica.location.application.service.dto.query.GetLocationByIdQuery;
import co.edu.udc.desechos_fabrica.location.application.service.mapper.LocationApplicationMapper;
import co.edu.udc.desechos_fabrica.location.domain.exception.LocationNotFoundException;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.LocationId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class GetLocationByIdService implements GetLocationByIdUseCase {

    private final GetLocationByIdPort getLocationByIdPort;
    private final Validator validator;

    @Override
    public LocationModel execute(final GetLocationByIdQuery query) {
        validateQuery(query);

        final LocationId id = LocationApplicationMapper.fromGetLocationByIdQueryToLocationId(query);
        return getLocationByIdPort.getById(id.value())
                .orElseThrow(() -> LocationNotFoundException.becauseIdWasNotFound(id.value()));
    }

    private void validateQuery(final GetLocationByIdQuery query){
        final Set<ConstraintViolation<GetLocationByIdQuery>> violations = validator.validate(query);
    }
}
