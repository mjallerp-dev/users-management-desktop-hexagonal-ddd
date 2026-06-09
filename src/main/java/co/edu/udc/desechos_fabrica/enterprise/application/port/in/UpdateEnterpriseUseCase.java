package co.edu.udc.desechos_fabrica.enterprise.application.port.in;

import co.edu.udc.desechos_fabrica.enterprise.application.service.dto.command.UpdateEnterpriseCommand;
import co.edu.udc.desechos_fabrica.enterprise.domain.model.EnterpriseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateEnterpriseUseCase {
    EnterpriseModel execute(@NotNull @Valid UpdateEnterpriseCommand command);
}
