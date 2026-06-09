package co.edu.udc.desechos_fabrica.residue.domain.valueobject;

import co.edu.udc.desechos_fabrica.location.domain.exception.InvalidLocationIdException;

public record ResidueId(Long value){

    public ResidueId {
        if (value == null){
            throw InvalidLocationIdException.becauseValueIsNull();
        }
        validateGreaterThanZero(value);
    }

    private static void validateGreaterThanZero(final Long value) {
        if (value <= 0) {
            throw InvalidLocationIdException.becauseInvalidFormat();
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
