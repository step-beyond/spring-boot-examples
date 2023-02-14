package today.stepbeyond.examples.springbootexamples.domain.usecases;

import java.util.UUID;
import org.springframework.stereotype.Service;
import today.stepbeyond.examples.springbootexamples.domain.core.DogDomainService;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.controller.PetRegistrationInputPort;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.repository.PetRepository;

@Service
public class PetRegistrationUseCases implements PetRegistrationInputPort {

  private final DogDomainService dogDomainService;

  private final PetRepository petRepository;

  public PetRegistrationUseCases(DogDomainService dogDomainService, PetRepository petRepository) {
    this.dogDomainService = dogDomainService;
    this.petRepository = petRepository;
  }

  @Override
  public void deregisterPet(UUID petId) {
    petRepository
        .find(petId)
        // TODO: ignore Cats for the moment
        .filter(Dog.class::isInstance)
        .map(Dog.class::cast)
        .map(dogDomainService::deregisterDog)
        .ifPresent(petRepository::update);
  }
}
