package com.rsreu.rodin.lab6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProgressLogger {

    private final int partCount;
    private int executedPartCount = 0;

    private final Lock lock = new ReentrantLock();

    public ProgressLogger(int partCount) {
        this.partCount = partCount;
    }

    public void updateProgress() {
        this.lock.lock();
        this.executedPartCount += 1;
        String progressString = String.format("Progress %d/%d-%.2f%%%n", this.executedPartCount, partCount,
                executedPartCount / (double) partCount * 100);
        System.out.printf(progressString);
        this.lock.unlock();
    }
}
