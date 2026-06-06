package co.edu.udc.desechos_fabrica.location.application.service.dto.query;

import jakarta.validation.constraints.NotNull;

public record GetLocationByIdQuery(@NotNull(message = "id must not be null") Long id){
}
