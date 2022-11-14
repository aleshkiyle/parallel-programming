package com.rsreu.rodin.metanit.mutlithreading.conditionInLocks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Store {

    private int product = 0;
    private ReentrantLock reentrantLock;
    private Condition condition;

    public Store() {
        this.reentrantLock = new ReentrantLock(); // создаём блокировку
        this.condition = reentrantLock.newCondition(); // получаем условие, связанное с блокировкой
    }

    public void get() {
        this.reentrantLock.lock();
        try {
            while (product < 1)
                condition.await();
            product--;
            System.out.println("Покупатель купил 1 товар");
            System.out.println("Товаров на складе: " + product);

            // сигнализируем
            this.condition.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            this.reentrantLock.unlock();
        }
    }

    public void put() {
        this.reentrantLock.lock();
        try {
            while (product >= 3)
                condition.await();
            product++;
            System.out.println("Производитель добавил 1 товар");
            System.out.println("Товаров на складе: " + product);

            // сигнализируем
            condition.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
