package com.rodin.mavenBasics;

import com.rodin.mavenBasics.data.Circle;
import com.rodin.mavenBasics.data.Cylinder;
import com.rodin.mavenBasics.logic.AreaCircle;
import com.rodin.mavenBasics.logic.CylinderVolume;

import java.util.HashMap;
import java.util.Map;

import static com.rodin.mavenBasics.logic.FindNumberSpecifiedLettersInString.findNumberOfSpecifiedLettersInString;

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
}
