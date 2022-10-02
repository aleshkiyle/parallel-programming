package com.rodin.implementComplexComputationalProblem;

public class Program {

    private static final double E = 0.0001;

    public static void main(String[] args) throws InterruptedException {
        ImplementProbabilityInCubes probabilityInCubes =
                new ImplementProbabilityInCubes(E);
        long start = System.currentTimeMillis();
        probabilityInCubes.start();
        probabilityInCubes.join();
        System.out.println();
        long finish = System.currentTimeMillis();
        System.out.print("Time work: ");
        System.out.print((float) (finish - start) / 1000);
    }
}
