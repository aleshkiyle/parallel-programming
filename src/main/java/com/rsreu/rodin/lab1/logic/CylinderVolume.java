package com.rsreu.rodin.lab1.logic;

import com.rsreu.rodin.lab1.data.Cylinder;

import java.util.function.Predicate;

public class CylinderVolume {

    public String printCylinderVolume(Cylinder cylinder) {
        if (validationEnteredValues(cylinder)) {
            return String.format("Cylinder volume: %.2f", this.calculateCylinderVolume(cylinder));
        } else {
            return "Incorrect data";
        }
    }

    private double calculateCylinderVolume(Cylinder cylinder) {
        return Math.PI * Math.pow(cylinder.getRadius(), 2) * cylinder.getHeight();
    }

    private boolean validationEnteredValues(Cylinder cylinder) {
        Predicate<Cylinder> cylinderPredicate = cylinderPred -> cylinderPred.getRadius() > 0 &&
                cylinderPred.getHeight() > 0;
        return cylinderPredicate.test(cylinder);
    }
}
