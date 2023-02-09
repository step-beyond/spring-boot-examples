package today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events;

import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetIsBornEvent;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetRegisteredEvent;

public interface PetEventPublisher {

  /**
   * Announce that a new pet is born
   *
   * @param petIsBornEvent - the newborn pet event
   */
  void publishPetIsBorn(PetIsBornEvent petIsBornEvent);

  /**
   * Announce that a pet is registered at pet store
   *
   * @param petRegisteredEvent - the registered pet event
   */
  void publishPetIsRegistered(PetRegisteredEvent petRegisteredEvent);
}
