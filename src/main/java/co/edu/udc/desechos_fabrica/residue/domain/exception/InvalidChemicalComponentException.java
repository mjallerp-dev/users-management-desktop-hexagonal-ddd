package co.edu.udc.desechos_fabrica.residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class InvalidChemicalComponentException extends DomainException {

    private InvalidChemicalComponentException(final String message) {
        super(message);
    }

    public static InvalidChemicalComponentException becauseIdIsInvalid() {
        return new InvalidChemicalComponentException("The chemical component ID must be a positive number greater than zero.");
    }

    public static InvalidChemicalComponentException becauseNameIsEmpty() {
        return new InvalidChemicalComponentException("The chemical component name must not be null or empty.");
    }
}