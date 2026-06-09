package co.edu.udc.desechos_fabrica.enterprise.application.port.in;

import co.edu.udc.desechos_fabrica.enterprise.application.service.dto.command.ActivateEnterpriseCommand;
import co.edu.udc.desechos_fabrica.enterprise.domain.model.EnterpriseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface ActivateEnterpriseUseCase {
    EnterpriseModel execute(@NotNull @Valid ActivateEnterpriseCommand command);
}
