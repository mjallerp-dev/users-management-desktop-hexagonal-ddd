package co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto;

public record UpdateUserRequest(
    String currentEmail,
    String newFirstName,
    String newLastName,
    String newEmail,
    String password,
    String role,
    String status,
    Long enterpriseId) {}
