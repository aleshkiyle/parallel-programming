package com.rsreu.rodin.lab2;

public class ProgramImplementProbabilityInCubes {

    public static void main(String[] args) throws InterruptedException {
        ImplementProbabilityInCubes implementProbabilityInCubes =
                new ImplementProbabilityInCubes(Integer.MAX_VALUE / 100);
        long start = System.currentTimeMillis();
        double probability = implementProbabilityInCubes.getProbabilityInExperiment(
                implementProbabilityInCubes.getTestCounts()
        );
        long finish = System.currentTimeMillis();
        double timeWork = (finish - start) / 1e3;
        System.out.printf("Probability: %.5f %n", probability);
        System.out.printf("Time work: %.5f %n", timeWork);
    }
}
