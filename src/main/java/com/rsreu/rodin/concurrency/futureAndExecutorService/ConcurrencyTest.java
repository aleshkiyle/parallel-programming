package com.rsreu.rodin.concurrency.futureAndExecutorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrencyTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<String> resultProperties = new ArrayList<>();
        List<Future<String>> futures = new ArrayList<>();

        final ExecutorService service = Executors.newFixedThreadPool(2);

        System.out.println("START");

        try {
            futures.add(service.submit(new PhotoProperty()));
            futures.add(service.submit(new MarketProperty()));
            futures.add(service.submit(new MarketProperty()));
            futures.add(service.submit(new PhotoProperty()));


            for (Future<String> stringFuture : futures) {
                System.out.println(stringFuture.isDone());
                resultProperties.add(stringFuture.get());
            }
        } finally {
            service.shutdown();
        }

        System.out.println("STOP");

        for (String str : resultProperties) {
            System.out.println(str);
        }
    }
}


