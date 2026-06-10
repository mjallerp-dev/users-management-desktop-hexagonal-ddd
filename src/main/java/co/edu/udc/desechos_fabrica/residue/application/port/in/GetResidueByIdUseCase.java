package co.edu.udc.desechos_fabrica.residue.application.port.in;

import co.edu.udc.desechos_fabrica.residue.application.service.dto.query.GetResidueByIdQuery;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;

public interface GetResidueByIdUseCase {
    ResidueModel execute(GetResidueByIdQuery query);
}