package com.rsreu.rodin.monitors.metanit.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockRealisation implements Runnable {

    private final ReentrantLock reentrantLock;
    private final ResourceExample resourceExample;

    public ReentrantLockRealisation(ResourceExample resourceExample, ReentrantLock reentrantLock) {
        this.resourceExample = resourceExample;
        this.reentrantLock = reentrantLock;
    }

    @Override
    public void run() {
        this.reentrantLock.lock();
        try {
            this.resourceExample.x = 1;
            for (int i = 1; i < 5; i++) {
                System.out.printf("%s %d %n", Thread.currentThread().getName(), this.resourceExample.getX());
                this.resourceExample.x++;
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
