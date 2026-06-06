package co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.dto;

public record UpdateLocationRequest(
        Long id,
        String newName,
        String newAddress,
        String newCountry,
        String newState,
        String newCity,
        Double newLatitude,
        Double newLongitude
) {
}
