package com.rodin.implementComplexComputationalProblem;

public class Program {

    private static final int THROW = 100;

    public static void main(String[] args) {
        ImplementCalculateProbability implementCalculateProbability =
                new ImplementCalculateProbability();

        float probability = implementCalculateProbability.implementTask(THROW);
        long finish = System.currentTimeMillis();
        long start = System.currentTimeMillis();
        System.out.printf("%.10f", probability);
        System.out.println();
        System.out.print("Время работы ");
        System.out.print((float)(finish - start)/1000);
    }
}
