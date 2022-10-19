package com.rsreu.rodin.monitors.metanit;

import java.util.concurrent.Semaphore;

public class Program {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        CommonResource res = new CommonResource();
        new Thread(new CountThread(res, semaphore, "Count thread 1")).start();
        new Thread(new CountThread(res, semaphore, "Count thread 2")).start();
        new Thread(new CountThread(res, semaphore, "Count thread 3")).start();
    }
}
