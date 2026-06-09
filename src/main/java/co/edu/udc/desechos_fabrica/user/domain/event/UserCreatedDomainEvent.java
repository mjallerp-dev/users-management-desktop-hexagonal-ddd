package co.edu.udc.desechos_fabrica.user.domain.event;

import co.edu.udc.desechos_fabrica.shared.domain.event.DomainEvent;
import co.edu.udc.desechos_fabrica.user.domain.model.UserModel;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public final class UserCreatedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "user.created";

  private final UserModel user;

  public UserCreatedDomainEvent(final UserModel user) {
    super(EVENT_NAME);
    this.user = user;
  }

  @Override
  public Map<String, Object> payload() {
    final Map<String, Object> map = new HashMap<>();

    map.put("id", user.getId() != null ? String.valueOf(user.getId()) : null);
    map.put("enterpriseId", user.getEnterpriseId() != null ? String.valueOf(user.getEnterpriseId().value()) : null);
    map.put("firstName", user.getFirstName().value());
    map.put("lastName", user.getLastName().value());
    map.put("email", user.getEmail().value());
    map.put("role", user.getRole().name());
    map.put("status", user.getStatus().name());

    return map;
  }
}
