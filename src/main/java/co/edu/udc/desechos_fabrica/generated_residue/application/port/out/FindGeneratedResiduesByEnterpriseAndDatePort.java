package co.edu.udc.desechos_fabrica.generated_residue.application.port.out;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import java.time.LocalDate;
import java.util.List;

public interface FindGeneratedResiduesByEnterpriseAndDatePort {
    List<GeneratedResidueModel> findByEnterpriseAndDateRange(
            EnterpriseId enterpriseId, LocalDate start, LocalDate end);
}
