package co.edu.udc.desechos_fabrica.generated_residue.application.port.in;

import co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.command.RegisterGeneratedResidueCommand;

public interface RegisterGeneratedResidueUseCase {
    Long execute(RegisterGeneratedResidueCommand command);
}
