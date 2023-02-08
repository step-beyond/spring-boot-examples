package today.stepbeyond.examples.springbootexamples.usecases;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import today.stepbeyond.examples.springbootexamples.domain.DogDomainService;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.model.PetType;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.PetStoreClient;
import today.stepbeyond.examples.springbootexamples.infrastructure.repository.PetRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DogUseCasesTest {

    @Spy
    DogDomainService dogDomainService;

    @Mock
    PetRepository petRepository;

    @Mock
    PetStoreClient petStoreClient;

    @InjectMocks
    DogUseCases objectUnderTest;

    @Test
    void birthOfDog() {
        // GIVEN
        var dogName = "Hasso";
        var id = UUID.fromString("DF08B211-4D02-4FC1-910C-5803F1E42BD6");
        var dog = new Dog().setName(dogName).setId(id);
        when(petStoreClient.registerPet(any()))
                .thenReturn(true);
        when(petRepository.create(any()))
                .thenReturn(new Dog()
                        .setName(dogName)
                        .setRegistered(false)
                        .setId(id));
        when(petRepository.update(any()))
                .thenReturn(new Dog()
                        .setName(dogName)
                        .setRegistered(true)
                        .setId(id));

        // WHEN
        var newDog = objectUnderTest.birthOfADog(dogName);

        // THEN
        assertThat(newDog)
                .extracting(Dog::getName, Dog::getType, Dog::isRegistered)
                .contains("Hasso", PetType.DOG, true);
        verify(dogDomainService, times(1)).createDog(any());
        verify(petRepository, times(1)).create(any());
        verify(petRepository, times(1)).update(any());
        verify(petStoreClient, times(1)).registerPet(any());
    }
}