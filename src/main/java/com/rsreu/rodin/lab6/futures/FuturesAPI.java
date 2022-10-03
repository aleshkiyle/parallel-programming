package com.rsreu.rodin.lab6.futures;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FuturesAPI {

    private static final Integer COUNT_THREADS = 7;
    private ExecutorService executorService;

    public FuturesAPI() {
        this.executorService = Executors.newFixedThreadPool(COUNT_THREADS);
    }

    public <T> CustomizingFuture submit(Callable<T> callable, Listener<T> listener, ExecutorService listenerExecutor) {
        CustomizingFuture<T> settableFuture =  CustomizingFuture.createSettableFuture(listener, listenerExecutor);
        executorService.submit(() -> {
            try {
                settableFuture.set(callable.call());
            } catch (Exception e) {
                settableFuture.setError(e);
            }
        });
        return null;
    }
}
