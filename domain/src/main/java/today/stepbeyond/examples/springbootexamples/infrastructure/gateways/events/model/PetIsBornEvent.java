package today.stepbeyond.examples.springbootexamples.infrastructure.gateways.events.model;

import java.util.Date;
import java.util.UUID;

public record PetIsBornEvent(UUID id, String name, Date timestamp) implements PetEvent {

  private static final String EVENT_TYPE = "BORN";

  @Override
  public String getType() {
    return EVENT_TYPE;
  }
}
