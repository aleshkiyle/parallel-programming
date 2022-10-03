package com.rsreu.rodin.lab1.logic;

import java.util.Map;

public class FindNumberSpecifiedLettersInString {
    
    private static final String PATTERN_LETTERS = "[^\\p{L}]+";
    
    public static void findNumberOfSpecifiedLettersInString(String phrase, Map<Character, Integer> map) {
        String input = phrase.replaceAll(PATTERN_LETTERS, "");
        for (int i = 0; i < input.length(); i++) {
            Integer numberRepetitionsOfLetterInString = map.get(input.charAt(i));
            if (numberRepetitionsOfLetterInString == null) {
                map.put(input.charAt(i), 1);
            } else {
                map.put(input.charAt(i), ++numberRepetitionsOfLetterInString);
            }
        }
    }
}
