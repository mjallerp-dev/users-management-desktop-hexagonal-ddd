package co.edu.udc.desechos_fabrica.residue.application.service.mapper;

import co.edu.udc.desechos_fabrica.residue.application.port.in.GetResidueByTypeUseCase;
import co.edu.udc.desechos_fabrica.residue.application.port.out.FindResidueByTypePort;
import co.edu.udc.desechos_fabrica.residue.application.service.dto.query.GetResidueByTypeQuery;
import co.edu.udc.desechos_fabrica.residue.domain.enums.ResidueType;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetResidueByTypeService implements GetResidueByTypeUseCase {

    private final FindResidueByTypePort findResidueByTypePort;

    @Override
    public List<ResidueModel> execute(GetResidueByTypeQuery query) {
        return findResidueByTypePort.findByType(ResidueType.valueOf(query.type()));
    }
}
