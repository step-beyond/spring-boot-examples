package today.stepbeyond.examples.springbootexamples.infrastructure.gateways;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import today.stepbeyond.examples.springbootexamples.domain.core.model.Dog;
import today.stepbeyond.examples.springbootexamples.infrastructure.gateways.api.PetStoreApi;

@ExtendWith(MockitoExtension.class)
class PetStoreRestClientTest {

  @Mock PetStoreApi api;

  @InjectMocks PetStoreRestClient objectUnderTest;

  @Test
  void shouldRegisterPet() {
    // GIVEN
    var dog = new Dog(UUID.fromString("9AD0236E-43A7-40C7-9FF1-54FEE4D23D84"), "Hasso", false);
    when(api.registerPet(any())).thenReturn(ResponseEntity.ok(null));

    // WHEN
    var result = objectUnderTest.registerPet(dog);

    // THEN
    assertThat(result).isTrue();
  }
}
