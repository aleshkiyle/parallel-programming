package com.rsreu.rodin.monitors.metanit.philosopherTask;

import java.util.concurrent.Semaphore;

public class Program {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 1; i < 6; i++) {
            Philosopher philosopher = new Philosopher(semaphore, i);
            philosopher.start();
        }
    }
}
