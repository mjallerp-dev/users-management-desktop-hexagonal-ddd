package co.edu.udc.desechos_fabrica.enterprise.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateEnterpriseCommand(
        @NotBlank(message = "enterprise name must not be blank")
        @Size(min = 3, message = "enterprise name must have at least 3 characters")
        String name,
        @NotBlank(message = "enterprise nit must not be blank")
        @Size(min = 5, message = "enterprise nit must contain only digits between 9 and 12 characters")
        String nit,
        @NotBlank(message = "enterprise role must not be blank")
        String role
) {
}
