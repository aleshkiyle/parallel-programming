package com.rodin.mavenBasics.artur;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @RepeatedTest(50)
    void findArea() {
        Circle circle = new Circle();
        double actual = circle.findArea(5);
        double expected = 78.5;
        double delta = 0.0001;
        assertEquals(expected, actual, delta);
    }
}