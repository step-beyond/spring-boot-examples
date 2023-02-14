package today.stepbeyond.examples.springbootxamples.infrastructure.rabbitmq;

import static today.stepbeyond.examples.springbootxamples.infrastructure.rabbitmq.RabbitMqConfiguration.QUEUE_NAME;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.controller.PetRegistrationInputPort;
import today.stepbeyond.examples.springbootxamples.infrastructure.rabbitmq.model.PetDeregisteredEvent;

@Service
public class RabbitMqListener {

  private final PetRegistrationInputPort port;

  public RabbitMqListener(PetRegistrationInputPort port) {
    this.port = port;
  }

  @RabbitListener(queues = QUEUE_NAME)
  public void onPetDeregisteredEvent(PetDeregisteredEvent event) {
    port.deregisterPet(event.id());
  }
}
