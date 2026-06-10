package co.edu.udc.desechos_fabrica.generated_residue.infrastructure.adapter.persistence.dto;

import java.time.LocalDateTime;

public record GeneratedResiduePersistenceDto(
        Long id,
        Long residueId,
        Long enterpriseId,
        String code,
        Double generatedQuantity,
        LocalDateTime generationDate) {
}
