package co.edu.udc.desechos_fabrica.location.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class LocationAlreadyExistsException extends DomainException {

    private static final String MESSAGE = "Location with id '%s' already exists.";

    public LocationAlreadyExistsException(String message) {
        super(message);
    }

    public static LocationAlreadyExistsException becauseIdAlreadyExists(String id) {
        return new LocationAlreadyExistsException(String.format(MESSAGE, id));
    }
}
