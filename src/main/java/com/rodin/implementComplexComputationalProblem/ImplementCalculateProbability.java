package com.rodin.implementComplexComputationalProblem;

public class ImplementCalculateProbability {

    public float implementTask(int THROW) {
        GenerationNumber generationNumber = new GenerationNumber();
        int countMore80 = 0;
        int[] array = new int[10];
        for (int j = 0; j < THROW; j++) {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                array[i] = generationNumber.getRandomNumber();
                sum += array[i];
                if (i != 0 && array[i] == array[i - 1]) {
                    i--;
                }
            }
            if (sum > 80) {
                countMore80++;
            }
        }
        return (float) countMore80 / THROW;
    }
}
