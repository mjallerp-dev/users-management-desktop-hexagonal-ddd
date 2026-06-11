package co.edu.udc.desechos_fabrica.generated_residue.application.port.in;

import co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.query.GetGeneratedResidueByCodeQuery;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import java.util.Optional;

public interface GetGeneratedResidueByCodeUseCase {
    Optional<GeneratedResidueModel> execute(GetGeneratedResidueByCodeQuery query);
}
