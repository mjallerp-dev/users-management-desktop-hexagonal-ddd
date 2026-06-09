package co.edu.udc.desechos_fabrica.residue.domain.valueobject;

import co.edu.udc.desechos_fabrica.location.domain.exception.InvalidLocationNameException;
import co.edu.udc.desechos_fabrica.residue.domain.exception.InvalidResidueNameException;

import java.util.Objects;

public record ResidueName(String value) {

    private static final int MINIMUM_LENGTH = 3;

    public ResidueName {
        final String normalizedValue = Objects.requireNonNull(value, "Residue name can not be null").trim();
        validateNotEmpty(normalizedValue);
        validateMinimumLength(normalizedValue);
        value = normalizedValue;
    }

    public void validateNotEmpty(final String normalizedValue){
        if (normalizedValue.isEmpty()) {
            throw InvalidResidueNameException.becauseValueIsEmpty();
        }
    }

    public void validateMinimumLength(final String normalizedValue){
        if (normalizedValue.length() < MINIMUM_LENGTH){
            throw InvalidResidueNameException.becauseLengthIsTooShort(MINIMUM_LENGTH);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
