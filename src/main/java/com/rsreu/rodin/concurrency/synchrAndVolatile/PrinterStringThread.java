package com.rsreu.rodin.concurrency.synchrAndVolatile;

import java.util.Arrays;
import java.util.List;

public class PrinterStringThread extends Thread {

    private volatile boolean running = true;

    @Override
    public void run() {
        while (this.running) {
            List<String> namesClassmates = Arrays.asList("Alex", "Ivan", "Artur", "Andrew");
            for (int i = 0; i < namesClassmates.size(); i++) {
                String randomGreetings = namesClassmates.get((int) (Math.random() * 5));
                switch (randomGreetings) {
                    case "Alex" -> System.out.println("Hello, children");
                    case "Ivan" -> System.out.println("Hello, junior developer");
                    case "Artur" -> System.out.println("Hello, Voronezsh boy");
                    case "Andrew" -> System.out.println("Hello, Chess king");
                }
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean shutDown() {
        return this.running = false;
    }
}
