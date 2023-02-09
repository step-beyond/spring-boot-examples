package today.stepbeyond.examples.springbootexamples.infrastructure.gateways.events.model;

import java.util.Date;

public interface PetEvent {

  /**
   * @return the type of the event
   */
  String getType();

  /**
   * @return the timestampt, when the event is omitted
   */
  Date timestamp();
}
