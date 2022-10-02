package com.rodin.concurrency.callable;

import java.util.concurrent.Callable;

public class ExampleCallable implements Callable<String> {

    @Override
    public String call() throws InterruptedException {
        Thread.sleep(100);
        return "Hello, world";
    }
}
