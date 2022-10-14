package com.rsreu.rodin.generalLogic;

import java.util.Random;

public class UtilityCalculateProbabilityInCubesLogic {

    private static final Integer THROWS_COUNT_IN_SINGLE_EXPERIMENT = 10;

    private static final Integer NUMBER_FACES_CUBE = 10;

    private static final Integer LOWER_SCORE_BOUND = 80;
    public boolean makeExperiment() {
        Random random = new Random();
        int sum = 0;
        boolean sumOverEighteenPoints;
        for (int i = 0; i < THROWS_COUNT_IN_SINGLE_EXPERIMENT; i++) {
            int thrownValue = 0;
            // бросаем до тех пор, пока не получим "невзрывную" комбинацию
            do {
                thrownValue += random.nextInt(NUMBER_FACES_CUBE) + 1;
            }
            while (thrownValue == NUMBER_FACES_CUBE);

            sum += thrownValue;
        }
        sumOverEighteenPoints = sum > LOWER_SCORE_BOUND;
        return sumOverEighteenPoints;
    }
}
