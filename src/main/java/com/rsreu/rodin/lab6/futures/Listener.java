package com.rsreu.rodin.lab6.futures;

@FunctionalInterface
public interface Listener<T> {

    void settableSet(T result, Exception e);
}
