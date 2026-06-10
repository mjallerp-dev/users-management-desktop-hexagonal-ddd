package co.edu.udc.desechos_fabrica.residue.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;

public record GetResidueByTypeQuery(
        @NotBlank String type)
{}
