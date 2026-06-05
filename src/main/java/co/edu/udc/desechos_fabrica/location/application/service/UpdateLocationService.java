package co.edu.udc.desechos_fabrica.location.application.service;

import co.edu.udc.desechos_fabrica.location.application.port.in.UpdateLocationUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.out.GetLocationByIdPort;
import co.edu.udc.desechos_fabrica.location.application.port.out.UpdateLocationPort;
import co.edu.udc.desechos_fabrica.location.application.service.dto.command.UpdateLocationCommand;
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
public final class UpdateLocationService implements UpdateLocationUseCase {

    private final UpdateLocationPort updateLocationPort;
    private final GetLocationByIdPort getLocationByIdPort;
    private final Validator validator;

    @Override
    public LocationModel execute(final UpdateLocationCommand command) {
        validateCommand(command);

        final LocationId locationId = new LocationId(command.id());
        final LocationModel existingLocation = findExistingLocationOrFail(locationId);

        final LocationModel locationToUpdate = LocationApplicationMapper.fromUpdateCommandToModel(command, existingLocation);
        return updateLocationPort.update(locationToUpdate);
    }

    public void validateCommand(UpdateLocationCommand command) {
        final Set<ConstraintViolation<UpdateLocationCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public LocationModel findExistingLocationOrFail(final LocationId id) {
        return getLocationByIdPort.getById(id)
                .orElseThrow(() -> LocationNotFoundException.becauseIdWasNotFound(id.value()));
    }
}
