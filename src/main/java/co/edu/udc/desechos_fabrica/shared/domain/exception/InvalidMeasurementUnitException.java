package co.edu.udc.desechos_fabrica.shared.domain.exception;

public class InvalidMeasurementUnitException extends RuntimeException {

    private static final String MESSAGE_INVALID_ID = "The measurement unit ID '%d' is invalid or does not exist.";

    private InvalidMeasurementUnitException(final String message) {
        super(message);
    }

    public static InvalidMeasurementUnitException becauseIdDoesNotExist(final int id) {
        return new InvalidMeasurementUnitException(String.format(MESSAGE_INVALID_ID, id));
    }
}