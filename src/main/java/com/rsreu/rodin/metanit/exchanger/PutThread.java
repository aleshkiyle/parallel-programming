package com.rsreu.rodin.metanit.exchanger;

import java.util.concurrent.Exchanger;

public class PutThread implements Runnable {

    private final Exchanger<String> exchanger;
    private String message;

    public PutThread(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        this.message = "Message from PutThread!";
    }

    @Override
    public void run() {
        try {
            message = this.exchanger.exchange(message);
            System.out.println("PutThread has received " + this.message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
