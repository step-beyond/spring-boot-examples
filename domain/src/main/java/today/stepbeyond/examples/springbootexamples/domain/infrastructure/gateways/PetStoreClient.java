package today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways;

import today.stepbeyond.examples.springbootexamples.domain.core.model.Pet;

public interface PetStoreClient {

  /**
   * Register a pet at the petstore
   *
   * @param pet pet to register
   * @return false, if the registration failed.
   */
  boolean registerPet(Pet pet);
}
