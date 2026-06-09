package co.edu.udc.desechos_fabrica.residue.domain.model;

import co.edu.udc.desechos_fabrica.residue.domain.enums.ResidueType;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.*;
import co.edu.udc.desechos_fabrica.shared.domain.enums.MeasurementUnit;
import lombok.Value;
import lombok.With;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Value
@With
public class ResidueModel {

    ResidueId id;
    ResidueName name;
    MaxTransportQuantity maxTransportQuantity;
    MaxTransportTime maxTransportTime;
    ResidueType residueType;
    MeasurementUnit measurementUnit;
    List<ChemicalComponent> chemicalComponents;

    public ResidueModel(
            final ResidueId id,
            final ResidueName name,
            final MaxTransportTime maxTransportTime,
            final MaxTransportQuantity maxTransportQuantity,
            final ResidueType residueType,
            final MeasurementUnit measurementUnit,
            final List<ChemicalComponent> chemicalComponents) {

        this.id = id;
        this.name = Objects.requireNonNull(name, "Residue name must not be null");
        this.maxTransportQuantity = Objects.requireNonNull(maxTransportQuantity, "Max transport quantity must not be null");
        this.maxTransportTime = Objects.requireNonNull(maxTransportTime, "Max transport time must not be null");
        this.residueType = Objects.requireNonNull(residueType, "Residue type must not be null");
        this.measurementUnit = Objects.requireNonNull(measurementUnit, "Measurement unit must not be null");
        this.chemicalComponents = chemicalComponents != null
                ? List.copyOf(chemicalComponents)
                : Collections.emptyList();
    }

    public static ResidueModel create(
            final ResidueName name,
            final MaxTransportTime maxTransportTime,
            final MaxTransportQuantity maxTransportQuantity,
            final ResidueType residueType,
            final MeasurementUnit measurementUnit,
            final List<ChemicalComponent> chemicalComponents) {
        return new ResidueModel(null, name, maxTransportTime, maxTransportQuantity, residueType, measurementUnit, chemicalComponents);
    }

    public ResidueModel updateWith(
            final ResidueName name,
            final MaxTransportTime maxTransportTime,
            final MaxTransportQuantity maxTransportQuantity,
            final ResidueType residueType,
            final MeasurementUnit measurementUnit,
            final List<ChemicalComponent> chemicalComponents) {
        return new ResidueModel(
                this.id,
                name,
                maxTransportTime,
                maxTransportQuantity,
                residueType,
                measurementUnit,
                chemicalComponents
        );
    }
}
