package com.rsreu.rodin.lab3;

import lombok.Getter;

import java.util.Random;

public class ImplementProbabilityInCubes implements Runnable {

    private static final int DICE_EDGE_COUNT = 10; // количество граней кубика
    private static final int THROWS_COUNT_IN_SINGLE_TEST = 10; // количество бросков в одном опыте
    public static final int LOWER_SCORE_BOUND = 80; // значение очков, которое надо превысить, чтобы событие произошло

    private static final Random random = new Random();

    @Getter
    private long testCount;

    public ImplementProbabilityInCubes(long testCount) {
        this.testCount = testCount;
    }

    private static boolean makeThrowingExperiment() {
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

    public double calculateExceedProbability(long testsCount) throws InterruptedException {
        double probability;
        int countMessage = 10;
        long stepMessage = testsCount / countMessage;
        long nextMessageIndex = 0;

        long numbersExcesses = 0;
        for (long i = 0; i < testsCount; ++i) {
            if (Thread.currentThread().isInterrupted()){
                System.out.printf("Thread with %d interrupted", Thread.currentThread().getId());
                return 0;
            }

            if (makeThrowingExperiment())
                ++numbersExcesses;

            if (i == nextMessageIndex) {
                System.out.printf("Thread name: %s, Completed on %.1f percent\n",
                        Thread.currentThread().getName(),
                        100.0 * nextMessageIndex / testsCount);
                nextMessageIndex += stepMessage;
            }
        }
        probability = (double) numbersExcesses / testsCount;
        return probability;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Probability: %f\n", calculateExceedProbability(testCount));
            System.out.printf("Thread %s finished", Thread.currentThread().getId());
        } catch (InterruptedException e) {
            System.out.printf("Thread %s stopped\n", Thread.currentThread().getId());
        }
    }
}
