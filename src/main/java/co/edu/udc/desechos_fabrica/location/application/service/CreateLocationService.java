package co.edu.udc.desechos_fabrica.location.application.service;

import co.edu.udc.desechos_fabrica.location.application.port.in.CreateLocationUseCase;
import co.edu.udc.desechos_fabrica.location.application.port.out.SaveLocationPort;
import co.edu.udc.desechos_fabrica.location.application.service.dto.command.CreateLocationCommand;
import co.edu.udc.desechos_fabrica.location.application.service.mapper.LocationApplicationMapper;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Set;

@Log
@RequiredArgsConstructor
public class CreateLocationService implements CreateLocationUseCase {

    private final SaveLocationPort saveLocationPort;
    private final Validator validator;

    @Override
    public LocationModel execute(final CreateLocationCommand command) {
        validateCommand(command);

        final LocationModel LocationToSave = LocationApplicationMapper.fromCreateCommandToModel(command);
        return saveLocationPort.save(LocationToSave);}

    private void validateCommand(final CreateLocationCommand command) {
        final Set<ConstraintViolation<CreateLocationCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
