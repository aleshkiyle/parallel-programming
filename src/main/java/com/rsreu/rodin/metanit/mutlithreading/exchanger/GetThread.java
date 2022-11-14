package com.rsreu.rodin.metanit.mutlithreading.exchanger;

import java.util.concurrent.Exchanger;

public class GetThread implements Runnable {

    private final Exchanger<String> exchanger;

    private String message;

    public GetThread(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        this.message = "Hello from GetThread!";
    }


    @Override
    public void run() {
        try {
            this.message = this.exchanger.exchange(message);
            System.out.println("GetThread has received " + message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
