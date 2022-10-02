package com.rodin.threadPoolsAndFutureObjects;

import com.rodin.implementComplexComputationalProblem.ImplementProbabilityInCubes;
import com.rodin.threadPoolsAndFutureObjects.futures.CustomizingFuture;
import com.rodin.threadPoolsAndFutureObjects.futures.FuturesAPI;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProgramRealizationFutureApi {

    private static final Double E = 0.0001;

    public static void main(String[] args) throws InterruptedException {
        new FuturesAPI()
                .submit(() -> {
                            Thread.sleep(1000);
                            ImplementProbabilityInCubes realizationProbability =
                                    new ImplementProbabilityInCubes(E);
                            return realizationProbability.call();
                        },
                        (String res, Exception e) -> System.out.println(res),
                        Executors.newSingleThreadExecutor()
                );
        for (Thread thread: Thread.getAllStackTraces().keySet()) {
            System.out.printf("%s, %s \n", thread.getName(), thread.getState());
        }
    }
}
