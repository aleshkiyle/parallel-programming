package com.rsreu.rodin.metanit.exchanger;

import java.util.concurrent.Exchanger;

public class ExchangerRunner {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(new GetThread(exchanger)).start();
        new Thread(new PutThread(exchanger)).start();
    }
}
