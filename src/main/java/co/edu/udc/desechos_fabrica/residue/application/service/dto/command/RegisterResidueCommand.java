package co.edu.udc.desechos_fabrica.residue.application.service.dto.command;

import java.util.List;

public record RegisterResidueCommand(
        String name,
        Integer maxTransportTime,
        Double maxTransportQuantity,
        String residueType,
        String measurementUnit,
        List<Long> chemicalComponentIds
) {}
