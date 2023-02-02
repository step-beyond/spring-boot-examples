package today.stepbeyond.examples.springbootexamples.infrastructure.repository.impl;

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
    public UUID create(Pet pet) {
        if (store.containsKey(pet.getId())) {
            throw new IllegalArgumentException("Pet already exists");
        } else {
            var newPet = createNewPet(pet);
            store.put(newPet.getId(), newPet);
            return newPet.getId();
        }
    }

    private static Pet createNewPet(Pet pet) {
        if (pet.isOfType(PetType.DOG)) {
            return new Dog().setId(UUID.randomUUID()).setName(pet.getName());
        } else if (pet.isOfType(PetType.CAT)) {
            return new Cat().setId(UUID.randomUUID()).setName(pet.getName());
        } else {
            throw new IllegalArgumentException("PetType is unknown.");
        }
    }
}
