package com.rsreu.rodin.monitors.metanit;

import java.util.concurrent.Semaphore;

public class CountThread implements Runnable {

    private final CommonResource res;
    private final Semaphore semaphore;
    private final String name;

    public CountThread(CommonResource commonResource, Semaphore semaphore, String name) {
        this.res = commonResource;
        this.semaphore = semaphore;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " ожидает разрешене");
            this.semaphore.acquire();
            this.res.x = 1;
            for (int i = 1; i < 5; i++) {
                System.out.println(this.name + ": " + this.res.x);
                this.res.x++;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(this.name + " освобождает разрешение");
        this.semaphore.release();
    }
}
