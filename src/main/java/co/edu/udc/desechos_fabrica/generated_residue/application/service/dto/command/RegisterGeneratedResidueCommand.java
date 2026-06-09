package co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.command;

public record RegisterGeneratedResidueCommand(
        String code,
        Long residueId,
        Double residueQuantity,
        String measurementUnit,
        Long enterpriseId
) {}