package co.edu.udc.desechos_fabrica.residue.domain.valueobject;

import co.edu.udc.desechos_fabrica.residue.domain.exception.InvalidTransportTimeException;

public record MaxTransportTime(Integer value) {

    public MaxTransportTime {
        if (value == null) {
            throw InvalidTransportTimeException.becauseValueIsNull();
        }
        validateGreaterThanZero(value);
    }

    private static void validateGreaterThanZero(final Integer value) {
        if (value <= 0) {
            throw InvalidTransportTimeException.becauseMustBeGreaterThanZero();
        }
    }

    @Override
    public String toString() {
        return value + " minutes";
    }
}
