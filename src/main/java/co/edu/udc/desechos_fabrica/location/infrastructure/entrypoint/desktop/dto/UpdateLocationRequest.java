package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto;

public record UpdateLocationRequest(
        String name,
        String address,
        String country,
        String state,
        String city,
        Double latitude,
        Double longitude,
        String status
) {
}
