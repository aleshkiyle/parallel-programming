package com.rsreu.rodin.metanit.mutlithreading.conditionInLocks;

// класс Производитель
public class Producer implements Runnable {

    private Store store;

    public Producer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++) {
            this.store.put();
        }
    }
}
