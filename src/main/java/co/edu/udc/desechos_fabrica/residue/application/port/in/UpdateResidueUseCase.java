package co.edu.udc.desechos_fabrica.residue.application.port.in;

import co.edu.udc.desechos_fabrica.residue.application.service.dto.command.UpdateResidueCommand;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;

public interface UpdateResidueUseCase {
    ResidueModel execute(UpdateResidueCommand command);
}
