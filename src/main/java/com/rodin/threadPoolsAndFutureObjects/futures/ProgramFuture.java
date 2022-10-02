package com.rodin.threadPoolsAndFutureObjects.futures;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ProgramFuture {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new FuturesAPI()
                .submit(() -> {
                            Thread.sleep(1000);
                            return getRandomNumber();
                        },
                        (String res, Exception e) -> System.out.println(res),
                        Executors.newSingleThreadExecutor()
                );
    }

    private static String getRandomNumber() {
        double randomNumber = Math.random() * 100 - 1;
        return String.format("Random number: %.1f", Math.floor(randomNumber));
    }
}
