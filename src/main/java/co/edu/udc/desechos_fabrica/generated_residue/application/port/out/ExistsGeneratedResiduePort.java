package co.edu.udc.desechos_fabrica.generated_residue.application.port.out;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject.GeneratedResidueCode;

public interface ExistsGeneratedResiduePort {
    boolean existsByCodeAndEnterpriseId(GeneratedResidueCode code, EnterpriseId enterpriseId);
}
