package today.stepbeyond.examples.springbootexamples.usecases;

import org.springframework.stereotype.Service;
import today.stepbeyond.examples.springbootexamples.domain.DogDomainService;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.PetStoreClient;
import today.stepbeyond.examples.springbootexamples.infrastructure.repository.PetRepository;

@Service
public class DogUseCases implements BirthOfADog {
    private final DogDomainService dogDomainService;

    private final PetRepository petRepository;

    private final PetStoreClient petStoreClient;

    public DogUseCases(DogDomainService dogDomainService, PetRepository petRepository, PetStoreClient petStoreClient) {
        this.dogDomainService = dogDomainService;
        this.petRepository = petRepository;
        this.petStoreClient = petStoreClient;
    }

    @Override
    public Dog birthOfADog(String name) {
        var newBornDog = dogDomainService.createDog(name);
        newBornDog = (Dog) petRepository.create(newBornDog);
        if (petStoreClient.registerPet(newBornDog)) {
            var registeredDog = dogDomainService.registerDog(newBornDog);
            petRepository.update(registeredDog);
            return registeredDog;
        }
        return newBornDog;
    }
}
