package com.rsreu.rodin.lab1;

import com.rsreu.rodin.lab1.data.Circle;
import com.rsreu.rodin.lab1.data.Cylinder;
import com.rsreu.rodin.lab1.logic.AreaCircle;
import com.rsreu.rodin.lab1.logic.CylinderVolume;
import com.rsreu.rodin.lab1.logic.SumAllDigitsString;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.rsreu.rodin.lab1.logic.FindNumberSpecifiedLettersInString.findNumberOfSpecifiedLettersInString;

public class Runner {

    private static final double CIRCLE_RADIUS = 3.0;
    private static final double CYLINDER_RADIUS = 2.84;
    private static final double CYLINDER_HEIGHT = 3.3;
    private static final String INPUT_STRING = "Hello, world";
    private static final Map<Character, Integer> MAP = new HashMap<>();


    public static void main(String[] args) {
        calculateAreaCircle();
        calculateCylinderVolume();
        findNumberSpecifiedLettersInString();
        calculateSumOfAllDigitsInString();
    }

    private static void calculateAreaCircle() {
        AreaCircle areaCircle = new AreaCircle();
        Circle circle = Circle.builder()
                .radius(CIRCLE_RADIUS)
                .build();

        String resultAreaCircle = areaCircle.printAreaCircle(circle);
        System.out.println(resultAreaCircle);
    }

    private static void calculateCylinderVolume() {
        CylinderVolume cylinderVolume = new CylinderVolume();
        Cylinder cylinder = Cylinder.builder()
                .radius(CYLINDER_RADIUS)
                .height(CYLINDER_HEIGHT)
                .build();
        String resultCylinderVolume = cylinderVolume.printCylinderVolume(cylinder);
        System.out.println(resultCylinderVolume);
    }

    private static void findNumberSpecifiedLettersInString() {
        findNumberOfSpecifiedLettersInString(INPUT_STRING, MAP);
        System.out.println(MAP);
    }

    private static void calculateSumOfAllDigitsInString() {
        SumAllDigitsString sumAllDigitsString =
                new SumAllDigitsString();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input string: ");
        String str = scanner.next();
        System.out.println(sumAllDigitsString.calculateSumAllDigitsInString(str));
    }
}
