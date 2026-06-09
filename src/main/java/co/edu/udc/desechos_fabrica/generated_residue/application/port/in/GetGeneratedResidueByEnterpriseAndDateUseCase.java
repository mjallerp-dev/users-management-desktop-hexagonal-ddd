package co.edu.udc.desechos_fabrica.generated_residue.application.port.in;

import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import java.time.LocalDate;
import java.util.List;

public interface GetGeneratedResidueByEnterpriseAndDateUseCase {
    List<GeneratedResidueModel> execute(Long enterpriseId, LocalDate startDate, LocalDate endDate);
}
