package com.rodin.implementComplexComputationalProblem;

public class ProgramImplementProbabilityInCubes {

    private static final double E = 0.0001;

    public static void main(String[] args) throws InterruptedException {

        ImplementProbabilityInCubes implementProbabilityInCubesThread1 =
                new ImplementProbabilityInCubes("First thread probability", E);
        ImplementProbabilityInCubes implementProbabilityInCubesThread2 =
                new ImplementProbabilityInCubes("Second thread probability", E);
        long start = System.currentTimeMillis();
        implementProbabilityInCubesThread1.start();
        implementProbabilityInCubesThread2.start();
        implementProbabilityInCubesThread1.join();
        implementProbabilityInCubesThread2.join();
        System.out.println();
        long finish = System.currentTimeMillis();
        System.out.print("Time work: ");
        System.out.print((float) (finish - start) / 1000);
    }
}
