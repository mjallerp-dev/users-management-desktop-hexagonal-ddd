package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.entity;

public record LocationEntity (
    Long id,
    String name,
    String address,
    String country,
    String state,
    String city,
    Long enterpriseId,
    String coordinate,
    String status,
    String createdAt,
    String updatedAt) {
}
