package co.edu.udc.desechos_fabrica.residue.application.port.in;

import co.edu.udc.desechos_fabrica.residue.domain.enums.ResidueType;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;
import java.util.List;

public interface GetResidueByTypeUseCase {
    List<ResidueModel> execute(ResidueType type);
}
