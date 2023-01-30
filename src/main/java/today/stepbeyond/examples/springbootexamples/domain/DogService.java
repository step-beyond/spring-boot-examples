package today.stepbeyond.examples.springbootexamples.domain;

import org.springframework.stereotype.Service;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;

@Service
public class DogService {

    public Dog createDog(String name) {
        return new Dog().setName(name);
    }
}
