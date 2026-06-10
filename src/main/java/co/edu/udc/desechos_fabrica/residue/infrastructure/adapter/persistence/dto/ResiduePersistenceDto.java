package co.edu.udc.desechos_fabrica.residue.infrastructure.adapter.persistence.dto;

import co.edu.udc.desechos_fabrica.residue.domain.enums.ResidueType;
import co.edu.udc.desechos_fabrica.shared.domain.enums.MeasurementUnit;

import java.util.List;

public record ResiduePersistenceDto(
    Long id,
    String name,
    Double maxTransportQuantity,
    Integer maxTransportTime,
    ResidueType residueType,
    MeasurementUnit measurementUnit,
    List<String> chemicalComponents)
{
}
