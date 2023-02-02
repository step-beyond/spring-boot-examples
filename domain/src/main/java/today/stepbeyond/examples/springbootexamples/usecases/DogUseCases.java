package today.stepbeyond.examples.springbootexamples.usecases;

import org.springframework.stereotype.Service;
import today.stepbeyond.examples.springbootexamples.domain.DogDomainService;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;
import today.stepbeyond.examples.springbootexamples.infrastructure.repository.PetRepository;

@Service
public class DogUseCases implements BirthOfADog {
    private final DogDomainService dogDomainService;
    private final PetRepository petRepository;

    public DogUseCases(DogDomainService dogDomainService, PetRepository petRepository) {
        this.dogDomainService = dogDomainService;
        this.petRepository = petRepository;
    }

    @Override
    public Dog birthOfADog(String name) {
        var newBornDog = dogDomainService.createDog(name);
        var id = petRepository.create(newBornDog);
        return newBornDog.setId(id);
    }
}
