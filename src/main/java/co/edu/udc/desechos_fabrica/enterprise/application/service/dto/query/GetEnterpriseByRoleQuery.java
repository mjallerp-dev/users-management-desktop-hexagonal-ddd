package co.edu.udc.desechos_fabrica.enterprise.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;

public record GetEnterpriseByRoleQuery(
        @NotBlank(message = "role must not be blank")
        String role
) {
}
