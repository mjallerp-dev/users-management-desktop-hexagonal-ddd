package co.edu.udc.desechos_fabrica.residue.application.port.out;

import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;

public interface SaveResiduePort {
    ResidueModel save(ResidueModel model);
}
