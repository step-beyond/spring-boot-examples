package today.stepbeyond.examples.springbootexamples.infrastructure.gateways;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:petstore-client-defaults.properties")
public class PetStoreRestConfiguration {}
