package today.stepbeyond.examples.springbootexamples.domain.model;

import java.util.UUID;

public record Cat(UUID id, String name, boolean registered) implements Pet {

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PetType getType() {
        return PetType.CAT;
    }

    @Override
    public boolean isRegistered() {
        return registered;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
