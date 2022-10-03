package com.rsreu.rodin.concurrency.metanit.terminatingInterruptingThread;

public class MyThread implements Runnable {

    private boolean isActive;

    public void divisible() {
        isActive = false;
    }

    public MyThread() {
        isActive = true;
    }


    @Override
    public void run() {
        Runnable runnable = () -> {
            System.out.printf("%s started...\n", Thread.currentThread().getName());
            int counter = 1;
            while (this.isActive) {
                System.out.println("Loop " + counter++);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Thread has been interrupted");
                }

            }
            System.out.printf("%s finished...", Thread.currentThread().getName());
        };
    }
}
