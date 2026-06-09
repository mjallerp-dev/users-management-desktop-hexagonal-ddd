package co.edu.udc.desechos_fabrica.generated_residue.application.service;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.generated_residue.application.port.in.RegisterGeneratedResidueUseCase;
import co.edu.udc.desechos_fabrica.generated_residue.application.port.out.ExistsGeneratedResiduePort;
import co.edu.udc.desechos_fabrica.generated_residue.application.port.out.SaveGeneratedResiduePort;
import co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.command.RegisterGeneratedResidueCommand;
import co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.mapper.GeneratedResidueApplicationMapper;
import co.edu.udc.desechos_fabrica.generated_residue.domain.exception.GeneratedResidueAlreadyExistsException;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject.GeneratedResidueCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class RegisterGeneratedResidueService implements RegisterGeneratedResidueUseCase {

    private final SaveGeneratedResiduePort savePort;
    private final ExistsGeneratedResiduePort existsPort;
    private final Validator validator;

    @Override
    public Long execute(RegisterGeneratedResidueCommand command) {
        validate(command);

        if (existsPort.existsByCodeAndEnterpriseId(new GeneratedResidueCode(command.code()), new EnterpriseId(command.enterpriseId()))) {
            throw GeneratedResidueAlreadyExistsException.withCodeAndEnterprise(command.code());
        }

        GeneratedResidueModel model = GeneratedResidueApplicationMapper.fromRegisterCommandToModel(command);
        return savePort.save(model).getId();
    }

    private void validate(RegisterGeneratedResidueCommand command) {
        Set<ConstraintViolation<RegisterGeneratedResidueCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }
}