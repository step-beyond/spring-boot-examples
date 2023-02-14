package today.stepbeyond.examples.springbootexamples.domain.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import today.stepbeyond.examples.springbootexamples.domain.core.DogDomainService;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
class PetRegistrationUseCasesTest {

  @Spy DogDomainService domainService;

  @Mock PetRepository repository;

  @InjectMocks PetRegistrationUseCases objectUnderTest;

  @Test
  void shouldDeregisterPet() {
    // GIVEN
    var id = UUID.fromString("D5CBA199-09CA-4184-A421-FBED214C60E1");
    when(repository.find(id)).thenReturn(Optional.of(new Dog(id, "Hasso", true)));

    // WHEN
    objectUnderTest.deregisterPet(id);

    // THEN
    verify(domainService, times(1)).deregisterDog(any());
    verify(repository, times(1)).find(any());
    verify(repository, times(1)).update(any());
  }

  @Test
  void shouldNotDeregisterPet() {
    // GIVEN
    var id = UUID.fromString("D5CBA199-09CA-4184-A421-FBED214C60E1");
    when(repository.find(id)).thenReturn(Optional.empty());

    // WHEN
    objectUnderTest.deregisterPet(id);

    // THEN
    verify(domainService, never()).deregisterDog(any());
    verify(repository, times(1)).find(any());
    verify(repository, never()).update(any());
  }
}
