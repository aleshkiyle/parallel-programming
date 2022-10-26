package com.rsreu.rodin.monitor;

import java.util.concurrent.atomic.AtomicInteger;

public class Program {

    private static final String FIRST_PATH = "C:\\Users\\aleshkiyle\\Desktop\\7 семестр\\Баранчиков\\parallel-programming\\src\\main\\resources\\file1.txt";
    private static final String SECOND_PATH = "C:\\Users\\aleshkiyle\\Desktop\\7 семестр\\Баранчиков\\parallel-programming\\src\\main\\resources\\file2.txt";

    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger();

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

        if (counter.get() < 10) {
            System.out.println("Не найдены 10 символов a");
        } else {
            System.out.println("Найдено 10 символов a");
        }
    }
}
