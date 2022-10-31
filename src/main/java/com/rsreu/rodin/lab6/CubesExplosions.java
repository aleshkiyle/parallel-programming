package com.rsreu.rodin.lab6;

import com.rsreu.rodin.generalLogic.UtilityCalculateProbabilityInCubesLogic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class CubesExplosions {

    private CountDownLatch countDownLatch;

    private Semaphore semaphore;

    private ReentrantLock reentrantLock;

    private ProgressLogger progressLogger;

    public CubesExplosions(CountDownLatch countDownLatch, Semaphore semaphore,
                           ReentrantLock reentrantLock, ProgressLogger progressLogger) {
        this.countDownLatch = countDownLatch;
        this.semaphore = semaphore;
        this.reentrantLock = reentrantLock;
        this.progressLogger = progressLogger;
    }

    public double calculateExceedProbability(long testCount) {
        double result = 0;
        try {
            this.semaphore.acquire();
            UtilityCalculateProbabilityInCubesLogic utilityCalculateProbabilityInCubesLogic =
                    new UtilityCalculateProbabilityInCubesLogic();

            long exceededCount = 0;
            for (int i = 0; i < testCount; i++) {
                if (Thread.interrupted()) {
                    break;
                }

                if (utilityCalculateProbabilityInCubesLogic.makeExperiment()) {
                    exceededCount++;
                }
            }
            result = (double) exceededCount / testCount;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            this.semaphore.release();
        }
        this.progressLogger.updateProgress();

        countDownLatch.countDown();
        long endTime = System.currentTimeMillis();
        try {
            this.countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long resultTime = System.currentTimeMillis() - endTime;
        System.out.printf("Время для задачи в миллисекундах %s: %s%n", Thread.currentThread().getId(), resultTime);
        return result;
    }
}
