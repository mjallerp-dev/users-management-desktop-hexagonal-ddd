package co.edu.udc.desechos_fabrica.location.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.DomainException;

public class InvalidLocationIdException extends DomainException{

    private static final String MESSAGE_NULL = "The location id must not be null.";

    private InvalidLocationIdException(final String message) {
        super(message);
    }

    public static InvalidLocationIdException becauseValueIsNull() {
        return new InvalidLocationIdException(MESSAGE_NULL);
    }

    public static InvalidLocationIdException becauseInvalidFormat() {
        return new InvalidLocationIdException("The location id must contain only digits.");
    }

}
