package co.edu.udc.desechos_fabrica.residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class InvalidTransportTimeException extends DomainException {

    private static final String MESSAGE_NULL = "The maximum transport time must not be null.";
    private static final String MESSAGE_INVALID = "The maximum transport time must be strictly greater than zero minutes.";

    private InvalidTransportTimeException(final String message) {
        super(message);
    }

    public static InvalidTransportTimeException becauseValueIsNull() {
        return new InvalidTransportTimeException(MESSAGE_NULL);
    }

    public static InvalidTransportTimeException becauseMustBeGreaterThanZero() {
        return new InvalidTransportTimeException(MESSAGE_INVALID);
    }
}
