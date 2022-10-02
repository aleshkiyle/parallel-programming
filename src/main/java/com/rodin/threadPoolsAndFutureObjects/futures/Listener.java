package com.rodin.threadPoolsAndFutureObjects.futures;

@FunctionalInterface
public interface Listener<T> {

    void settableSet(T result, Exception e);
}
