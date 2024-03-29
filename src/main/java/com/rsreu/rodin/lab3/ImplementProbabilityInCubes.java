package com.rsreu.rodin.lab3;

import com.rsreu.rodin.generalLogic.UtilityCalculateProbabilityInCubesLogic;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicReference;

public class ImplementProbabilityInCubes implements Runnable {

    private static final Integer COUNT_MESSAGE = 10;

    private AtomicReference<Double> resultProbability;
    @Getter
    private long testCount;

    public ImplementProbabilityInCubes(long testCount) {
        this.testCount = testCount;
    }

    private static boolean checkSumPointDuringExperiment() {
        UtilityCalculateProbabilityInCubesLogic utilityCalculateProbabilityInCubesLogic
                = new UtilityCalculateProbabilityInCubesLogic();
        return utilityCalculateProbabilityInCubesLogic.makeExperiment();
    }

    public double calculateExceedProbability(long testsCount) throws InterruptedException {
        double probability;

        long numbersExcesses = 0;
        for (long i = 0; i < testsCount; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.printf("Thread with %d interrupted", Thread.currentThread().getId());
                throw new InterruptedException();
            }

            if (checkSumPointDuringExperiment()) {
                numbersExcesses++;
            }
        }
        probability = (double) numbersExcesses / testsCount;
        return probability;
    }

    @Override
    public void run() {
        try {
            synchronized (resultProbability) {
                resultProbability.set(resultProbability.get() + calculateExceedProbability(testCount));
                System.out.printf(" Probability: %f %n", calculateExceedProbability(testCount));
                System.out.printf("Thread %s finished %n", Thread.currentThread().getId());
            }
        } catch (InterruptedException e) {
            System.out.printf("%nThread %s stopped %n", Thread.currentThread().getId());
        }
    }
}
