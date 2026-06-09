package co.edu.udc.desechos_fabrica.generated_residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class InvalidGeneratedQuantityException extends DomainException {

    private static final String MESSAGE_NULL_VALUE = "The generated quantity value must not be null.";
    private static final String MESSAGE_NULL_UNIT = "The measurement unit for the generated quantity must not be null.";
    private static final String MESSAGE_INVALID_VALUE = "The generated quantity must be strictly greater than zero.";

    private InvalidGeneratedQuantityException(final String message) {
        super(message);
    }

    public static InvalidGeneratedQuantityException becauseValueIsNull() {
        return new InvalidGeneratedQuantityException(MESSAGE_NULL_VALUE);
    }

    public static InvalidGeneratedQuantityException becauseUnitIsNull() {
        return new InvalidGeneratedQuantityException(MESSAGE_NULL_UNIT);
    }

    public static InvalidGeneratedQuantityException becauseMustBeGreaterThanZero() {
        return new InvalidGeneratedQuantityException(MESSAGE_INVALID_VALUE);
    }
}
