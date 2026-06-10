package co.edu.udc.desechos_fabrica.residue.infrastructure.adapter.persistence.entity;

import co.edu.udc.desechos_fabrica.residue.domain.enums.ResidueType;
import co.edu.udc.desechos_fabrica.shared.domain.enums.MeasurementUnit;

public class ResidueEntity {
    private Long id;
    private String name;
    private Integer maxTransportTime;
    private Double maxTransportQuantity;
    private ResidueType residueType;
    private MeasurementUnit measurementUnit;
}
