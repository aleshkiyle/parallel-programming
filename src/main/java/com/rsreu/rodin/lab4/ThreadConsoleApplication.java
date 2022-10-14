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

    private static final String INCORRECT_NUMBER_FORMAT = "Incorrect number format";

    private Scanner scanner = new Scanner(System.in);

    private Command command = null;

    private final List<Thread> tasks = new ArrayList<>();

    public void runProgram() {
        while (command != Command.EXIT) {
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
            } catch (IndexOutOfBoundsException | IncorrectCommandArgumentException e) {
                System.err.println(INCORRECT_INDEX);
            } catch (NumberFormatException e) {
                System.err.println(INCORRECT_NUMBER_FORMAT);
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
        System.out.printf("Task %s STARTED%n", this.tasks.indexOf(implementProbabilityInCubesThread));
    }


    private void stop(String[] args) throws IncorrectCommandArgumentException {
        checkCorrectInputArgument(args);
        int numberTask = Integer.parseInt(args[1]);
        this.tasks.get(numberTask).interrupt();
        System.out.printf("STOP THREAD %d %n", this.tasks.get(numberTask).getId());
    }


    private void await(String[] args) throws IncorrectCommandArgumentException, InterruptedException {
        checkCorrectInputArgument(args);
        int numberTask = Integer.parseInt(args[1]);
        System.out.printf("AWAIT THREAD %d %n", this.tasks.get(numberTask).getId());
        this.tasks.get(numberTask).join();
    }

    private void exit(String[] args) throws IncorrectCommandArgumentException, InterruptedException {
        if (args.length != 1) {
            throw new IncorrectCommandArgumentException();
        }
        this.tasks.forEach(Thread::interrupt);
        for (Thread thread : tasks) {
            thread.join();
            System.out.printf("Thread with id %d FINIsSHED %n", thread.getId());
        }
    }

    private Command getCommand(String string) {
        try {
            return Command.valueOf(string.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return Command.INCORRECT;
        }
    }

    private static void checkCorrectInputArgument(String[] args) throws IncorrectCommandArgumentException, NumberFormatException {
        Predicate<String[]> predicateCorrectInputString =
                arguments -> arguments.length == 2 && arguments[1] != null && Integer.parseInt(args[1]) >= 0;
        if (predicateCorrectInputString.negate().test(args)) {
            throw new IncorrectCommandArgumentException();
        }
    }

    private void outputCommandTypeError() {
        System.err.println(UNKNOWN_COMMAND);
    }
}
