package today.stepbeyond.examples.springbootexamples.application.domain;

import static com.github.tomakehurst.wiremock.client.WireMock.created;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.github.tomakehurst.wiremock.client.WireMock;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.jms.core.JmsTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.PetEventPublisher;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetEvent;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetIsBornEvent;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.gateways.events.model.PetRegisteredEvent;
import today.stepbeyond.examples.springbootexamples.domain.infrastructure.repository.PetRepository;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Cat;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Dog;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Pet;
import today.stepbeyond.examples.springbootexamples.domain.usecases.BirthOfADog;
import wiremock.org.eclipse.jetty.http.HttpHeader;

@SpringBootTest(
    properties = {"petStoreApi.url=http://localhost:${wiremock.server.port}/"},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class BirthOfDogIT {

  @Container
  public GenericContainer<?> activemq =
      new GenericContainer(DockerImageName.parse("rmohr/activemq:5.14.3")).withExposedPorts(61616);

  @Autowired PetRepository petRepository;

  // With @SpyBean, you can check your beans with mockito toolset
  @SpyBean PetEventPublisher petEventPublisher;

  @Autowired BirthOfADog testee;

  @Autowired JmsTemplate template;

  @Test
  void checkIfDogIsBorn() {

    // GIVEN
    WireMock.stubFor(
        post("/registrations")
            .willReturn(
                created()
                    .withHeader(HttpHeader.LOCATION.asString(), UUID.randomUUID().toString())));

    // WHEN
    var newDog = testee.birthOfADog("Lilly");

    // THEN

    // With soft assertions you can disable the default "fail fast" of the asstion framework
    assertSoftly(
        softly -> {
          softly
              .assertThat(petRepository.find(newDog.getId()))
              .isPresent()
              .get()
              .isExactlyInstanceOf(Dog.class)
              .extracting(Pet::getName)
              .isEqualTo("Lilly");
          softly
              .assertThat(petRepository.find(newDog.getId()))
              .isPresent()
              .get()
              .isNotInstanceOf(Cat.class);
        });

    // Awaitlity is a nice framewprk to ensure that asynchronous operations fulfil assertions after
    // a defined time.
    await()
        .pollDelay(200, TimeUnit.MILLISECONDS)
        .atMost(500, TimeUnit.MILLISECONDS)
        .untilAsserted(
            () ->
                assertThat(template.receiveAndConvert("pet-topic"))
                    .isNotNull()
                    .isInstanceOf(PetEvent.class));

    // Another way to check if some specific bean does the expected operations during integration
    // tests.
    verify(petEventPublisher).publishPetIsBorn(any(PetIsBornEvent.class));
    verify(petEventPublisher).publishPetIsRegistered(any(PetRegisteredEvent.class));
  }
}
