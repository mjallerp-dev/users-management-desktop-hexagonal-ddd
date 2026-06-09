package co.edu.udc.desechos_fabrica.shared.domain.enums;

import co.edu.udc.desechos_fabrica.shared.domain.exception.InvalidMeasurementUnitException;
import lombok.Getter;
import java.util.Arrays;

@Getter
public enum MeasurementUnit {

    KILOGRAM(1, "Kg", "Kilogram"),
    LITER(2, "L", "Liter"),
    CUBIC_METER(3, "m³", "Cubic Meter"),
    TON(4, "Ton", "Metric Ton");

    private final int id;
    private final String symbol;
    private final String name;

    MeasurementUnit(final int id, final String symbol, final String name) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
    }

    public static MeasurementUnit fromId(final int id) {
        return Arrays.stream(values())
                .filter(unit -> unit.id == id)
                .findFirst()
                .orElseThrow(() -> InvalidMeasurementUnitException.becauseIdDoesNotExist(id));
    }
}
