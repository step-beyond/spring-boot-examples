package today.stepbeyond.examples.springbootexamples.usecases;

import org.springframework.stereotype.Service;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.DogService;
import today.stepbeyond.examples.springbootexamples.infrastructure.repository.PetRepository;

@Service
public class DogUseCases implements BirthOfADog {

    private final DogService dogService;
    private final PetRepository petRepository;

    public DogUseCases(DogService dogService, PetRepository petRepository) {
        this.dogService = dogService;
        this.petRepository = petRepository;
    }

    @Override
    public Dog birthOfADog(String name) {
        var newBornDog = dogService.createDog(name);
        var id = petRepository.create(newBornDog);
        return newBornDog.setId(id);
    }
}
