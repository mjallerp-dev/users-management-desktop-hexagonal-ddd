package co.edu.udc.desechos_fabrica.residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class InvalidTransportQuantityException extends DomainException {

    private static final String MESSAGE_NULL = "The maximum transport quantity must not be null.";
    private static final String MESSAGE_INVALID = "The maximum transport quantity must be greater than zero.";

    private InvalidTransportQuantityException(final String message) {
        super(message);
    }

    public static InvalidTransportQuantityException becauseValueIsNull() {
        return new InvalidTransportQuantityException(MESSAGE_NULL);
    }

    public static InvalidTransportQuantityException becauseMustBeGreaterThanZero() {
        return new InvalidTransportQuantityException(MESSAGE_INVALID);
    }
}
