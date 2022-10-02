package com.rodin.implementComplexComputationalProblem;

public class ProgramImplementQuestion {

    public static void main(String[] args) {
        int nThreads = Thread.getAllStackTraces().keySet().size();
        System.out.println("Количество запущенных потоков: " + nThreads);

        TestThread testThread = new TestThread();
        TestThread testThread1 = new TestThread();
        System.out.println(testThread.getName());
        System.out.println(testThread1.getName());
        testThread.setName("TestThreadName");
        System.out.println(testThread.getName());

        System.out.println(calcRunnableThreads());
    }

    // Все потоки выполняются в настоящий момент
    private static int calcRunnableThreads() {
        int thrRunning = 0;
        for (Thread thread: Thread.getAllStackTraces().keySet()) {
            if (thread.getState() == Thread.State.RUNNABLE) {
                thrRunning++;
            }
        }
        return thrRunning;
    }
}
