package com.rsreu.rodin.lab6;


import com.rsreu.rodin.lab2.ImplementProbabilityInCubes;

import java.util.function.Function;
import java.util.logging.Logger;

public class ProgramRealizationFutureApi {

    private static final Logger logger = Logger.getLogger(ProgramRealizationFutureApi.class.getName());

    public static void main(String[] args) throws Exception {
        final long TESTS_COUNT = 1000000000;

        long startTime;
        long endTime;
        double probability;

        // параллельно
        System.out.println("Начало параллельных вычислений");
        startTime = System.nanoTime();
        probability = ThreadControlUsingExecutorService.startThreadUsingExecutors(TESTS_COUNT);
        endTime = System.nanoTime();
        System.out.printf("Параллельно:\nВремя: %.3f\nВероятность: %f\n\n", (endTime - startTime) / 1e9, probability);

        // непараллельно
        System.out.println("Начало непараллельных вычислений");
        startTime = System.nanoTime();
        Function<Long, Double> getProbabilityInExperiment = ImplementProbabilityInCubes::getProbabilityInExperiment;
        probability = getProbabilityInExperiment.apply(TESTS_COUNT);
        endTime = System.nanoTime();
        System.out.printf("Не параллельно:\nВремя: %.3f\nВероятность: %f\n\n", (endTime - startTime) / 1e9, probability);
    }
}
