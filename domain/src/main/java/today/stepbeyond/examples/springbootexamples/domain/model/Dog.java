package today.stepbeyond.examples.springbootexamples.domain.model;

import java.util.UUID;

public class Dog implements Pet {
    private String name;
    private UUID id;

    private boolean registered;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public PetType getType() {
        return PetType.DOG;
    }

    public Dog setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Dog setId(UUID id) {
        this.id = id;
        return this;
    }

    public boolean isRegistered() {
        return registered;
    }

    public Dog setRegistered(boolean registered) {
        this.registered = registered;
        return this;
    }
}
