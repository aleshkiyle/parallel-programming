package com.rodin.concurrency.synchrAndVolatile;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        PrinterStringThread printerStringThread =
                new PrinterStringThread();
        printerStringThread.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        printerStringThread.shutDown();
    }
}
