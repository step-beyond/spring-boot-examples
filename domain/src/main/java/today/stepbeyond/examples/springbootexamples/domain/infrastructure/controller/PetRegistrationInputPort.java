package today.stepbeyond.examples.springbootexamples.domain.infrastructure.controller;

import java.util.UUID;

public interface PetRegistrationInputPort {

  void deregisterPet(UUID petId);
}
