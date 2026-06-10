package co.edu.udc.desechos_fabrica.residue.application.service;

import co.edu.udc.desechos_fabrica.residue.application.port.in.UpdateResidueUseCase;
import co.edu.udc.desechos_fabrica.residue.application.port.out.FindResidueByIdPort;
import co.edu.udc.desechos_fabrica.residue.application.port.out.SaveResiduePort;
import co.edu.udc.desechos_fabrica.residue.application.service.dto.command.UpdateResidueCommand;
import co.edu.udc.desechos_fabrica.residue.application.service.mapper.ResidueApplicationMapper;
import co.edu.udc.desechos_fabrica.residue.domain.exception.ResidueNotFoundException;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ResidueId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Set;

@Log
@RequiredArgsConstructor
public class UpdateResidueService implements UpdateResidueUseCase {

    private final FindResidueByIdPort findPort;
    private final SaveResiduePort savePort;
    private final Validator validator;

    @Override
    public ResidueModel execute(final UpdateResidueCommand command) {
        validateCommand(command);

        final ResidueModel currentResidue = findPort.findById(new ResidueId(command.id()))
                .orElseThrow(ResidueNotFoundException::becauseIdWasNotFound);

        final ResidueModel updatedResidue = ResidueApplicationMapper.fromUpdateCommandToModel(currentResidue, command);

        return savePort.save(updatedResidue);
    }

    private void validateCommand(final UpdateResidueCommand command) {
        final Set<ConstraintViolation<UpdateResidueCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
