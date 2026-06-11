package co.edu.udc.desechos_fabrica.generated_residue.infrastructure.adapter.persistence.exception;

import co.edu.udc.desechos_fabrica.shared.domain.exception.DomainException;

public class GeneratedResiduePersistenceException extends DomainException {

    private static final String MESSAGE_SAVE = "Failed to save Generated Residue.";
    private static final String MESSAGE_FIND_CODE = "Failed to find enterprise with code: '%s'.";
    private static final String MESSAGE_FIND_ENTERPRISE_AND_DATE = "Failed to to find enterprise or date.";
    private static final String MESSAGE_CONNECTION = "Could not establish database connection.";

    public GeneratedResiduePersistenceException(String message, final Throwable cause) {
        super(message, cause);
    }

    public static GeneratedResiduePersistenceException becauseSaveFailed(final Throwable cause) {
        return new GeneratedResiduePersistenceException(String.format(MESSAGE_SAVE), cause);
    }

    public static GeneratedResiduePersistenceException becauseFindByCodeFailed(final String code, final Throwable cause) {
        return new GeneratedResiduePersistenceException(String.format(MESSAGE_FIND_CODE, code), cause);
    }

    public static GeneratedResiduePersistenceException becauseFinByEnterpriseAndDateFailed(final Throwable cause) {
        return new GeneratedResiduePersistenceException(MESSAGE_FIND_ENTERPRISE_AND_DATE, cause);
    }

    public static GeneratedResiduePersistenceException becauseConnectionFailed(final Throwable cause) {
        return new GeneratedResiduePersistenceException(MESSAGE_CONNECTION, cause);
    }
}

