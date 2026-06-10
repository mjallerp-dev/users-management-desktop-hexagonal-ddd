package co.edu.udc.desechos_fabrica.residue.application.service.dto.query;

import jakarta.validation.constraints.NotNull;

public record GetResidueByIdQuery(
        @NotNull Long id)
{}
