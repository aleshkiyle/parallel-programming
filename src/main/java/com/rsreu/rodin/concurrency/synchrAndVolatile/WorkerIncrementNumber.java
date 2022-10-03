package com.rsreu.rodin.concurrency.synchrAndVolatile;

public class WorkerIncrementNumber implements Runnable {

    private static final Integer COUNT_LOOP_ITERATES = 2000;

    @Override
    public void run() {
        for (int i = 0; i < COUNT_LOOP_ITERATES; i++) {
            RunnerSynchronizedTest.incrementNumber();
        }
    }
}
