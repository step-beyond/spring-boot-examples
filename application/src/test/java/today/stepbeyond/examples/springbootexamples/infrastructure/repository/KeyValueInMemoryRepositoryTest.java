package today.stepbeyond.examples.springbootexamples.infrastructure.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.model.Pet;
import today.stepbeyond.examples.springbootexamples.infrastructure.repository.impl.KeyValueInMemoryRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class KeyValueInMemoryRepositoryTest {

    private KeyValueInMemoryRepository objectUnderTest;

    @BeforeEach
    void flush() {
        objectUnderTest = new KeyValueInMemoryRepository();
    }

    @Test
    void shouldNotCreateAlreadyExistingDog() {
        // GIVEN
        var pet = new Dog().setName("Hasso");
        var dog = objectUnderTest.create(pet);

        // WHEN, THEN
        assertThatCode(() -> objectUnderTest.create(dog))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldStoreAndFindDog() {
        // GIVEN
        var pet = new Dog().setName("Hasso");

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
        assertThat(foundDog)
                .isEmpty();
    }
}