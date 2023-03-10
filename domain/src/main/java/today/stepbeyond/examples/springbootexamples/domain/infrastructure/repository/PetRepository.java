package today.stepbeyond.examples.springbootexamples.domain.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Pet;

public interface PetRepository {

  Optional<Pet> find(UUID id);

  Pet create(Pet dog);

  Pet update(Pet dog);
}
