package com.rodin.implementComplexComputationalProblem;

public class ImplementProbabilityInCubes extends Thread {

    private static final int THROW = 9626564;

    private double e;

    public ImplementProbabilityInCubes(double e) {
        this.e = e;
    }

    public static int getRandomNumber() {
        return (int) (Math.random() * 6) + 1;
    }

    public void getProbability(double e) {
        int countMore80 = 0;
        float probability = 0;
        int counterPercent = 0;
        int[] array = new int[10];
        for (int j = 0; j < THROW; j++) {
            countMore80 = getCountMore80(e, countMore80, probability, array);
            probability = (float) countMore80 / j;
            if (j == THROW/ 10 * counterPercent) {
                System.out.println((counterPercent * 10) + " %");
                counterPercent++;
            }
        }
        System.out.printf("%.10f", probability);
    }

    private int getCountMore80(double e, int countMore80, float probability, int[] array) {
        // while (Math.abs(probability - e / 2) > e) {
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
        //  }
        return countMore80;
    }

    @Override
    public void run() {
        getProbability(e);
    }
}