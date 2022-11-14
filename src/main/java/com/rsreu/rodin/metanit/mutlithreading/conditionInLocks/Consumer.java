package com.rsreu.rodin.metanit.mutlithreading.conditionInLocks;

// класс Потребитель
public class Consumer implements Runnable {

    private Store store;

    public Consumer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++) {
            this.store.get();
        }
    }
}
