package co.edu.udc.desechos_fabrica.residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class InvalidResidueIdException extends DomainException {

    private static final String MESSAGE_NULL = "The residue ID must not be null.";
    private static final String MESSAGE_INVALID = "The residue ID must be a positive value greater than zero.";

    private InvalidResidueIdException(final String message) {
        super(message);
    }

    public static InvalidResidueIdException becauseValueIsNull() {
        return new InvalidResidueIdException(MESSAGE_NULL);
    }

    public static InvalidResidueIdException becauseInvalidFormat() {
        return new InvalidResidueIdException(MESSAGE_INVALID);
    }
}
