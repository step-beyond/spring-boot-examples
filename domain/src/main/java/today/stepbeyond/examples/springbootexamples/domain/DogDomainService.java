package today.stepbeyond.examples.springbootexamples.domain;

import org.springframework.stereotype.Service;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;

@Service
public class DogDomainService {

    public Dog createDog(String name) {
        return new Dog(null, name, false);
    }

    public Dog registerDog(Dog dog) {
        return new Dog(dog.id(), dog.name(), true);
    }
}
