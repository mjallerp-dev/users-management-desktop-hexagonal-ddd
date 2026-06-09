package co.edu.udc.desechos_fabrica.enterprise.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;

public record GetEnterpriseByNitQuery(
        @NotBlank(message = "nit must not be blank")
        String nit
) {
}
