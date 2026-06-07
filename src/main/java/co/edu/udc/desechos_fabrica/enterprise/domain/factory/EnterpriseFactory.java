package co.edu.udc.desechos_fabrica.enterprise.domain.factory;

import co.edu.udc.desechos_fabrica.enterprise.domain.enums.EnterpriseRole;
import co.edu.udc.desechos_fabrica.enterprise.domain.enums.EnterpriseStatus;
import co.edu.udc.desechos_fabrica.enterprise.domain.model.EnterpriseModel;
import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseName;
import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseNit;

public class EnterpriseFactory {

    public static EnterpriseModel create(
            final EnterpriseId id,
            final EnterpriseNit nit,
            final EnterpriseName name,
            final EnterpriseRole role) {
        return new EnterpriseModel(id, nit, name, EnterpriseStatus.PENDING, role);
    }
}
