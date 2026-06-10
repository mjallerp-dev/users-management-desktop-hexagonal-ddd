package co.edu.udc.desechos_fabrica.residue.application.service;

import co.edu.udc.desechos_fabrica.residue.application.port.in.RegisterResidueUseCase;
import co.edu.udc.desechos_fabrica.residue.application.port.out.SaveResiduePort;
import co.edu.udc.desechos_fabrica.residue.application.service.dto.command.RegisterResidueCommand;
import co.edu.udc.desechos_fabrica.residue.application.service.mapper.ResidueApplicationMapper;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import java.util.Set;

@Log
@RequiredArgsConstructor
public class RegisterResidueService implements RegisterResidueUseCase {

    private final SaveResiduePort saveResiduePort;
    private final Validator validator;

    @Override
    public ResidueModel execute(final RegisterResidueCommand command) {
        validateCommand(command);

        final ResidueModel residueToSave = ResidueApplicationMapper.fromRegisterCommandToModel(command);
        return saveResiduePort.save(residueToSave);
    }

    private void validateCommand(final RegisterResidueCommand command) {
        final Set<ConstraintViolation<RegisterResidueCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
