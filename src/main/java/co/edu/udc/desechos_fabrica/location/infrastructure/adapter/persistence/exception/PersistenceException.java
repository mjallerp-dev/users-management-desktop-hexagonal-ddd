package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class PersistenceException extends DomainException {

    private static final String MESSAGE_SAVE = "Failed to save location with ID: '%s'.";
    private static final String MESSAGE_UPDATE = "Failed to update location with ID: '%s'.";
    private static final String MESSAGE_FIND = "Failed to find location with ID: '%s'.";
    private static final String MESSAGE_ALL = "Failed to retrieve all locations.";
    private static final String MESSAGE_CONNECTION = "Could not establish database connection.";

    public PersistenceException(String message, final Throwable cause) {
        super(message, cause);
    }

    public static PersistenceException becauseSaveFailed(final String locationId, final Throwable cause) {
        return new PersistenceException(String.format(MESSAGE_SAVE, locationId), cause);
    }

    public static PersistenceException becauseUpdateFailed(final String locationId, final Throwable cause) {
        return new PersistenceException(String.format(MESSAGE_UPDATE, locationId), cause);
    }

    public static PersistenceException becauseFindByIdFailed(final String locationId, final Throwable cause) {
        return new PersistenceException(String.format(MESSAGE_FIND, locationId), cause);
    }

    public static PersistenceException becauseFindAllFailed(final Throwable cause) {
        return new PersistenceException(MESSAGE_ALL, cause);
    }

    public static PersistenceException becauseConnectionFailed(final Throwable cause) {
        return new PersistenceException(MESSAGE_CONNECTION, cause);
    }
}
