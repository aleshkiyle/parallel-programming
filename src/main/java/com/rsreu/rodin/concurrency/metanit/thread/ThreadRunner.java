package com.rsreu.rodin.concurrency.metanit.thread;

public class ThreadRunner {

    public static void main(String[] args) {
        Thread thread = Thread.currentThread(); // получить главный поток
        RealizationInterfaceRunnable realizationInterfaceRunnable =
                new RealizationInterfaceRunnable();
        thread.setPriority(10);
        // System.out.println(thread.getName());
        // System.out.println(thread);
        // runThreadUsingClassThread();
        // runThreadUsingInterfaceRunnable();
        // iterateNumbersWithJThreadClass();
        // implementMethodJoinMethod();
        realizationInterfaceRunnable.implementRunnable();
    }

    private static void implementMethodJoinMethod() {
        System.out.println("Main thread started");
        Thread thread = new JThread("JThread");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.printf(
                    "%s has been interrupted", Thread.currentThread().getName()
            );
        }
        System.out.println("Main thread finished");
    }

    private static void iterateNumbersWithJThreadClass() {
        System.out.println("Main thread started...");
        for (int i = 1; i < 6; i++) {
            new JThread("JThread " + i).start();
        }
        System.out.println("Main thread finished...");
    }

    private static void runThreadUsingClassThread() {
        System.out.println("MainThread started...");
        JThread jThread = new JThread("JThread");
        jThread.setPriority(10);
        jThread.start();
        System.out.println("MainThread finished...");
    }

    private static void runThreadUsingInterfaceRunnable() {
        System.out.println("Main thread started...");
        Thread myThread = new Thread(new MyThread(), "MyThread");
        myThread.start();
        System.out.println("Main thread finished");
    }
}
