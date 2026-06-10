package co.edu.udc.desechos_fabrica.residue.application.service;

import co.edu.udc.desechos_fabrica.residue.application.port.in.GetResidueByIdUseCase;
import co.edu.udc.desechos_fabrica.residue.application.port.out.FindResidueByIdPort;
import co.edu.udc.desechos_fabrica.residue.application.service.dto.query.GetResidueByIdQuery;
import co.edu.udc.desechos_fabrica.residue.domain.exception.ResidueNotFoundException;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ResidueId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetResidueByIdService implements GetResidueByIdUseCase {

    private final FindResidueByIdPort findResidueByIdPort;

    @Override
    public ResidueModel execute(GetResidueByIdQuery query) {
        return findResidueByIdPort.findById(new ResidueId(query.id()))
                .orElseThrow(ResidueNotFoundException::becauseIdWasNotFound);
    }
}
