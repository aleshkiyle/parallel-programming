package com.rsreu.rodin.lab2;

import com.rsreu.rodin.generalLogic.UtilityCalculateProbabilityInCubesLogic;
import lombok.Getter;

public record ImplementProbabilityInCubes(@Getter long testCounts) {

    private static boolean makeThrowingExperiment() {
        UtilityCalculateProbabilityInCubesLogic utilityCalculateProbabilityInCubesLogic =
                new UtilityCalculateProbabilityInCubesLogic();
        return utilityCalculateProbabilityInCubesLogic.makeExperiment();
    }

    public static double getProbabilityInExperiment(long testsCount) {
        double probability;
        long numbersExcesses = 0;
        for (long i = 0; i < testsCount; i++)
            if (makeThrowingExperiment()) {
                numbersExcesses++;
            }
        probability = (double) numbersExcesses / testsCount;
        return probability;
    }

}