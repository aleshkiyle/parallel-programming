package com.rodin.concurrency.metanit.terminatingInterruptingThread;

public class Program {

    public static void main(String[] args) {
        System.out.println("Main thread started...");
        MyThread myThread = new MyThread();
        new Thread(myThread, "MyThread").start();

        try {
            Thread.sleep(5000);
            myThread.divisible();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread has been interrupted");
        }
        System.out.println("Thread thread finished... ");
    }
}
