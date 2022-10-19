package com.rsreu.rodin.monitors.metanit.philosopherTask;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Philosopher extends Thread {

    // Семафор, ограничивающий число философов
    private final Semaphore semaphore;

    // количество приёмов пищи
    private int num = 0;

    // идентификатор филисофа
    private final long id;

    public Philosopher(Semaphore semaphore, long id) {
        this.semaphore = semaphore;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (num < 3) {
                this.semaphore.acquire();
                System.out.println("Философ " + this.id + " садится за стол");
                TimeUnit.MILLISECONDS.sleep(1000);
                this.num++;
                System.out.println("-----------------");
                System.out.println("Филиософ " + this.id + " выходит из стола!");
                this.semaphore.release();
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Проблемы");
        }
    }
}
