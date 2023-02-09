package today.stepbeyond.examples.springbootexamples.application.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Cat;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Pet;
import today.stepbeyond.examples.springbootexamples.domain.core.model.PetType;

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
  void shouldUpdateAPet() {
    // Given
    var newCat = objectUnderTest.create(new Cat(UUID.randomUUID(), "Chippie", true));
    // WHEN
    objectUnderTest.update(new Cat(newCat.getId(), "Werni", true));

    // THEN
    var updatedCat = objectUnderTest.find(newCat.getId());

    assertThat(updatedCat)
        .isPresent()
        .get()
        .extracting(Pet::getId, Pet::getName, Pet::getType, Pet::isRegistered)
        .containsExactly(newCat.getId(), "Werni", PetType.CAT, true);
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
