package today.stepbeyond.examples.springbootexamples.infrastructure.jms.pet.event;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.events.model.PetIsBornEvent;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.events.model.PetRegisteredEvent;

@ExtendWith(MockitoExtension.class)
class JmsPetEventProducerTest {

  @Mock JmsTemplate jmsTemplate;

  @Test
  void shouldSendPetIsBorn() {
    var testee = new JmsPetEventProducer(jmsTemplate, "testDestination");

    testee.publishPetIsBorn(
        new PetIsBornEvent(UUID.randomUUID(), "Lilly", Date.from(Instant.now())));

    verify(jmsTemplate).convertAndSend(eq("testDestination"), any(PetIsBornEvent.class));
  }

  @Test
  void shouldSendPetRegistered() {
    var testee = new JmsPetEventProducer(jmsTemplate, "testDestination");

    testee.publishPetIsRegistered(
        new PetRegisteredEvent(UUID.randomUUID(), "Lilly", Date.from(Instant.now())));

    verify(jmsTemplate).convertAndSend(eq("testDestination"), any(PetRegisteredEvent.class));
  }
}
