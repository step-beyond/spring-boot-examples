package today.stepbeyond.examples.springbootexamples.domain.core.model;

import java.util.UUID;

public interface Pet {

  UUID getId();

  String getName();

  PetType getType();

  boolean isRegistered();

  default boolean isOfType(PetType type) {
    return getType() == type;
  }
}
