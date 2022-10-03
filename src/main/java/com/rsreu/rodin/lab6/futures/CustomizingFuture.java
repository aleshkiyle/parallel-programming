package com.rsreu.rodin.lab6.futures;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CustomizingFuture<T> implements Future<T> {
    private final Listener<T> listener;
    private final ExecutorService listenerExecutor;
    private final ReadWriteLock readWriteLock;
    private boolean isCanceled;
    private T object;
    private Exception exception;

    private CustomizingFuture(Listener<T> listener, ExecutorService listenerExecutor) {
        this.readWriteLock = new ReentrantReadWriteLock();
        this.isCanceled = false;
        this.listener = listener;
        this.listenerExecutor = listenerExecutor;
    }

    public static <K> CustomizingFuture<K> createSettableFuture(Listener<K> listener, ExecutorService listenerExecutor) {
        return new CustomizingFuture<>(listener, listenerExecutor);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        this.readWriteLock.writeLock().lock();
        try {
            this.isCanceled = true;
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            this.readWriteLock.writeLock().unlock();
        }

    }

    @Override
    public boolean isCancelled() {
        return this.isCanceled;
    }

    @Override
    public boolean isDone() {
        readWriteLock.readLock().lock();
        try {
            return this.object != null;
        } finally {
            this.readWriteLock.readLock().unlock();
        }
    }

    @Override
    public T get() {
        readWriteLock.readLock().lock();
        try {
            return this.object;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void set(T object) {
        this.readWriteLock.writeLock().lock();
        try {
            if (this.exception != null) {
                return;
            }
            this.object = object;
            if (this.listenerExecutor == null) {
                this.listener.settableSet(object, null);
            } else {
                this.listenerExecutor.submit(() -> {
                    System.out.printf("Being listened in with id %s thread \n", Thread.currentThread().getId());
                    System.out.printf("Java thread: %s finished \n", Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getState());
                    listener.settableSet(object, null);
                });
            }
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }

    public void setError(Exception exception) {
        this.readWriteLock.writeLock().lock();
        try {
            if (object != null) {
                return;
            }
            this.exception = exception;
            this.listener.settableSet(null, exception);
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
