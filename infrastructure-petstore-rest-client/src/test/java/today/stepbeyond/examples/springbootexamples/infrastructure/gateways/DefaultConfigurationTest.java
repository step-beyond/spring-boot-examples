package today.stepbeyond.examples.springbootexamples.infrastructure.gateways;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DefaultConfigurationTest {

    @Value("${petStoreApi.url}")
    String petStoreApiUrl;

    @Test
    void shouldReadDefaultPropertiesFile() {
        assertThat(petStoreApiUrl).isEqualTo("http://localhost:9000");
    }
}
