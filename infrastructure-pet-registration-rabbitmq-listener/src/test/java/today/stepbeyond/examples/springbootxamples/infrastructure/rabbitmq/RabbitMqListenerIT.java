package today.stepbeyond.examples.springbootxamples.infrastructure.rabbitmq;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
import static today.stepbeyond.examples.springbootxamples.infrastructure.rabbitmq.RabbitMqConfiguration.QUEUE_NAME;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.controller.PetRegistrationInputPort;
import today.stepbeyond.examples.springbootxamples.infrastructure.rabbitmq.model.PetDeregisteredEvent;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = RabbitMqListenerIT.Initializer.class)
@Testcontainers
class RabbitMqListenerIT {

  @Container
  public static RabbitMQContainer rabbitMQContainer =
      new RabbitMQContainer(DockerImageName.parse("rabbitmq:3-management")).withExposedPorts(5672);

  public static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertyValues.of(
              "spring.rabbitmq.host=" + rabbitMQContainer.getHost(),
              "spring.rabbitmq.port=" + rabbitMQContainer.getMappedPort(5672))
          .applyTo(applicationContext);
    }
  }

  @MockBean PetRegistrationInputPort inputPort;

  @InjectMocks RabbitMqListener objectUnderTest;

  @Autowired RabbitTemplate template;

  @Test
  void shouldDeregisteredPet() {
    // GIVEN
    var id = UUID.fromString("A2600735-146B-4E05-A584-9AFB55D2C60F");
    var event = new PetDeregisteredEvent(id);

    // WHEN
    template.convertAndSend(QUEUE_NAME, event);

    // THEN
    await()
        .pollDelay(50, TimeUnit.MILLISECONDS)
        .atMost(500, TimeUnit.MILLISECONDS)
        .untilAsserted(() -> verify(inputPort, times(1)).deregisterPet(id));
  }
}
