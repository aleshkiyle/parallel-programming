package com.rsreu.rodin.lab3;

public class ProgramThreadStart {

    public static void main(String[] args) {
        Thread probabilityCalcThread = new Thread(new ImplementProbabilityInCubes());
        probabilityCalcThread.setName("probabilityThreadCubes");
        long start = System.currentTimeMillis();
        probabilityCalcThread.start();
        try {
            probabilityCalcThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        System.out.print("Time work: ");
        System.out.print((float) (finish - start) / 1000);
    }
}
