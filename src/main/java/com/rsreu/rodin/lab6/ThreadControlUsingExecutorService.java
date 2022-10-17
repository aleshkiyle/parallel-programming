package com.rsreu.rodin.lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

public class ThreadControlUsingExecutorService {

    private static final Integer COUNT_THREADS = 6;

    private static final ExecutorService THREAD_POOL =
            Executors.newFixedThreadPool(COUNT_THREADS);

    private static final List<Callable<Double>> TASKS
            = new ArrayList<>();

    public static double startThreadUsingExecutors(long testCount) throws InterruptedException {
        long testForTask = testCount / COUNT_THREADS;
        splitTasksByThreadsFromThreadPool(testForTask);

        List<Future<Double>> futures = getFutures(TASKS);
        final double[] resultProbability = getResultProbability(futures);
        return resultProbability[0];
    }

    private static void splitTasksByThreadsFromThreadPool(long testForTask) {
        Function<Long, Double> calculateExceedProbability = CubesExplosions::calculateExceedProbability;
        System.out.println("Tasks are divided into " + COUNT_THREADS + " tasks");
        int[] parts = {0};
        for (int i = 0; i < COUNT_THREADS; i++) {
            TASKS.add(() -> {
                double start = System.currentTimeMillis();
                double result = calculateExceedProbability.apply(testForTask);
                parts[0]++;
                System.out.printf("Task %d complete, Time: %.3f, Result task = %.5f%n", parts[0],
                        (System.currentTimeMillis() - start) / 1_000, result / COUNT_THREADS);
                return result;
            });
        }
    }

    private static List<Future<Double>> getFutures(List<Callable<Double>> tasks)
            throws InterruptedException {
        return THREAD_POOL.invokeAll(tasks);
    }

    private static double[] getResultProbability(List<Future<Double>> futures) {
        final double[] resultProbability = {0};
        futures.forEach(
                future -> {
                    try {
                        resultProbability[0] += future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                });
        resultProbability[0] /= COUNT_THREADS;
        THREAD_POOL.shutdown();
        return resultProbability;
    }




}
