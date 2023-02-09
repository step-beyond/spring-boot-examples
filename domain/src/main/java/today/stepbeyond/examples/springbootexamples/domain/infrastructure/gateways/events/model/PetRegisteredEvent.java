package today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model;

import java.util.Date;
import java.util.UUID;

public record PetRegisteredEvent(UUID id, String name, Date timestamp) implements PetEvent {

  private static final String EVENT_TYPE = "REGISTERED";

  @Override
  public String getType() {
    return EVENT_TYPE;
  }
}
