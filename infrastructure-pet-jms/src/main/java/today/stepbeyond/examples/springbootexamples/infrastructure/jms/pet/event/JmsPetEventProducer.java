package today.stepbeyond.examples.springbootexamples.infrastructure.jms.pet.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.events.PetEventPublisher;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.events.model.PetIsBornEvent;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.events.model.PetRegisteredEvent;

@Component
public class JmsPetEventProducer implements PetEventPublisher {

  private static final Logger LOG = LoggerFactory.getLogger(JmsPetEventProducer.class);

  private final JmsTemplate jmsTemplate;
  private final String petIsBornDestination;

  public JmsPetEventProducer(
      JmsTemplate jmsTemplate,
      @Value("${app.pet.events.pet.destination}") String petIsBornDestination) {
    this.jmsTemplate = jmsTemplate;
    this.petIsBornDestination = petIsBornDestination;
  }

  @Override
  public void publishPetIsBorn(PetIsBornEvent petIsBornEvent) {
    try {
      jmsTemplate.convertAndSend(petIsBornDestination, petIsBornEvent);
    } catch (JmsException e) {
      LOG.error("Could not send event: '{}'", petIsBornEvent.getType(), e);
    }
  }

  @Override
  public void publishPetIsRegistered(PetRegisteredEvent petRegisteredEvent) {
    try {
      jmsTemplate.convertAndSend(petIsBornDestination, petRegisteredEvent);
    } catch (JmsException e) {
      LOG.error("Could not send event: '{}'", petRegisteredEvent.getType(), e);
    }
  }
}
