package com.rsreu.rodin.lab4;

import com.rsreu.rodin.lab3.ImplementProbabilityInCubes;
import com.rsreu.rodin.lab4.exceptions.IncorrectCommandArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Predicate;

public class ThreadConsoleApplication {

    private static final String REGEX_SPLIT = " ";

    private static final String UNKNOWN_COMMAND = "Input command not found";

    private static final String INCORRECT_INDEX = "Incorrect index of task";

    private Scanner scanner = new Scanner(System.in);

    private Command command = null;

    private final List<Thread> tasks = new ArrayList<>();

    public void runProgram() {
        while (command != Command.EXIT) {
            System.out.println("Input command:");
            String inputString = this.scanner.nextLine();
            String[] args = inputString.split(REGEX_SPLIT);
            command = getCommand(args[0]);
            try {
                switch (command) {
                    case START -> start(args);
                    case STOP -> stop(args);
                    case AWAIT -> await(args);
                    case EXIT -> exit(args);
                    default -> outputCommandTypeError();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException();
            } catch (IndexOutOfBoundsException | IncorrectCommandArgumentException e) {
                System.err.println(INCORRECT_INDEX);
            }
        }
    }


    private void start(String[] args) throws IncorrectCommandArgumentException {
        checkCorrectInputArgument(args);
        ImplementProbabilityInCubes implementProbabilityInCubes
                = new ImplementProbabilityInCubes(Long.parseLong(args[1]));
        Thread implementProbabilityInCubesThread = new Thread(implementProbabilityInCubes);
        implementProbabilityInCubesThread.start();
        this.tasks.add(implementProbabilityInCubesThread);
        System.out.printf("Thread %d start%n", implementProbabilityInCubesThread.getId());
    }


    private void stop(String[] args) throws IncorrectCommandArgumentException, InterruptedException {
        checkCorrectInputArgument(args);
        int numberTask = Integer.parseInt(args[1]);
        stop(numberTask);
    }

    private void stop(int numberTask) throws InterruptedException {
        this.checkCommandAndImplemenmtDependingOneSelectedCommand(numberTask, Command.STOP);
    }


    private void await(String[] args) throws IncorrectCommandArgumentException, InterruptedException {
        checkCorrectInputArgument(args);
        int numberTask = Integer.parseInt(args[1]);
        await(numberTask);
    }

    private void await(int numberTask) throws InterruptedException {
        this.checkCommandAndImplemenmtDependingOneSelectedCommand(numberTask, Command.AWAIT);
    }

    private void checkCommandAndImplemenmtDependingOneSelectedCommand(int numberTask, Command command) throws InterruptedException {
        if (!this.tasks.get(numberTask).isInterrupted()) {
            switch (command) {
                case STOP -> {
                    this.tasks.get(numberTask).interrupt();
                    System.out.println("STOP THREAD");
                }
                case AWAIT -> {
                    System.out.println("AWAIT THREAD");
                    this.tasks.get(numberTask).join();
                }
            }
        }
    }

    private void exit(String[] args) throws IncorrectCommandArgumentException, InterruptedException {
        if (args.length != 1) {
            throw new IncorrectCommandArgumentException();
        }
        exit();
    }

    private void exit() throws InterruptedException {
        this.tasks.forEach(Thread::interrupt);
        for (Thread thread : tasks) {
            thread.join();
        }
    }

    private Command getCommand(String string) {
        try {
            return Command.valueOf(string.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return Command.INCORRECT;
        }
    }

    private static void checkCorrectInputArgument(String[] args) throws IncorrectCommandArgumentException {
        Predicate<String[]> predicateCorrectInputString =
                arguments -> arguments.length == 2 && arguments[1] != null;
        if (predicateCorrectInputString.negate().test(args)) {
            throw new IncorrectCommandArgumentException();
        }
    }

    private void outputCommandTypeError() {
        System.err.println(UNKNOWN_COMMAND);
    }
}
