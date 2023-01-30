package today.stepbeyond.examples.springbootexamples.domain.model;

import java.util.UUID;

public class Cat implements Pet {

    private String name;

    private UUID id;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PetType getType() {
        return PetType.CAT;
    }

    public Cat setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Cat setId(UUID id) {
        this.id = id;
        return this;
    }
}
