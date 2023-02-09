package today.stepbeyond.examples.springbootexamples.domain.model;

import java.util.UUID;

public record Dog(UUID id, String name, boolean registered) implements Pet {
    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public PetType getType() {
        return PetType.DOG;
    }
}
