package today.stepbeyond.examples.springbootxamples.infrastructure.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableRabbit
@PropertySource("classpath:/defaults.properties")
public class RabbitMqConfiguration {

  public static final String QUEUE_NAME = "pet_deregistered_event";

  @Bean
  public Queue queue() {
    return new Queue(QUEUE_NAME);
  }

  @Bean
  public Jackson2JsonMessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }
}
