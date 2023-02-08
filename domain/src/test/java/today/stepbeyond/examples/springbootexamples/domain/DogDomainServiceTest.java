package today.stepbeyond.examples.springbootexamples.domain;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;

import static org.assertj.core.api.Assertions.assertThat;

class DogDomainServiceTest {

    DogDomainService objectUnderTest = new DogDomainService();

    @Test
    void shouldCreateDog() {
        // GIVEN
        var name = "Hasso";

        // WHEN
        var result = objectUnderTest.createDog(name);

        // THEN
        assertThat(result)
                .extracting(Dog::getName, Dog::isRegistered, Dog::getId)
                .contains("Hasso", false, null);
    }

    @Test
    void shouldRegisterDog() {
        // GIVEN
        var dog = new Dog()
                .setName("Hasso")
                .setId(UUID.fromString("9AD0236E-43A7-40C7-9FF1-54FEE4D23D84"))
                .setRegistered(false);

        // WHEN
        var result = objectUnderTest.registerDog(dog);

        // THEN
        assertThat(result)
                .extracting(Dog::getName, Dog::isRegistered, Dog::getId)
                .contains("Hasso", true, UUID.fromString("9AD0236E-43A7-40C7-9FF1-54FEE4D23D84"));
    }
}