package co.edu.udc.desechos_fabrica.user.application.service.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DeleteUserCommand(
    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    String email
) {

}
