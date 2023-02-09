package today.stepbeyond.examples.springbootexamples.infrastructure.jms.pet.event;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.events.PetEventPublisher;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.events.model.PetRegisteredEvent;

@SpringBootTest(properties = {"spring.artemis.mode=embedded"})
class JmsPetEventProducerIT {

  @Autowired PetEventPublisher objectUnderTest;

  @Test
  void shouldSendAnEventWithoutException() {

    assertThatNoException()
        .isThrownBy(
            () ->
                objectUnderTest.publishPetIsRegistered(
                    new PetRegisteredEvent(UUID.randomUUID(), "Lilly", Date.from(Instant.now()))));
  }
}
