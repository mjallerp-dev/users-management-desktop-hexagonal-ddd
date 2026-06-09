package co.edu.udc.desechos_fabrica.residue.domain.valueobject;

import co.edu.udc.desechos_fabrica.residue.domain.exception.InvalidChemicalComponentException;

public record ChemicalComponent(Integer id, String name) {

    public ChemicalComponent {
        if (id == null || id <= 0) {
            throw InvalidChemicalComponentException.becauseIdIsInvalid();
        }
        if (name == null || name.isBlank()) {
            throw InvalidChemicalComponentException.becauseNameIsEmpty();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
