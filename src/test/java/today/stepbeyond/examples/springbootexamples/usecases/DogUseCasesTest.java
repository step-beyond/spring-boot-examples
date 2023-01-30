package today.stepbeyond.examples.springbootexamples.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import today.stepbeyond.examples.springbootexamples.domain.DogService;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.model.PetType;
import today.stepbeyond.examples.springbootexamples.infrastructure.repository.PetRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DogUseCasesTest {

    @Mock
    DogService dogService;

    @Mock
    PetRepository petRepository;

    @InjectMocks
    DogUseCases objectUnderTest;

    @Test
    void birthOfDog() {
        // GIVEN
        var dogName = "Hasso";
        var id = UUID.fromString("DF08B211-4D02-4FC1-910C-5803F1E42BD6");
        var dog = new Dog().setName(dogName).setId(id);
        when(dogService.createDog(dogName))
                .thenReturn(dog);
        when(petRepository.create(dog))
                .thenReturn(id);

        // WHEN
        var newDog = objectUnderTest.birthOfADog(dogName);

        // THEN
        assertThat(newDog)
                .extracting(Dog::getName, Dog::getType)
                .contains("Hasso", PetType.DOG);
        verify(dogService, times(1)).createDog(any());
        verify(petRepository, times(1)).create(any());
    }
}