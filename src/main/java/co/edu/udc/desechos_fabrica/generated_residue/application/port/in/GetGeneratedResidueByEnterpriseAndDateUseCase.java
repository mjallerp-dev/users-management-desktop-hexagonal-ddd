package co.edu.udc.desechos_fabrica.generated_residue.application.port.in;

import co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.query.GetGeneratedResidueByEnterpriseAndDateQuery;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import java.util.List;

public interface GetGeneratedResidueByEnterpriseAndDateUseCase {
    List<GeneratedResidueModel> execute(GetGeneratedResidueByEnterpriseAndDateQuery query);
}
