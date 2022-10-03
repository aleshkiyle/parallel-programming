package com.rsreu.rodin.concurrency.futureAndExecutorService;

import java.util.concurrent.Callable;

class PhotoProperty implements Callable<String> {

    @Override
    public String call() throws Exception {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count += i;
            Thread.sleep(1000);
        }
        return String.format("PhotoProperty: %s", count);
    }
}