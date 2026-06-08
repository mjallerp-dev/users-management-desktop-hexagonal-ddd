package co.edu.udc.desechos_fabrica.location.domain.event;

import co.edu.udc.desechos_fabrica.shared.domain.DomainEvent;

import java.util.Map;

public class LocationDeactivatedDomainEvent extends DomainEvent {

    private static final String EVENT_NAME = "location.deactivated";

    private final String locationId;
    private final String enterpriseId;

    public LocationDeactivatedDomainEvent(final String locationId, final String enterpriseId) {
        super(EVENT_NAME);
        this.locationId = locationId;
        this.enterpriseId = enterpriseId;
    }

    @Override
    public Map<String, Object> payload() {
        return java.util.Map.of(
                "locationId", locationId,
                "enterpriseId", enterpriseId
        );
    }
}
