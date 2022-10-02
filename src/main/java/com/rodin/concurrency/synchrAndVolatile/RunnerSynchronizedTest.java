package com.rodin.concurrency.synchrAndVolatile;

public class RunnerSynchronizedTest {

    private static Integer number = 0;

    public static void main(String[] args) {
        Thread thread1 = new Thread(new WorkerIncrementNumber());
        Thread thread2 = new Thread(new WorkerIncrementNumber());
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(number);
    }

    public static synchronized void incrementNumber() {
        number++;
    }

}
