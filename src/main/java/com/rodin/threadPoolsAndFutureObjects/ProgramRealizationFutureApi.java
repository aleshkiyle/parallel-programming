package com.rodin.threadPoolsAndFutureObjects;

import com.rodin.implementComplexComputationalProblem.ImplementProbabilityInCubes;
import com.rodin.threadPoolsAndFutureObjects.futures.CustomizingFuture;
import com.rodin.threadPoolsAndFutureObjects.futures.FuturesAPI;
import com.rodin.threadPoolsAndFutureObjects.futures.Listener;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProgramRealizationFutureApi {

    private static final Double E = 0.0001;

    public static void main(String[] args) {
        ExecutorService listenerExecutor = Executors.newSingleThreadExecutor();
        Listener<String> stringListener = (String res, Exception e) -> System.out.println(res);
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            ImplementProbabilityInCubes realizationProbability =
                    new ImplementProbabilityInCubes("Future thread", E);
            return (realizationProbability.call());
        };
        try {
            new FuturesAPI()
                    .submit(callable,
                            stringListener,
                            listenerExecutor
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // implementExecutorService();
    }

    private static void implementExecutorService() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> {
            ImplementProbabilityInCubes implementProbabilityInCubes =
                    new ImplementProbabilityInCubes("Future thread", E);
            return implementProbabilityInCubes.call();
        });
        try {
            String s = future.get();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
