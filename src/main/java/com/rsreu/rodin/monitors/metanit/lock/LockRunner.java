package com.rsreu.rodin.monitors.metanit.lock;

import java.util.concurrent.locks.ReentrantLock;

public class LockRunner {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        ResourceExample resourceExample = new ResourceExample();
        for (int i = 1; i < 6; i++) {
            Thread thread = new Thread(new ReentrantLockRealisation(resourceExample, reentrantLock));
            thread.setName("Thread " + i);
            thread.start();
        }
    }
}
