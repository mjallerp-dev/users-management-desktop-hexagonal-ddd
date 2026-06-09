package co.edu.udc.desechos_fabrica.generated_residue.application.port.out;

import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject.GeneratedResidueCode;
import java.util.Optional;

public interface FindGeneratedResidueByCodePort {
    Optional<GeneratedResidueModel> findByCode(GeneratedResidueCode code);
}
