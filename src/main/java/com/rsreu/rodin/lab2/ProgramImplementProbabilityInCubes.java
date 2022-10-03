package com.rsreu.rodin.lab2;

public class ProgramImplementProbabilityInCubes {

    public static void main(String[] args) throws InterruptedException {

        final long TESTS_COUNT = Integer.MAX_VALUE / 77;

        ImplementProbabilityInCubes implementProbabilityInCubes =
                new ImplementProbabilityInCubes(TESTS_COUNT);
        long start = System.currentTimeMillis();
        double probability = implementProbabilityInCubes.getProbabilityInExperiment(implementProbabilityInCubes.getTestCounts());
        long finish = System.currentTimeMillis();
        double timeWork = (finish - start) / 1e3;
        System.out.printf("Probability: %f\n", probability);
        System.out.printf("Time work: %f\n", timeWork);
    }
}
