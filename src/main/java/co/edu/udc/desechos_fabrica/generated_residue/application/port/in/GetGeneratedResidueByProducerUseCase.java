package co.edu.udc.desechos_fabrica.generated_residue.application.port.in;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import java.util.List;

public interface GetGeneratedResidueByProducerUseCase {
    List<GeneratedResidueModel> execute(EnterpriseId enterpriseId);
}

