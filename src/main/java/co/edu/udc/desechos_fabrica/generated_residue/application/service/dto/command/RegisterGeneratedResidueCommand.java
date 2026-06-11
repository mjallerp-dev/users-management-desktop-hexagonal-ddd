package co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.command;

public record RegisterGeneratedResidueCommand(
        Long residueId,
        String code,
        Long enterpriseId,
        Double residueQuantity
) {}
