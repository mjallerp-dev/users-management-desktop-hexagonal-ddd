package co.edu.udc.desechos_fabrica.residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class InvalidResidueTypeException extends DomainException {

    private static final String MESSAGE_NULL = "The residue type ID must not be null.";

    private InvalidResidueTypeException(final String message) {
        super(message);
    }

    public static InvalidResidueTypeException becauseValueIsNull() {
        return new InvalidResidueTypeException(MESSAGE_NULL);
    }
}