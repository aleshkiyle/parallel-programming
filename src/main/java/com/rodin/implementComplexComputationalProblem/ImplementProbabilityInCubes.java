package com.rodin.implementComplexComputationalProblem;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class ImplementProbabilityInCubes extends Thread implements Callable<String> {

    private static final int THROW = 9626564;

    @Getter
    private final double e;

    public ImplementProbabilityInCubes(String name, double e) {
        super(name);
        this.e = e;
    }

    public void printProbability() {
        double probability = getProb();
        System.out.printf("Probability: %.10f \n", probability);
    }

    public double getProb() {
        int countMore80 = 0;
        float probability = 0;
        int counterPercent = 0;
        int[] array = new int[10];
        for (int j = 0; j < THROW; j++) {
            countMore80 = getCountMore80(countMore80, array);
            probability = (float) countMore80 / j;
            if (j == THROW / 10 * counterPercent) {
                System.out.printf("Thread name: %s, percent progress bar: %d \n",
                        Thread.currentThread().getName(),
                        (counterPercent * 10));
                counterPercent++;
            }
        }
        return probability;
    }

    private int getCountMore80(int countMore80, int[] array) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            array[i] = getRandomNumber();
            sum += array[i];
            if (i != 0 && array[i] == array[i - 1]) {
                i--;
            }
        }
        if (sum > 80) {
            countMore80 += 1;
        }
        return countMore80;
    }

    private static int getRandomNumber() {
        return (int) (Math.random() * 6) + 1;
    }

    @Override
    public void run() {
        printProbability();
    }

    @Override
    public String call() throws Exception {
        return String.format("Probability: %.10f", getProb());
    }
}