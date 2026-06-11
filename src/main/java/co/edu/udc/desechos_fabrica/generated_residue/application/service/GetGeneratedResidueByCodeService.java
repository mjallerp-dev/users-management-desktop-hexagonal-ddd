package co.edu.udc.desechos_fabrica.generated_residue.application.service;

import co.edu.udc.desechos_fabrica.generated_residue.application.port.in.GetGeneratedResidueByCodeUseCase;
import co.edu.udc.desechos_fabrica.generated_residue.application.port.out.FindGeneratedResidueByCodePort;
import co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.query.GetGeneratedResidueByCodeQuery;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject.GeneratedResidueCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetGeneratedResidueByCodeService implements GetGeneratedResidueByCodeUseCase {

    private final FindGeneratedResidueByCodePort findGeneratedResidueByCodePort;

    @Override
    public Optional<GeneratedResidueModel> execute(GetGeneratedResidueByCodeQuery query) {
        return findGeneratedResidueByCodePort.findByCode(new GeneratedResidueCode(query.code()));
    }
}
