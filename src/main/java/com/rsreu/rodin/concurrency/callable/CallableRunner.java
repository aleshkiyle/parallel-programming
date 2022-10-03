package com.rsreu.rodin.concurrency.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableRunner {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Callable<Integer> callable = new MyCallable();
//        FutureTask<Integer> futureTask = new FutureTask<>(callable);
//        new Thread(futureTask).start();
//        System.out.println(futureTask.get());
//
//        ExecutorService ex = Executors.newFixedThreadPool(3);
//        Future<String> submit = ex.submit(new ExampleCallable());
//        String s = submit.get();
//        System.out.println(s);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Future<?> submit = executorService.submit(new ImplementCallable());

        Thread.sleep(1000);
        submit.cancel(true);
//        while (!submit.isDone()) {
//            System.out.println("Is not done");
//            Thread.sleep(1000);
//        }

        System.out.println(submit.isCancelled());
        System.out.println("Shutdown");
        executorService.shutdown();
    }
}

class ImplementCallable implements Callable<Integer> {

    @Override
    public Integer call() {
        try {
            System.out.println("Started: " + Thread.currentThread().getId());
            Thread.sleep(5000);
//            long d1 = System.currentTimeMillis();
//            long d2 = System.currentTimeMillis();
//            while (d2 < d1  + 5000) {
//                d2 = System.currentTimeMillis();
//            }
            System.out.println("Finished: " + Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }
        return 99;
    }
}