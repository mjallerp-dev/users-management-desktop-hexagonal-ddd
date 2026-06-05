package co.edu.udc.desechos_fabrica.location.application.service.mapper;

import co.edu.udc.desechos_fabrica.location.application.port.in.ActivateLocationUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.out.GetLocationByIdPort;
import co.edu.udc.desechos_fabrica.location.application.port.out.UpdateLocationPort;
import co.edu.udc.desechos_fabrica.location.application.service.dto.command.ActivateLocationCommand;
import co.edu.udc.desechos_fabrica.location.domain.exception.LocationNotFoundException;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.LocationId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class ActivateLocationService implements ActivateLocationUseCase {

    private final UpdateLocationPort updateLocationPort;
    private final GetLocationByIdPort getLocationByIdPort;
    private final Validator validator;

    @Override
    public LocationModel execute(final ActivateLocationCommand command) {
        validateCommand(command);

        final LocationId locationId = new LocationId(command.id());
        final LocationModel existingLocation = findExistingLocationOrFail(locationId);

        final LocationModel locationToActivate = LocationApplicationMapper.fromActivateCommandToModel(existingLocation);
        return updateLocationPort.update(locationToActivate);
    }

    public void validateCommand(final ActivateLocationCommand command){
        final Set<ConstraintViolation<ActivateLocationCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public LocationModel findExistingLocationOrFail(final LocationId id) {
        return getLocationByIdPort.getById(id)
                .orElseThrow(() -> LocationNotFoundException.becauseIdWasNotFound(id.value()));
    }
}
