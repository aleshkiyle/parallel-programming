package com.rsreu.rodin.lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.logging.Logger;

public class ThreadControlUsingExecutorService {

    private static final Integer COUNT_THREADS = 4;

    private static final Logger LOGGER =
            Logger.getLogger(ThreadControlUsingExecutorService.class.getName());

    public static double startThreadUsingExecutors(long testCount) {
        long testForTask = testCount / COUNT_THREADS;
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Function<Long, Double> calculateExceedProbability = CubesExplosions::calculateExceedProbability;
        List<Callable<Double>> tasks = new ArrayList<>();
        LOGGER.info("Задача будет разбита на " + COUNT_THREADS + " части");
        int[] parts = {0};
        for (int i = 0; i < COUNT_THREADS; i++) {
            tasks.add(() -> {
                long start = System.currentTimeMillis();
                double result = calculateExceedProbability.apply(testForTask);
                synchronized (parts) {
                    parts[0]++;
                }
                System.out.println("Часть " + parts[0] + " выполнена!, Время: " + (System.currentTimeMillis() - start) / 1_000);
                return result;
            });
        }

        List<Future<Double>> futures = new ArrayList<>();
        try {
            futures = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(e);
        }

        final double[] resultProbability = {0};
        futures.forEach(
                f -> {
                    try {
                        resultProbability[0] += f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        ;
                    }
                });
        resultProbability[0] /= COUNT_THREADS;
        executorService.shutdown();
        return resultProbability[0];
    }
}
