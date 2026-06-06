package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.exception;

import co.edu.udc.desechos_fabrica.shared.DomainException;

public class PersistenceException extends DomainException {

    private static final String MESSAGE_SAVE = "Failed to save location with ID: '%s'.";
    private static final String MESSAGE_UPDATE = "Failed to update location with ID: '%s'.";
    private static final String MESSAGE_FIND = "Failed to find location with ID: '%s'.";
    private static final String MESSAGE_ALL = "Failed to retrieve all locations.";
    private static final String MESSAGE_CONNECTION = "Could not establish database connection.";

    public PersistenceException(String message) {
        super(message);
    }
}
