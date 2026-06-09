package co.edu.udc.desechos_fabrica.residue.domain.enums;

import co.edu.udc.desechos_fabrica.residue.domain.exception.InvalidResidueTypeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ResidueType {

    HAZARDOUS(1, "Hazardous \\ Peligroso"),
    RECYCLABLE(2, "Recyclable \\ Aprovechable"),
    ORDINARY(3, "Ordinary \\ Ordinario"),
    BIODEGRADABLE(4, "Biodegradable \\ Biodegradable");

    private final int id;
    private final String name;

    ResidueType(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static ResidueType fromCode(final int id) {
        return Arrays.stream(values())
                .filter(type -> type.id == id)
                .findFirst()
                .orElseThrow(() -> InvalidResidueTypeException.becauseIdDoesNotExist(id));
    }
}