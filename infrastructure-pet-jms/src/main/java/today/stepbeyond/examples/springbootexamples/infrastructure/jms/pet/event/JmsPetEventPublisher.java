package today.stepbeyond.examples.springbootexamples.infrastructure.jms.pet.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.PetEventPublisher;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetIsBornEvent;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetRegisteredEvent;

@Component
public class JmsPetEventPublisher implements PetEventPublisher {

  private static final Logger LOG = LoggerFactory.getLogger(JmsPetEventPublisher.class);

  private final JmsTemplate jmsTemplate;
  private final String petEventDestination;

  public JmsPetEventPublisher(
      JmsTemplate jmsTemplate, @Value("${app.pet.events.destination}") String petEventDestination) {
    this.jmsTemplate = jmsTemplate;
    this.petEventDestination = petEventDestination;
  }

  @Override
  public void publishPetIsBorn(PetIsBornEvent petIsBornEvent) {
    try {
      jmsTemplate.convertAndSend(petEventDestination, petIsBornEvent);
    } catch (JmsException e) {
      LOG.error("Could not send event: '{}'", petIsBornEvent.getType(), e);
    }
  }

  @Override
  public void publishPetIsRegistered(PetRegisteredEvent petRegisteredEvent) {
    try {
      jmsTemplate.convertAndSend(petEventDestination, petRegisteredEvent);
    } catch (JmsException e) {
      LOG.error("Could not send event: '{}'", petRegisteredEvent.getType(), e);
    }
  }
}
