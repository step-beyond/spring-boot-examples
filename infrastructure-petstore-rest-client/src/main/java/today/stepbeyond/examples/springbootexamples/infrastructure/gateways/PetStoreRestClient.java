package today.stepbeyond.examples.springbootexamples.infrastructure.gateways;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import today.stepbeyond.examples.springbootexamples.domain.model.Pet;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.api.PetStoreApi;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.api.model.RegistrationRequest;

@Service
public class PetStoreRestClient implements PetStoreClient {

    Logger log = LoggerFactory.getLogger(PetStoreRestClient.class);

    private final PetStoreApi api;

    public PetStoreRestClient(PetStoreApi api) {
        this.api = api;
    }

    @Override
    public boolean registerPet(Pet pet) {
        try {
            var registrationResponse = api.registerPet(new RegistrationRequest().setPetId(pet.getId()));
            return registrationResponse.getStatusCode().is2xxSuccessful();
        } catch (FeignException.FeignClientException e) {
            log.info("Unable to register pet in petstore service", e);
            return false;
        }
    }
}
