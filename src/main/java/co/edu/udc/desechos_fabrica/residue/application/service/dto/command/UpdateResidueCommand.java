package co.edu.udc.desechos_fabrica.residue.application.service.dto.command;

import jakarta.validation.constraints.*;
import java.util.List;

public record UpdateResidueCommand(
        @NotNull(message = "Residue ID must not be null")
        Long id,

        @NotBlank(message = "Residue name must not be blank")
        @Size(min = 3, message = "Residue name must have at least 3 characters")
        String name,

        @NotNull(message = "Residue max transport time must not be null")
        @Positive(message = "Residue max transport time must be greater than zero")
        Integer maxTransportTime,

        @NotNull(message = "Residue max transport quantity must not be null")
        @Positive(message = "Residue max transport quantity must be greater than zero")
        Double maxTransportQuantity,

        @NotBlank(message = "Residue type must not be blank")
        String residueType,

        @NotBlank(message = "Measurement unit must not be blank")
        String measurementUnit,

        @NotEmpty(message = "Chemical components list must not be empty")
        List<Long> chemicalComponentIds
) {}
