package com.rsreu.rodin.lab1.logic;

public class SumAllDigitsString {

    public int calculateSumAllDigitsInString(String phrase) {
        int sumNumber = 0;
        for (int i = 0; i < phrase.length(); i++) {
            if (Character.isDigit(phrase.charAt(i))) {
                sumNumber += Character.getNumericValue(phrase.charAt(i));
            }
        }
        return sumNumber;
    }
}
