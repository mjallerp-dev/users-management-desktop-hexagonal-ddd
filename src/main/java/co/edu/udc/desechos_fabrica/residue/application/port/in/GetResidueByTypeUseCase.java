package co.edu.udc.desechos_fabrica.residue.application.port.in;

import co.edu.udc.desechos_fabrica.residue.application.service.dto.query.GetResidueByTypeQuery;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;
import java.util.List;

public interface GetResidueByTypeUseCase {
    List<ResidueModel> execute(GetResidueByTypeQuery query);
}
