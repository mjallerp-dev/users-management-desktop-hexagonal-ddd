package co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject;

import co.edu.udc.desechos_fabrica.generated_residue.domain.exception.InvalidGeneratedQuantityException;
import co.edu.udc.desechos_fabrica.shared.domain.enums.MeasurementUnit;

public record GeneratedQuantity(Double value) {

    public GeneratedQuantity {
        if (value == null) {
            throw InvalidGeneratedQuantityException.becauseValueIsNull();
        }
        if (value <= 0) {
            throw InvalidGeneratedQuantityException.becauseMustBeGreaterThanZero();
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
