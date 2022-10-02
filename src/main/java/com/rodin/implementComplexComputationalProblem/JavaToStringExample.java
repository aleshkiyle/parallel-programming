package com.rodin.implementComplexComputationalProblem;

public class JavaToStringExample implements Runnable {

    Thread t;

    public JavaToStringExample() {
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        System.out.println(t.toString());
    }

    public static void main(String[] args) {
        new JavaToStringExample();
    }
}
