package com.rsreu.rodin.monitor;

import java.util.concurrent.atomic.AtomicInteger;

public class Program {

    private static final String FIRST_PATH = "C:\\Users\\Родин Алексей\\Desktop\\7 семестр\\Баранчиков. " +
            "Параллельное программирование\\parallel-programming\\src\\main\\resources\\file1.txt";
    private static final String SECOND_PATH = "C:\\Users\\Родин Алексей\\Desktop\\7 семестр\\Баранчиков. " +
            "Параллельное программирование\\parallel-programming\\src\\main\\resources\\file2.txt";

    public static void main(String[] args) {
        final AtomicInteger counter = new AtomicInteger();

        final Thread firstThread = new Thread(new FileReader(FIRST_PATH, counter));
        final Thread secondThread = new Thread(new FileReader(SECOND_PATH, counter));

        firstThread.start();
        secondThread.start();

        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Counter symbol: " + counter.get());
        if (counter.get() < 10) {
            System.out.println("Ten characters not found");
        } else {
            System.out.println("Ten characters found");
        }
    }
}
