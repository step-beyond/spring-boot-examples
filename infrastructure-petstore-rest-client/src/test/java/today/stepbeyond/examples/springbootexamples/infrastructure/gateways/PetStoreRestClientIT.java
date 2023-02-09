package today.stepbeyond.examples.springbootexamples.infrastructure.gateways;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import today.stepbeyond.examples.springbootexamples.domain.model.Dog;
import wiremock.org.eclipse.jetty.http.HttpHeader;

import static com.github.tomakehurst.wiremock.client.WireMock.created;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = "petStoreApi.url=http://localhost:${wiremock.server.port}/",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWireMock(port = 0)
class PetStoreRestClientIT {

    @Autowired
    private PetStoreRestClient objectUnderTest;

    @BeforeEach
    void setUp() {
        WireMock.reset();
    }

    @Test
    void shouldRegisterDog() {
        // GIVEN
        var uuid = UUID.fromString("66d0d3c4-7f78-4106-84ad-71691ba6d7d6");
        var dog = new Dog(uuid, "Hasso", false);

        WireMock.stubFor(post("/registrations").withRequestBody(equalTo("{\"petId\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\"}"))
                .willReturn(
                        created().withHeader(HttpHeader.LOCATION.asString(), UUID.randomUUID().toString())));

        // WHEN
        var result = objectUnderTest.registerPet(dog);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    void shouldNotRegisterDog() {
        // GIVEN
        var uuid = UUID.fromString("66d0d3c4-7f78-4106-84ad-71691ba6d7d6");
        var dog = new Dog(uuid, "Hasso", false);

        WireMock.stubFor(post("/registrations").withRequestBody(equalTo("{\"petId\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\"}"))
                .willReturn(ResponseDefinitionBuilder.responseDefinition().withStatus(422)));

        // WHEN
        var result = objectUnderTest.registerPet(dog);

        // THEN
        assertThat(result).isFalse();
    }
}