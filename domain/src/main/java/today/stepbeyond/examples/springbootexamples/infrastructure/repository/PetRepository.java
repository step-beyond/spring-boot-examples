package today.stepbeyond.examples.springbootexamples.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;
import today.stepbeyond.examples.springbootexamples.domain.model.Pet;

public interface PetRepository {

    Optional<Pet> find(UUID id);

    UUID create(Pet newBornDog);
}
