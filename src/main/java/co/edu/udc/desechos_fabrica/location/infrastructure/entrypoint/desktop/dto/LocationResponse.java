package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto;

public record LocationResponse(
        Long id,
        String name,
        String address,
        Long enterpriseId,
        String country,
        String state,
        String city,
        Double latitude,
        Double longitude,
        String status
) {
}
