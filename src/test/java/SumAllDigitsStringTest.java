import com.rodin.mavenBasics.logic.SumAllDigitsString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.function.Predicate;

/*
Unit tests to cover build-in Java operations.
 */
public class SumAllDigitsStringTest {

    private static final Integer CONST_NUMBER_TEST_1 = 18;

    private static final Integer CONST_NUMBER_TEST_2 = 19;

    private static final String STRING_TEST_1 = "123,123,123";

    private static final String STRING_TEST_2 = "3exe37,hty60";

    private final SumAllDigitsString sumAllDigitsString;

    public SumAllDigitsStringTest() {
        sumAllDigitsString = new SumAllDigitsString();
    }

    @RepeatedTest(25)
    public void testCalculatingSumOfAllDigitsInString_assertEquals() {
        Assertions.assertEquals(CONST_NUMBER_TEST_2, sumAllDigitsString.calculateSumAllDigitsInString(STRING_TEST_2));
    }


    @RepeatedTest(12)
    public void testCalculatingSumOfAllDigitsInString_assertNotEquals() {
        Assertions.assertNotEquals(CONST_NUMBER_TEST_2, sumAllDigitsString.calculateSumAllDigitsInString(STRING_TEST_1));
    }

    @RepeatedTest(50)
    public void testCalculatingSumOfAllDigitsInString_assertTrue() {
        Predicate<SumAllDigitsString> truePredicate =
                sumAllDigitsString1 -> sumAllDigitsString1.calculateSumAllDigitsInString(STRING_TEST_1) == CONST_NUMBER_TEST_1;

        Assertions.assertTrue(truePredicate.test(sumAllDigitsString));
    }

    @RepeatedTest(50)
    public void testCalculatingSumOfAllDigitsInString_assertFalse() {
        Predicate<SumAllDigitsString> falsePredicate =
                sumAllDigitsString1 -> sumAllDigitsString1.calculateSumAllDigitsInString(STRING_TEST_1) == CONST_NUMBER_TEST_2;
        Assertions.assertFalse(falsePredicate.test(sumAllDigitsString));
    }
}
