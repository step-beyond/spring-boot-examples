package today.stepbeyond.examples.springbootxamples.infrastructure.rabbitmq;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.controller.PetRegistrationInputPort;
import today.stepbeyond.examples.springbootxamples.infrastructure.rabbitmq.model.PetDeregisteredEvent;

@ExtendWith(MockitoExtension.class)
class RabbitMqListenerTest {

  @Mock PetRegistrationInputPort inputPort;

  @InjectMocks RabbitMqListener objectUnderTest;

  @Test
  void shouldDeregisteredPet() {
    // GIVEN
    var id = UUID.fromString("A2600735-146B-4E05-A584-9AFB55D2C60F");
    var event = new PetDeregisteredEvent(id);

    // WHEN
    objectUnderTest.onPetDeregisteredEvent(event);

    // THEN
    verify(inputPort, times(1)).deregisterPet(id);
  }
}
