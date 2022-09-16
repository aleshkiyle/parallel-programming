package com.rodin.implementComplexComputationalProblem;

public class ProbabilityInCubes {

    private static final int THROW = 9626564;

    public static int getRandomNumber() {
        return (int) (Math.random() * 6) + 1;
    }

    public static void main(String[] args) {
        try {
            Thread.sleep(5000);
            int countMore80 = 0;
            float probability;
            long start = System.currentTimeMillis();
            int[] array = new int[10];
            //List<Integer> cubes = new ArrayList<>(10);
            for (int j = 0; j < THROW; j++) {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    //cubes.add(getRandomNumber());
                    array[i] = getRandomNumber();
                    //sum += cubes.get(i);
                    sum += array[i];
                /*if (i != 0 && cubes.get(i).equals(cubes.get(i - 1))) {
                    cubes.set(i, getRandomNumber());
                    i--;
                }*/
                    if (i != 0 && array[i] == array[i - 1]) {
                        i--;
                    }
                }
                if (sum > 80) {
                    countMore80 += 1;
                }
            }
            probability = (float) countMore80 / THROW;
            long finish = System.currentTimeMillis();
            System.out.printf("%.10f", probability);
            System.out.println();
            System.out.print("Время работы ");
            System.out.print((float) (finish - start) / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}