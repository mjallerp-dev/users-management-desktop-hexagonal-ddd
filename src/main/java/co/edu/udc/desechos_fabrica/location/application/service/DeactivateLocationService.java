package co.edu.udc.desechos_fabrica.location.application.service;

import co.edu.udc.desechos_fabrica.location.application.port.in.DeactivateLocationUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.out.GetLocationByIdPort;
import co.edu.udc.desechos_fabrica.location.application.port.out.UpdateLocationPort;
import co.edu.udc.desechos_fabrica.location.application.service.dto.command.DeactivateLocationCommand;
import co.edu.udc.desechos_fabrica.location.application.service.mapper.LocationApplicationMapper;
import co.edu.udc.desechos_fabrica.location.domain.exception.LocationNotFoundException;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.LocationId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class DeactivateLocationService implements DeactivateLocationUseCase {

    private final UpdateLocationPort updateLocationPort;
    private final GetLocationByIdPort getLocationByIdPort;
    private final Validator validator;

    @Override
    public LocationModel execute(final DeactivateLocationCommand command) {
        validateCommand(command);

        final LocationId locationId = new LocationId(command.id());
        final LocationModel existingLocation = findExistingLocationOrFail(locationId);

        final LocationModel locationToDeactivate = LocationApplicationMapper.fromDeactivateCommandToModel(existingLocation);
        return updateLocationPort.update(locationId, locationToDeactivate);
    }

    private void validateCommand(final DeactivateLocationCommand command){
        final Set<ConstraintViolation<DeactivateLocationCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private LocationModel findExistingLocationOrFail(final LocationId id) {
        return getLocationByIdPort.getById(id)
                .orElseThrow(() -> LocationNotFoundException.becauseIdWasNotFound(id.value()));
    }
}
