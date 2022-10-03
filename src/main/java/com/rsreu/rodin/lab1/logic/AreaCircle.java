package com.rsreu.rodin.lab1.logic;

import com.rsreu.rodin.lab1.data.Circle;

import java.util.function.Predicate;

public class AreaCircle {

    public String printAreaCircle(Circle circle) {
        if (validationEnteredValues(circle)) {
            return String.format("Area circle: %.2f", this.calculateAreaCircle(circle));
        } else {
            return "Incorrect data";
        }
    }

    private double calculateAreaCircle(Circle circle) {
        return Math.PI * Math.pow(circle.getRadius(), 2);
    }

    private boolean validationEnteredValues(Circle circle) {
        Predicate<Circle> circlePredicate = circlePred -> circlePred.getRadius() > 0;
        return circlePredicate.test(circle);
    }
}
