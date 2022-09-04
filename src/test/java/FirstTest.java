import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
Unit tests to cover build-in Java operations.
 */
public class FirstTest {

    @Test
    public void testPlusOperator() {
        int expectedValue = 5;
        int actualValue = addiOTwoIntegers(2, 3);
        int actual = addiOTwoIntegers(10, -33);
        Assertions.assertEquals(expectedValue, actualValue);
        Assertions.assertNotEquals(expectedValue, actual);
    }

    private static int addiOTwoIntegers(int a, int b) {
        return a + b;
    }
}
