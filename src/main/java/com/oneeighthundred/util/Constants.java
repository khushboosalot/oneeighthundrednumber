package com.oneeighthundred.util;

import java.util.*;

/**
 * Constants
 */
public class Constants {
    public static final String DEFAULT_DICTIONARY_PATH="src\\main\\resources\\default_dictionary.txt";
    public static final String WORD_SEPARATOR="-";
    public static final Map<Character, Set<Character>> KEYPAD = new HashMap<>();
    static {
        KEYPAD.put('2', new HashSet<>(Arrays.asList('A', 'B', 'C')));
        KEYPAD.put('3', new HashSet<>(Arrays.asList('D', 'E', 'F')));
        KEYPAD.put('4', new HashSet<>(Arrays.asList('G', 'H', 'I')));
        KEYPAD.put('5', new HashSet<>(Arrays.asList('J', 'K', 'L')));
        KEYPAD.put('6', new HashSet<>(Arrays.asList('M', 'N', 'O')));
        KEYPAD.put('7', new HashSet<>(Arrays.asList('P', 'Q', 'R', 'S')));
        KEYPAD.put('8', new HashSet<>(Arrays.asList('T', 'U', 'V')));
        KEYPAD.put('9', new HashSet<>(Arrays.asList('W', 'X', 'Y', 'Z')));
    }


}
