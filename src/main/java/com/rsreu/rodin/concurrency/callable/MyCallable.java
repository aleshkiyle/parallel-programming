package com.rsreu.rodin.concurrency.callable;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws InterruptedException {
        int j = 0;
        for (int i = 0; i < 10; i++, j++) {
            Thread.sleep(100);
        }
        return j;
    }
}
