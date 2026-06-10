package co.edu.udc.desechos_fabrica.residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class ResidueNotFoundException extends DomainException {

    private static final String MESSAGE_NOT_FOUND = "Residue was not found in the system.";

    public ResidueNotFoundException(String message) {
        super(message);
    }

    public static ResidueNotFoundException becauseIdWasNotFound() {
        return new ResidueNotFoundException(MESSAGE_NOT_FOUND);
    }
}