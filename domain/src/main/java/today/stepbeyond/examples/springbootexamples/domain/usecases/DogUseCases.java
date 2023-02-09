package today.stepbeyond.examples.springbootexamples.domain.usecases;

import java.time.Instant;
import java.util.Date;
import org.springframework.stereotype.Service;
import today.stepbeyond.examples.springbootexamples.domain.core.DogDomainService;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.PetStoreClient;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.PetEventPublisher;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetIsBornEvent;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetRegisteredEvent;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.repository.PetRepository;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Dog;

@Service
public class DogUseCases implements BirthOfADog {
  private final DogDomainService dogDomainService;

  private final PetRepository petRepository;

  private final PetStoreClient petStoreClient;

  private final PetEventPublisher petEventPublisher;

  public DogUseCases(
      DogDomainService dogDomainService,
      PetRepository petRepository,
      PetStoreClient petStoreClient,
      PetEventPublisher petEventPublisher) {
    this.dogDomainService = dogDomainService;
    this.petRepository = petRepository;
    this.petStoreClient = petStoreClient;
    this.petEventPublisher = petEventPublisher;
  }

  @Override
  public Dog birthOfADog(String name) {
    var newBornDog = dogDomainService.createDog(name);
    petEventPublisher.publishPetIsBorn(
        new PetIsBornEvent(newBornDog.getId(), newBornDog.getName(), Date.from(Instant.now())));
    newBornDog = (Dog) petRepository.create(newBornDog);
    if (petStoreClient.registerPet(newBornDog)) {
      var registeredDog = dogDomainService.registerDog(newBornDog);
      petRepository.update(registeredDog);
      petEventPublisher.publishPetIsRegistered(
          new PetRegisteredEvent(
              newBornDog.getId(), newBornDog.getName(), Date.from(Instant.now())));
      return registeredDog;
    }
    return newBornDog;
  }
}
