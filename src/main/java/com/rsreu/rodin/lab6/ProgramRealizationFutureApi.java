package com.rsreu.rodin.lab6;


import com.rsreu.rodin.lab2.ImplementProbabilityInCubes;

import java.util.function.Function;

public class ProgramRealizationFutureApi {

    public static void main(String[] args) {
        final long TESTS_COUNT = Integer.MAX_VALUE / 12;

        double startTime;
        long endTime;
        double probability;

        try {
            startTime = System.currentTimeMillis();
            probability = ThreadControlUsingExecutorService.startThreadUsingExecutors(TESTS_COUNT);
            endTime = System.currentTimeMillis();
            System.out.printf("Parallel computing:%nTime: %.3f%nProbability: %f%n%n",
                    (endTime - startTime) / 1000, probability);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        startTime = System.currentTimeMillis();
        Function<Long, Double> getProbabilityInExperiment = ImplementProbabilityInCubes::getProbabilityInExperiment;
        probability = getProbabilityInExperiment.apply(TESTS_COUNT);
        endTime = System.currentTimeMillis();
        System.out.printf("Non-parallel computing:%nTime: %.3f%nProbability: %f%n%n",
                (endTime - startTime) / 1000, probability);
    }
}
