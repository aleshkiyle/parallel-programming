package com.rodin.implementComplexComputationalProblem;

import java.util.Random;

public class GenerationNumber {

    private static final Random random = new Random();
    private static final int MAX_VALUE = 6;

    private final Integer randomNumber = initField();

    private Integer initField() {
        return random.nextInt(MAX_VALUE) + 1;
    }

    public int getRandomNumber() {
        return this.randomNumber;
    }
}
