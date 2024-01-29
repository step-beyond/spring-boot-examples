package today.stepbeyond.examples.springbootexamples.testing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JsonDifferenceAssertionTest {

  @Test
  void shouldBeEqual() {

    var actual =
        "{\"registered\":false,\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"name\":\"Hasso\"}";

    var expected =
        "{\"registered\":false,\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"name\":\"Hasso\"}";

    JsonDifferenceAssertion.assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldBeEqualInDifferentOrder() {

    var actual =
        "{\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"registered\":false,\"name\":\"Hasso\"}";

    var expected =
        "{\"registered\":false,\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"name\":\"Hasso\"}";

    JsonDifferenceAssertion.assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldHaveDifferencesOnActual() {

    var actual =
        "{\"FOO\":\"NOT_EXISTING\",\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"registered\":false,\"name\":\"Hasso\"}";

    var expected =
        "{\"registered\":false,\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"name\":\"Hasso\"}";

    Assertions.assertThatCode(() -> JsonDifferenceAssertion.assertThat(actual).isEqualTo(expected))
        .isInstanceOf(AssertionError.class)
        .hasMessageContaining("FOO")
        .hasMessageContaining("NOT_EXISTING");
  }

  @Test
  void shouldHaveDifferencesOnExpected() {

    var actual = "{\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"name\":\"Hasso\"}";

    var expected =
        "{\"registered\":false,\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"name\":\"Hasso\"}";

    Assertions.assertThatCode(() -> JsonDifferenceAssertion.assertThat(actual).isEqualTo(expected))
        .isInstanceOf(AssertionError.class)
        .hasMessageContaining("registered")
        .hasMessageContaining("false");
  }

  @Test
  void shouldHaveDifferencesOnValues() {

    var actual =
        "{\"registered\":true,\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"name\":\"Hasso\"}";

    var expected =
        "{\"registered\":false,\"id\":\"66d0d3c4-7f78-4106-84ad-71691ba6d7d6\",\"name\":\"Hasso\"}";

    Assertions.assertThatCode(() -> JsonDifferenceAssertion.assertThat(actual).isEqualTo(expected))
        .isInstanceOf(AssertionError.class)
        .hasMessageContaining("registered")
        .hasMessageContaining("false")
        .hasMessageContaining("true");
  }
}
