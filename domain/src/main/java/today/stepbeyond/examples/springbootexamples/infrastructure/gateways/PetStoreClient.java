package today.stepbeyond.examples.springbootexamples.infrastructure.gateways;

import today.stepbeyond.examples.springbootexamples.domain.model.Pet;

public interface PetStoreClient {

    boolean registerPet(Pet pet);
}