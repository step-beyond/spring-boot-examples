package today.stepbeyond.examples.springbootexamples.infrastructure.repository;

import today.stepbeyond.examples.springbootexamples.domain.model.Pet;

import java.util.Optional;
import java.util.UUID;

public interface PetRepository {

    Optional<Pet> find(UUID id);

    UUID create(Pet newBornDog);
}
