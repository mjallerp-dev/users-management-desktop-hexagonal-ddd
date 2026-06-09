package co.edu.udc.desechos_fabrica.generated_residue.domain.exception;

public class InvalidGeneratedResidueCodeException extends RuntimeException {

    private static final String MESSAGE_EMPTY = "The generated residue unique code must not be empty.";
    private static final String MESSAGE_TOO_LONG = "The generated residue code must not exceed %d characters.";

    private InvalidGeneratedResidueCodeException(final String message) {
        super(message);
    }

    public static InvalidGeneratedResidueCodeException becauseValueIsEmpty() {
        return new InvalidGeneratedResidueCodeException(MESSAGE_EMPTY);
    }

    public static InvalidGeneratedResidueCodeException becauseLengthIsTooLong(final int maximumLength) {
        return new InvalidGeneratedResidueCodeException(String.format(MESSAGE_TOO_LONG, maximumLength));
    }
}