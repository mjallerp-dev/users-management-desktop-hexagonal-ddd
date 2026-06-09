package co.edu.udc.desechos_fabrica.generated_residue.domain.model;

import co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject.GeneratedQuantity;
import co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject.GeneratedResidueCode;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ResidueId;
import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import lombok.Value;
import lombok.With;

import java.util.Objects;

@Value
@With
public class GeneratedResidueModel {

    Long id;
    GeneratedResidueCode code;
    ResidueId residueId;
    GeneratedQuantity quantity;
    EnterpriseId enterpriseId;

    public GeneratedResidueModel(
            final Long id,
            final GeneratedResidueCode code,
            final ResidueId residueId,
            final GeneratedQuantity quantity,
            final EnterpriseId enterpriseId) {

        this.id = id;
        this.code = Objects.requireNonNull(code, "Generated residue business code must not be null");
        this.residueId = Objects.requireNonNull(residueId, "Residue catalog reference (id) must not be null");
        this.quantity = Objects.requireNonNull(quantity, "Generated quantity must not be null");
        this.enterpriseId = Objects.requireNonNull(enterpriseId, "Enterprise reference (id) must not be null");
    }

    public static GeneratedResidueModel create(
            final GeneratedResidueCode code,
            final ResidueId residueId,
            final GeneratedQuantity quantity,
            final EnterpriseId enterpriseId) {
        return new GeneratedResidueModel(null, code, residueId, quantity, enterpriseId);
    }

    public GeneratedResidueModel updateWith(
            final GeneratedResidueCode code,
            final GeneratedQuantity quantity) {
        return new GeneratedResidueModel(
                this.id,
                code,
                this.residueId,
                quantity,
                this.enterpriseId
        );
    }
}
