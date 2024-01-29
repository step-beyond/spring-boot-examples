package today.stepbeyond.examples.springbootexamples.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

public class JsonDifferenceAssertion extends AbstractAssert<JsonDifferenceAssertion, String> {

  protected JsonDifferenceAssertion(String s) {
    super(s, JsonDifferenceAssertion.class);
  }

  public static JsonDifferenceAssertion assertThat(String actual) {
    return new JsonDifferenceAssertion(actual);
  }

  public void isEqualTo(String expected) {

    var objectMapper = new ObjectMapper();
    var type = new TypeReference<HashMap<String, Object>>() {};

    try {
      Map<String, Object> actualMap = objectMapper.readValue(actual, type);
      Map<String, Object> expectedMap = objectMapper.readValue(expected, type);

      MapDifference<String, Object> difference = Maps.difference(actualMap, expectedMap);

      SoftAssertions.assertSoftly(
          softly -> {
            Assertions.assertThat(difference.entriesDiffering())
                .as("Caught differences in common values: %s", difference.entriesDiffering())
                .isEmpty();
            Assertions.assertThat(difference.entriesOnlyOnLeft())
                .as("Caught values only on actual: %s", difference.entriesOnlyOnLeft())
                .isEmpty();
            Assertions.assertThat(difference.entriesOnlyOnRight())
                .as("Caught values only on expected: %s", difference.entriesOnlyOnRight())
                .isEmpty();
          });

    } catch (JsonProcessingException e) {
      Assertions.fail("Unable to process passed string as JSON: ", e);
    }
  }
}
