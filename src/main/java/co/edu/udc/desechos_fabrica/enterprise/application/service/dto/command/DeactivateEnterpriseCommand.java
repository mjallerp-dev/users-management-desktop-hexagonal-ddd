package co.edu.udc.desechos_fabrica.enterprise.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeactivateEnterpriseCommand(
        @NotBlank(message = "enterprise nit must not be blank")
        @Size(min = 5, message = "enterprise nit must contain only digits between 9 and 12 characters")
        String nit
) {
}
