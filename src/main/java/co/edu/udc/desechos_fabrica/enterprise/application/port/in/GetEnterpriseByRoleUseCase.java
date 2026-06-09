package co.edu.udc.desechos_fabrica.enterprise.application.port.in;

import co.edu.udc.desechos_fabrica.enterprise.application.service.dto.query.GetEnterpriseByRoleQuery;
import co.edu.udc.desechos_fabrica.enterprise.domain.model.EnterpriseModel;

public interface GetEnterpriseByRoleUseCase {
    EnterpriseModel execute(GetEnterpriseByRoleQuery query);
}
