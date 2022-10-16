package com.rsreu.rodin.lab4;

import com.rsreu.rodin.lab4.exceptions.IncorrectCommandArgumentException;

public class ProgramThreadConsole {

    private static final String INCORRECT_INDEX = "Incorrect index of task";

    private static final String INCORRECT_NUMBER_FORMAT = "Incorrect number format";

    public static void main(String[] args) {
        ThreadConsoleApplication threadConsoleApplication = new ThreadConsoleApplication();
        try {
            threadConsoleApplication.runProgram();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IndexOutOfBoundsException | IncorrectCommandArgumentException e) {
            System.err.println(INCORRECT_INDEX);
        } catch (NumberFormatException e) {
            System.err.println(INCORRECT_NUMBER_FORMAT);
        }
    }
}

