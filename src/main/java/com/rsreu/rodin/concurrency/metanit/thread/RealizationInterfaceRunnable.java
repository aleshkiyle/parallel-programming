package com.rsreu.rodin.concurrency.metanit.thread;

public class RealizationInterfaceRunnable {

    public void implementRunnable() {
        System.out.println("Main thread started...");
        Runnable runnable = () -> {
            System.out.printf("%s thread started"
                    , Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s thread finished", Thread.currentThread().getName());
        };
        Thread thread = new Thread(runnable, "MyThread");
        thread.start();
        System.out.println("Main thread finished...");
    }
}
