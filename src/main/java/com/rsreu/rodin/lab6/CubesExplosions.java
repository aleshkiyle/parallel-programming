package com.rsreu.rodin.lab6;

import com.rsreu.rodin.generalLogic.UtilityCalculateProbabilityInCubesLogic;

public class CubesExplosions {

    private static final boolean MESSAGE_PRINT = false;

    public static double calculateExceedProbability(long testCount) {
        UtilityCalculateProbabilityInCubesLogic utilityCalculateProbabilityInCubesLogic =
                new UtilityCalculateProbabilityInCubesLogic();
        final int MESSAGE_COUNT = 15;
        final long MESSAGE_STEP = testCount / MESSAGE_COUNT;
        long nextMessageIndex = 0;

        long exceededCount = 0;
        for (int i = 0; i < testCount; i++) {
            if (Thread.interrupted()) {
                break;
            }

            if (utilityCalculateProbabilityInCubesLogic.makeExperiment()) {
                exceededCount++;
            }

            if (MESSAGE_PRINT && i == nextMessageIndex) {
                System.out.printf("Выполнено на %.2f%%%n", 100.0 * nextMessageIndex / testCount);
                nextMessageIndex += MESSAGE_STEP;
            }
        }
        if (MESSAGE_PRINT) {
            System.out.println("Complete!");
        }
        return (double) exceededCount / testCount;
    }
}
