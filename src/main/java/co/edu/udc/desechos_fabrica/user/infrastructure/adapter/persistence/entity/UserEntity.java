package co.edu.udc.desechos_fabrica.user.infrastructure.adapter.persistence.entity;

public record UserEntity(
    Long id,
    String firstName,
    String lastName,
    String email,
    String password,
    String role,
    String status,
    Long enterpriseId,
    String createdAt,
    String updatedAt) {}
