package com.rodin.concurrency.futureAndExecutorService.futures;

@FunctionalInterface
public interface Listener<T> {

    void settableSet(T result, Exception e);
}
