package co.edu.udc.desechos_fabrica.generated_residue.application.service;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.generated_residue.application.port.in.GetGeneratedResidueByEnterpriseAndDateUseCase;
import co.edu.udc.desechos_fabrica.generated_residue.application.port.out.FindGeneratedResiduesByEnterpriseAndDatePort;
import co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.query.GetGeneratedResidueByEnterpriseAndDateQuery;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetGeneratedResiduesByEnterpriseAndDateService implements GetGeneratedResidueByEnterpriseAndDateUseCase {

    private final FindGeneratedResiduesByEnterpriseAndDatePort findGeneratedResiduesByEnterpriseAndDatePort;

    @Override
    public List<GeneratedResidueModel> execute(GetGeneratedResidueByEnterpriseAndDateQuery query) {
        return findGeneratedResiduesByEnterpriseAndDatePort.findByEnterpriseAndDateRange(
                new EnterpriseId(query.enterpriseId()),
                query.startDate(),
                query.endDate()
        );
    }
}
