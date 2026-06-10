package co.edu.udc.desechos_fabrica.residue.application.port.in;

import co.edu.udc.desechos_fabrica.residue.application.service.dto.command.RegisterResidueCommand;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;

public interface RegisterResidueUseCase {
    ResidueModel execute(RegisterResidueCommand command);
}
