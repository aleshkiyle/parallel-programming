package com.rodin.mavenBasics.artur;

public class Main {

    public static void main(String[] args) {
        Circle circle = new Circle();
        double area = circle.findArea(5);
        System.out.println("Площадь = " + area);
    }
}
