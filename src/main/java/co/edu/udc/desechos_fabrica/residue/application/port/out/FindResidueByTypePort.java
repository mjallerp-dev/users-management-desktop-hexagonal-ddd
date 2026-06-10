package co.edu.udc.desechos_fabrica.residue.application.port.out;

import co.edu.udc.desechos_fabrica.residue.domain.enums.ResidueType;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;

import java.util.List;

public interface FindResidueByTypePort {
    List<ResidueModel> findByType(ResidueType type);
}
