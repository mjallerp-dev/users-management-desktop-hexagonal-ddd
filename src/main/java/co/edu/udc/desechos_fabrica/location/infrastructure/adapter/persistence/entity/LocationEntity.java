package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.entity;

public record LocationEntity (
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
