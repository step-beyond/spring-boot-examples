package today.stepbeyond.examples.springbootexamples.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import today.stepbeyond.examples.springbootexamples.application.infrastructure.repository.KeyValueInMemoryRepository;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.model.Pet;

class KeyValueInMemoryRepositoryTest {

  private KeyValueInMemoryRepository objectUnderTest;

  @BeforeEach
  void flush() {
    objectUnderTest = new KeyValueInMemoryRepository();
  }

  @Test
  void shouldNotCreateAlreadyExistingDog() {
    // GIVEN
    var pet = new Dog(null, "Hasso", false);
    var dog = objectUnderTest.create(pet);

    // WHEN, THEN
    assertThatCode(() -> objectUnderTest.create(dog)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void shouldStoreAndFindDog() {
    // GIVEN
    var pet = new Dog(null, "Hasso", false);

    // WHEN
    var dog = objectUnderTest.create(pet);
    var foundDog = objectUnderTest.find(dog.getId());

    // THEN
    assertThat(foundDog)
        .isPresent()
        .containsInstanceOf(Dog.class)
        .map(Pet::getName)
        .contains("Hasso");
  }

  @Test
  void shouldNotFindDog() {
    // GIVEN
    var id = UUID.randomUUID();

    // WHEN
    var foundDog = objectUnderTest.find(id);

    // THEN
    assertThat(foundDog).isEmpty();
  }
}
