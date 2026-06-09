package co.edu.udc.desechos_fabrica.generated_residue.domain.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class GeneratedResidueAlreadyExistsException extends DomainException {

    private static final String MESSAGE_ALREADY_EXISTS = "A generated residue with the code '%s' already exists.";

    public GeneratedResidueAlreadyExistsException(String message) {
        super(message);
    }

    public static GeneratedResidueAlreadyExistsException withCodeAndEnterprise(String code) {
        return new GeneratedResidueAlreadyExistsException(
                String.format(MESSAGE_ALREADY_EXISTS, code)
        );
    }
}
