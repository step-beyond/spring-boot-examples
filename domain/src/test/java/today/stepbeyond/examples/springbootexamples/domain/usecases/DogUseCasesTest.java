package today.stepbeyond.examples.springbootexamples.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import today.stepbeyond.examples.springbootexamples.domain.core.DogDomainService;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.PetStoreClient;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.PetEventPublisher;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.repository.PetRepository;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.core.model.PetType;

@ExtendWith(MockitoExtension.class)
class DogUseCasesTest {

  @Spy DogDomainService dogDomainService;

  @Mock PetRepository petRepository;

  @Mock PetStoreClient petStoreClient;

  @Mock PetEventPublisher petEventPublisher;

  @InjectMocks
  DogUseCases objectUnderTest;

  @Test
  void shouldHandle_birthOfDog() {
    // GIVEN
    var dogName = "Hasso";
    var id = UUID.fromString("DF08B211-4D02-4FC1-910C-5803F1E42BD6");
    when(petStoreClient.registerPet(any())).thenReturn(true);
    when(petRepository.create(any())).thenReturn(new Dog(id, dogName, false));
    when(petRepository.update(any())).thenReturn(new Dog(id, dogName, true));

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
    verify(petEventPublisher, times(1)).publishPetIsBorn(any());
    verify(petEventPublisher, times(1)).publishPetIsRegistered(any());
  }

  @Test
  void shouldHandle_BirthOfDog_WhenRegistrationFailed() {
    // GIVEN
    var dogName = "Hasso";
    var id = UUID.fromString("DF08B211-4D02-4FC1-910C-5803F1E42BD6");
    when(petStoreClient.registerPet(any())).thenReturn(false);
    when(petRepository.create(any())).thenReturn(new Dog(id, dogName, false));

    // WHEN
    var newDog = objectUnderTest.birthOfADog(dogName);

    // THEN
    assertThat(newDog)
        .extracting(Dog::getName, Dog::getType, Dog::isRegistered)
        .contains("Hasso", PetType.DOG, false);

    verify(dogDomainService, times(1)).createDog(any());
    verify(petRepository, times(1)).create(any());
    verify(petRepository, never()).update(any());
    verify(petStoreClient, times(1)).registerPet(any());
    verify(petEventPublisher, times(1)).publishPetIsBorn(any());
    verify(petEventPublisher, never()).publishPetIsRegistered(any());
  }
}
