package today.stepbeyond.examples.springbootexamples.infrastructure.jms.pet.event;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jms.MessageNotWriteableException;
import org.springframework.jms.core.JmsTemplate;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.PetEventPublisher;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetIsBornEvent;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetRegisteredEvent;

@SpringBootTest(properties = {"spring.artemis.mode=embedded", "app.pet.events.destination=test"})
class JmsPetEventPublisherIT {

  @Autowired PetEventPublisher objectUnderTest;

  @SpyBean JmsTemplate jmsTemplate;

  @Test
  void shouldSendAnEventWithoutException() {
    objectUnderTest.publishPetIsRegistered(
        new PetRegisteredEvent(UUID.randomUUID(), "Lilly", Date.from(Instant.now())));

    var result = jmsTemplate.receiveAndConvert("test");
    assertThat(result).isExactlyInstanceOf(PetRegisteredEvent.class);
  }

  @Test
  void shouldHandleJmsExceptionCorrectly() {
    doThrow(MessageNotWriteableException.class)
        .when(jmsTemplate)
        .convertAndSend(anyString(), any(PetIsBornEvent.class));

    assertThatNoException()
        .isThrownBy(
            () ->
                objectUnderTest.publishPetIsRegistered(
                    new PetRegisteredEvent(UUID.randomUUID(), "Lilly", Date.from(Instant.now()))));
  }
}
