import com.rodin.mavenBasics.logic.SumAllDigitsString;
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

    @Test
    public void testCalculatingSumOfAllDigitsInString() {
        SumAllDigitsString sumAllDigitsString =
                new SumAllDigitsString();
        Assertions.assertEquals(100, sumAllDigitsString.calculateSumAllDigitsInString("3exe37,hty60"));
        Assertions.assertNotEquals(50, "45port45");
    }

    private static int addiOTwoIntegers(int a, int b) {
        return a + b;
    }
}
