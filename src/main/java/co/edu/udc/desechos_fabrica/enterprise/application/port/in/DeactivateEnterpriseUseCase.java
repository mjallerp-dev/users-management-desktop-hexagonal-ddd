package co.edu.udc.desechos_fabrica.enterprise.application.port.in;

import co.edu.udc.desechos_fabrica.enterprise.application.service.dto.command.DeactivateEnterpriseCommand;
import co.edu.udc.desechos_fabrica.enterprise.domain.model.EnterpriseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeactivateEnterpriseUseCase {
    EnterpriseModel execute(@NotNull @Valid DeactivateEnterpriseCommand command);
}
