package co.edu.udc.desechos_fabrica.residue.infrastructure.adapter.persistence.entity;

import co.edu.udc.desechos_fabrica.residue.domain.enums.ResidueType;
import co.edu.udc.desechos_fabrica.shared.domain.enums.MeasurementUnit;

public record ResidueEntity(
        Long id,
        String name,
        Integer maxTransportTime,
        Double maxTransportQuantity,
        ResidueType residueType,
        MeasurementUnit measurementUnit
) {

}
