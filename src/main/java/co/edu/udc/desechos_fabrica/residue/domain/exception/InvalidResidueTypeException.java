package co.edu.udc.desechos_fabrica.residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.DomainException;

public class InvalidResidueTypeException extends DomainException {

    private static final String MESSAGE_INVALID_ID = "The residue type ID '%d' is invalid or does not exist.";

    private InvalidResidueTypeException(final String message) {
        super(message);
    }

    public static InvalidResidueTypeException becauseIdDoesNotExist(final int id) {
        return new InvalidResidueTypeException(String.format(MESSAGE_INVALID_ID, id));
    }
}