package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.dto;

public record LocationPersistenceDto (
        Long id,
        String name,
        String address,
        Long enterpriseId,
        String country,
        String state,
        String city,
        String coordinate,
        String status,
        String createdAt,
        String updatedAt) {
}
