package today.stepbeyond.examples.springbootexamples.infrastructure.rsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import today.stepbeyond.examples.proto.PetRegistrationRequest;
import today.stepbeyond.examples.proto.PetRegistrationResponse;

import java.time.Duration;

@RestController
public class PetRegistrationEndpoint {

    private static Logger LOG = LoggerFactory.getLogger(PetRegistrationEndpoint.class);


    private final RSocketRequester rSocketRequester;

    public PetRegistrationEndpoint(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @GetMapping("/petRegistration/{name}")
    public void sendBidirectional(@PathVariable("name") String petName) {
        new PetRegistrationRequester(rSocketRequester, petName)
                .request()
                .timeout(Duration.ofSeconds(4))
                .doOnNext(petRegistrationResponse -> LOG.info("Pet registration not finished yet: {}", petRegistrationResponse.getStatus()))
                .doOnComplete(() -> LOG.info("Pet {} was successfully registered", petName))
                .erro
                .subscribe();
    }

    static class PetRegistrationRequester {

        private final RSocketRequester rSocketRequester;
        private final String petName;

        PetRegistrationRequester(RSocketRequester rSocketRequester, String petName) {
            this.rSocketRequester = rSocketRequester;
            this.petName = petName;
        }

        public Flux<PetRegistrationResponse> request() {
            return rSocketRequester.route("channel")
                    .data(Mono.fromSupplier(() -> PetRegistrationRequest.newBuilder().setName(petName).build()))
                    .retrieveFlux(PetRegistrationResponse.class);
        }
    }
}
