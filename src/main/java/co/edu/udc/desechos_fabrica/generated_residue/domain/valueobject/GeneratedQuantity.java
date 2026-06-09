package co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject;

import co.edu.udc.desechos_fabrica.generated_residue.domain.exception.InvalidGeneratedQuantityException;
import co.edu.udc.desechos_fabrica.shared.domain.enums.MeasurementUnit;

public record GeneratedQuantity(Double value, MeasurementUnit unit) {

    public GeneratedQuantity {
        if (value == null) {
            throw InvalidGeneratedQuantityException.becauseValueIsNull();
        }
        if (unit == null) {
            throw InvalidGeneratedQuantityException.becauseUnitIsNull();
        }
        if (value <= 0) {
            throw InvalidGeneratedQuantityException.becauseMustBeGreaterThanZero();
        }
    }

    @Override
    public String toString() {
        return value + " " + unit.getSymbol();
    }
}
