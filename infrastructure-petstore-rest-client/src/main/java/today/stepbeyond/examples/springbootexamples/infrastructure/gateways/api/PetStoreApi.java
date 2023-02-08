package today.stepbeyond.examples.springbootexamples.infrastructure.gateways.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.api.model.RegistrationRequest;

@FeignClient(name = "petStoreApi", url = "${petStoreApi.url}")
public interface PetStoreApi {

    @PostMapping(
            path = "registrations",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> registerPet(RegistrationRequest registrationRequest);
}
