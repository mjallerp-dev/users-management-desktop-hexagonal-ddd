package co.edu.udc.desechos_fabrica.generated_residue.application.port.in;

import co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.query.GetGeneratedResidueByProducerQuery;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import java.util.List;

public interface GetGeneratedResidueByProducerUseCase {
    List<GeneratedResidueModel> execute(GetGeneratedResidueByProducerQuery query);
}
