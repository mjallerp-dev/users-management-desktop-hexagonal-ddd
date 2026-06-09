package co.edu.udc.desechos_fabrica.residue.domain.valueobject;

import co.edu.udc.desechos_fabrica.residue.domain.exception.InvalidTransportQuantityException;

public record MaxTransportQuantity(Double value) {

    public MaxTransportQuantity {
        if (value == null) {
            throw InvalidTransportQuantityException.becauseValueIsNull();
        }
        validateGreaterThanZero(value);
    }

    private static void validateGreaterThanZero(final Double value) {
        if (value <= 0) {
            throw InvalidTransportQuantityException.becauseMustBeGreaterThanZero();
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}