package co.edu.udc.desechos_fabrica.location.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.DomainException;

public final class LocationNotFoundException extends DomainException {

    private static final String MESSAGE = "Location with id '%d' was not found.";

    public LocationNotFoundException(String message) {
        super(message);
    }

    public static LocationNotFoundException becauseIdWasNotFound(Long id) {
        return new LocationNotFoundException(String.format(MESSAGE, id));
    }
}
