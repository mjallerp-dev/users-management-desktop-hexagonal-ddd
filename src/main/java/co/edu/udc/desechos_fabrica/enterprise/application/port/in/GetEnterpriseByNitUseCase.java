package co.edu.udc.desechos_fabrica.enterprise.application.port.in;

import co.edu.udc.desechos_fabrica.enterprise.application.service.dto.query.GetEnterpriseByNitQuery;
import co.edu.udc.desechos_fabrica.enterprise.domain.model.EnterpriseModel;

public interface GetEnterpriseByNitUseCase {
    EnterpriseModel execute(GetEnterpriseByNitQuery query);
}
