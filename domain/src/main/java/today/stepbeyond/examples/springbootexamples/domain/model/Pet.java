package today.stepbeyond.examples.springbootexamples.domain.model;

import java.util.UUID;

public interface Pet {

    UUID getId();

    String getName();

    PetType getType();

    default boolean isOfType(PetType type) {
        return getType() == type;
    }
}
