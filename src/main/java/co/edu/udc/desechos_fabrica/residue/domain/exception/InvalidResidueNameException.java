package co.edu.udc.desechos_fabrica.residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.DomainException;

public class InvalidResidueNameException extends DomainException {

    private static final String MESSAGE_EMPTY = "The residue name must not be empty.";
    private static final String MESSAGE_TOO_LONG = "The residue name must not exceed %d characters.";

    private InvalidResidueNameException(final String message) {
        super(message);
    }

    public static InvalidResidueNameException becauseValueIsEmpty() {
        return new InvalidResidueNameException(MESSAGE_EMPTY);
    }

    public static InvalidResidueNameException becauseLengthIsTooShort(final int maximumLength) {
        return new InvalidResidueNameException(String.format(MESSAGE_TOO_LONG, maximumLength));
    }
}
