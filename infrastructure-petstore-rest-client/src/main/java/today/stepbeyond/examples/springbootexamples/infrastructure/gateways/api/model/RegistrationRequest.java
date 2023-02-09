package today.stepbeyond.examples.springbootexamples.infrastructure.gateways.api.model;

import java.util.UUID;

public class RegistrationRequest {

  private UUID petId;

  public UUID getPetId() {
    return petId;
  }

  public RegistrationRequest setPetId(UUID petId) {
    this.petId = petId;
    return this;
  }
}
