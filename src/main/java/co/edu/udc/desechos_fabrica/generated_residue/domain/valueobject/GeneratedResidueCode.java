package co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject;


import co.edu.udc.desechos_fabrica.generated_residue.domain.exception.InvalidGeneratedResidueCodeException;

public record GeneratedResidueCode(String value) {

    private static final int MAX_LENGTH = 50;

    public GeneratedResidueCode {
        if (value == null || value.isBlank()) {
            throw InvalidGeneratedResidueCodeException.becauseValueIsEmpty();
        }
        if (value.length() > MAX_LENGTH) {
            throw InvalidGeneratedResidueCodeException.becauseLengthIsTooLong(MAX_LENGTH);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
