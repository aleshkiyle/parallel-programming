package com.rodin.concurrency.futureAndExecutorService;

import java.util.concurrent.Callable;

public class MarketProperty implements Callable<String> {

    @Override
    public String call() throws Exception {
        int size = (int) Math.floor(Math.random() * 100 + 1);
        int count = 0;
        for (int i = 0; i < size; i++) {
            count += i;
            Thread.sleep(10);
        }
        return String.format("Market property: %s", count);
    }
}
