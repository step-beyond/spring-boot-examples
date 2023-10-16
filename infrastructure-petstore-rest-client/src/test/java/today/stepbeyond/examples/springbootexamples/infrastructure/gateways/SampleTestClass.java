package today.stepbeyond.examples.springbootexamples.infrastructure.gateways;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTestClass {


    @Test
    void thisTestMethodShouldNeverFail() {
        assertTrue(true);
    }

    @Test
    void twoPlusTwoShouldBeFour() {
        assertThat(2+2).isEqualTo(4);
    }

    @Test
    void thisTestMethodShouldFail() {
        assertTrue(false);
    }
}