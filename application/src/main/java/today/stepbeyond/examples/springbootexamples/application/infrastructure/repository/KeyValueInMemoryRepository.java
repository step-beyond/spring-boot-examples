package today.stepbeyond.examples.springbootexamples.application.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import today.stepbeyond.examples.springbootexamples.domain.model.Cat;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.model.Pet;
import today.stepbeyond.examples.springbootexamples.domain.model.PetType;
import today.stepbeyond.examples.springbootexamples.infrastructure.repository.PetRepository;

@Repository
public class KeyValueInMemoryRepository implements PetRepository {

  private final Map<UUID, Pet> store = new HashMap<>();

  @Override
  public Optional<Pet> find(UUID id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Pet create(Pet pet) {
    if (store.containsKey(pet.getId())) {
      throw new IllegalArgumentException("Pet already exists");
    } else {
      var newPet = createNewPet(pet);
      store.put(newPet.getId(), newPet);
      return newPet;
    }
  }

  @Override
  public Pet update(Pet pet) {
    if (pet.getId() == null || !store.containsKey(pet.getId())) {
      throw new IllegalArgumentException("Pet cannot be updated");
    }
    return store.put(pet.getId(), pet);
  }

  private static Pet createNewPet(Pet pet) {
    if (pet.isOfType(PetType.DOG)) {
      return new Dog(UUID.randomUUID(), pet.getName(), pet.isRegistered());
    } else if (pet.isOfType(PetType.CAT)) {
      return new Cat(UUID.randomUUID(), pet.getName(), pet.isRegistered());
    } else {
      throw new IllegalArgumentException("PetType is unknown.");
    }
  }
}
