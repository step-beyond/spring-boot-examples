package today.stepbeyond.examples.springbootexamples.infrastructure.jms.pet.event;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.MessageNotWriteableException;
import org.springframework.jms.core.JmsTemplate;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetIsBornEvent;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetRegisteredEvent;

@ExtendWith(MockitoExtension.class)
class JmsPetEventPublisherTest {

  private static final String TEST_DESTINATION = "testDestination";

  @Mock JmsTemplate jmsTemplate;

  JmsPetEventPublisher objectUnderTest;

  @BeforeEach
  void setUp() {
    objectUnderTest = new JmsPetEventPublisher(jmsTemplate, TEST_DESTINATION);
  }

  @Test
  void shouldSendPetIsBorn() {

    objectUnderTest.publishPetIsBorn(
        new PetIsBornEvent(UUID.randomUUID(), "Lilly", Date.from(Instant.now())));

    verify(jmsTemplate).convertAndSend(eq(TEST_DESTINATION), any(PetIsBornEvent.class));
  }

  @Test
  void shouldSendPetRegistered() {
    objectUnderTest.publishPetIsRegistered(
        new PetRegisteredEvent(UUID.randomUUID(), "Lilly", Date.from(Instant.now())));

    verify(jmsTemplate).convertAndSend(eq(TEST_DESTINATION), any(PetRegisteredEvent.class));
  }

  @Test
  void shouldHandleJmsExceptionIf_sendPetIsBornFailed() {

    doThrow(MessageNotWriteableException.class)
        .when(jmsTemplate)
        .convertAndSend(eq(TEST_DESTINATION), any(PetIsBornEvent.class));

    assertThatNoException()
        .isThrownBy(
            () ->
                objectUnderTest.publishPetIsBorn(
                    new PetIsBornEvent(UUID.randomUUID(), "Lilly", Date.from(Instant.now()))));
  }

  @Test
  void shouldHandleJmsExceptionIf_sendPetRegisteredFailed() {

    doThrow(MessageNotWriteableException.class)
        .when(jmsTemplate)
        .convertAndSend(eq(TEST_DESTINATION), any(PetRegisteredEvent.class));

    assertThatNoException()
        .isThrownBy(
            () ->
                objectUnderTest.publishPetIsRegistered(
                    new PetRegisteredEvent(UUID.randomUUID(), "Lilly", Date.from(Instant.now()))));
  }
}
