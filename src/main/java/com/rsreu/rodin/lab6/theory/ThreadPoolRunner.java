package com.rsreu.rodin.lab6.theory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1. Что такое Future? Что отображает этот интерфейс?
        // Future представляет собой результат асинхронного вычисления. Этот результат появляется в
        // конечном итоге в Future после заврешения обработки.
        // Интерфейс java.util.concurrent.Future описывает API для работы с задачами, результат которых мы планируем получить
        // в будущем: методы получения результата, методы проверки статуса.

        // 2.
        // Future<?> get() - Блокирующий метод

        // 8. Как завершить все потоки, работающие в пуле?
        // shutDown() - Инициирует упорядоченное отключение, при котором ранее отправленные задачи выполняются,
        // но новые задачи не принимаются. Вызов не имеет дополнительного эффекта, если он уже выключен.
        // Этот метод не ожидает завершения выполнения ранее отправленных задач. Для этого используйте awaitTermination.
        // shutDownNow() - Пытается остановить все активно выполняющиеся задачи, останавливает обработку ожидающих задач
        // и возвращает список задач, ожидающих выполнения.
        // Этот метод не ожидает завершения активно выполняющихся задач. Для этого используйте awaitTermination.
        // Нет никаких гарантий, кроме попыток остановить обработку активно выполняющихся задач. Например,
        // типичные реализации будут отменяться с помощью Thread.interrupt(), поэтому любая задача,
        // которая не отвечает на прерывания, может никогда не завершиться
    }

    private static void test1() throws ExecutionException, InterruptedException {
        FutureTask<String> stringFuture = new FutureTask<>(() -> "Callable");
        new Thread(stringFuture).start();
        System.out.println(stringFuture.get());

        Callable<String> callable1 = () -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return "test1";
        };

        Callable<String> callable2 = () -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return "test2";
        };

        Callable<String> callable3 = () -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return "test3";
        };


        List<Callable<String>> callableList = new ArrayList<>();
        callableList.add(callable1);
        callableList.add(callable2);
        callableList.add(callable3);

        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<Future<String>> futures = threadPool.invokeAll(callableList);
        StringBuilder str = new StringBuilder();
        for (Future<String> future: futures) {
            str.append(future.get());
        }
        System.out.println(str);
        threadPool.shutdown();

        Future<Integer> future = threadPool.submit(() -> 2 * 3);
        future.cancel(true);
        System.out.println(future.get());
    }

    private static void test2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> submit = executorService.submit(() -> "Hello");
        String s = submit.get();
        System.out.println(s);
    }

    private static void test3() {
        ExecutorService service =
                Executors.newCachedThreadPool();
        Future<String> future = service.submit(() -> {
            String str = "Test ";
            int number = 3;
            return str + number;
        });
        boolean done = future.isDone();
        System.out.println(done);
    }

    private static void test4() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 3);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
