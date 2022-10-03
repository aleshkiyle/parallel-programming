package com.rsreu.rodin.lab2;

import java.util.Random;

public class ImplementProbabilityInCubes {

    private static final int DICE_EDGE_COUNT = 10; // количество граней кубика

    private static final int THROWS_COUNT_IN_SINGLE_TEST = 10; // количество бросков в одном опыте

    private static final int LOWER_SCORE_BOUND = 80; // значение очков, которое надо превысить, чтобы событие произошло

    private static boolean makeThrowingExperiment() {
        Random random = new Random();
        int sum = 0;
        for (int i = 0; i < THROWS_COUNT_IN_SINGLE_TEST; ++i) {
            int thrownValue = 0;
            do // бросаем до тех пор, пока не получим "невзрывную" комбинацию
                thrownValue += random.nextInt(DICE_EDGE_COUNT) + 1;
            while (thrownValue == DICE_EDGE_COUNT);
            sum += thrownValue;
        }
        return sum > LOWER_SCORE_BOUND;
    }

    public double getProbabilityInExperiment(long testsCount) {
        double probability;
        long numbersExcesses = 0;
        for (long i = 0; i < testsCount; ++i)
            if (makeThrowingExperiment())
                ++numbersExcesses;
        probability = (double) numbersExcesses / testsCount;
        return probability;
    }

}