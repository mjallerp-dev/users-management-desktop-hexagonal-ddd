package co.edu.udc.desechos_fabrica.generated_residue.infrastructure.adapter.persistence.entity;

import java.time.LocalDateTime;

public record GeneratedResidueEntity(
        Long id,
        Long residueId,
        String code,
        Double generatedQuantity,
        LocalDateTime generationDate) {


}
