package com.oneeighthundred.core;

import com.oneeighthundred.data.PhoneNumber;
import com.oneeighthundred.util.Constants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OneEightHundredNumberGenerator {

    private final Set<String> dictionary;


    public OneEightHundredNumberGenerator(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public Set<String> generate(String number) {
        return splitNumberProcess(number)
                .flatMap(this::getWordNumbers)
                .collect(Collectors.toSet());

    }
    public Stream<PhoneNumber> splitNumberProcess(String number){
        return splitNumber(number).stream();
    }

    public Set<PhoneNumber> splitNumber(String number) {
        String returnNumber=validateNumber(number);

        //Process only if number is valid
        if(returnNumber!=null){
            return splitPhoneNumberStart(number);
        }else{
            return Collections.emptySet();
        }

    }

    public String validateNumber(String number){
        String returnNumber=null;
        //Remove punctuations or whitespace from number
        number = number.replaceAll("\\p{Punct}", "").replaceAll("\\s", "");
        //Remove prefix if available
        if (number.startsWith("1800")) {
            number= number.substring(4);
        }
        if(number.matches("\\d+")){
           returnNumber=number;
        }
        return returnNumber;
    }

    private Set<PhoneNumber> splitPhoneNumberStart(String number) {
        Set<PhoneNumber> result = new HashSet<>();

        Integer firstDigit = Integer.parseInt(number.substring(0, 1));
        Integer lastDigit = Integer.parseInt(number.substring(number.length() - 1, number.length()));
        String withFirstDigit = number.substring(1);
        String withLastDigit = number.substring(0, number.length() - 1);
        String withFirstAndLastDigit = number.substring(1, number.length() - 1);

        result.add(new PhoneNumber(firstDigit, withFirstDigit));
        result.add(new PhoneNumber(withLastDigit, lastDigit));
        result.add(new PhoneNumber(firstDigit, withFirstAndLastDigit, lastDigit));
        result.addAll(splitPhoneNumber(number));

        return result;
    }

    /**
     * Generate all the possible for the number
     * @param number
     * @return possible numbers
     */
    private Set<PhoneNumber> splitPhoneNumber(String number){
        Set<PhoneNumber> result = new HashSet<>();

        if (number.length() == 0) {
            return result;
        }

        result.add(new PhoneNumber(number));

        if (number.length() == 1) {
            return result;
        }

        for (int split = 1; split < number.length(); split++) {
            String leftString = number.substring(0, split);
            String rightString = number.substring(split, number.length());
            Set<PhoneNumber> leftParts = splitPhoneNumber(leftString);
            Set<PhoneNumber> rightParts = splitPhoneNumber(rightString);
            result.addAll(merge(leftParts, rightParts, null));

            String leftStringDigit = number.substring(0, split);
            Integer digit = Integer.parseInt(number.substring(split, split + 1));
            String rightStringDigit = number.substring(split + 1, number.length());
            Set<PhoneNumber> leftPartsDigit = splitPhoneNumber(leftStringDigit);
            Set<PhoneNumber> rightPartsDigit = splitPhoneNumber(rightStringDigit);
            result.addAll(merge(leftPartsDigit, rightPartsDigit, digit));
        }

        return result;
    }

    private Set<PhoneNumber> merge(Set<PhoneNumber> leftParts, Set<PhoneNumber> rightParts, Integer digit) {
        Set<PhoneNumber> result = new HashSet<>();
        for (PhoneNumber leftPart : leftParts) {
            for (PhoneNumber rightPart : rightParts) {
                if (digit == null) {
                    result.add(new PhoneNumber(leftPart, rightPart));
                } else {
                    result.add(new PhoneNumber(leftPart, digit, rightPart));
                }
            }
        }
        return result;
    }


    /**
     * Get words for all the possible values of number
     * @param phoneNumber
     * @return possible words
     */
    public Stream<String> getWordNumbers(PhoneNumber phoneNumber) {

        Set<String> potentialWords = new HashSet<>(Collections.singletonList(""));
        for (Object numberPart : phoneNumber.getParts()) {
            Set<String> potentialWordsNew = new HashSet<>();
            Set<String> wordParts = getWordsForPart(numberPart);
            if(!wordParts.isEmpty()){
                for (String potentialWord : potentialWords) {
                    for (String wordPart : wordParts) {
                        potentialWordsNew.add(potentialWord +
                                (potentialWord.length() > 0 ? "-" : "") +
                                wordPart);
                    }
                }
                if(!potentialWordsNew.isEmpty()){
                    potentialWords = potentialWordsNew;
                }

            }
        }


        return potentialWords.stream();

    }

    /**
     * Get words and check the same in the dictionary
     * @param numberPart
     * @return set of words available in dictionary
     */
    private Set<String> getWordsForPart(Object numberPart) {
        Set<String> result;
        if (numberPart instanceof String) {
            result = apply((String) numberPart)
                    .filter(dictionary::contains)
                    .collect(Collectors.toSet());
        }/*else if (numberPart instanceof Integer) {
            result = new HashSet<>(Collections.singletonList(numberPart.toString()));
            return result;
        }*/else {
            result = Collections.emptySet();
        }
        return result;
    }

    /**
     * Check for the invalid
     * @param number
     * @return set of possible words
     */
    private Stream<String> apply(String number) {
        if (!number.matches("[2-9]+")) {
            return Stream.empty();
        }

        return replaceNumbers(number).stream();
    }

    /**
     * Replace number with the keypad
     * @param number
     * @return possible words
     */
    public Set<String> replaceNumbers(String number) {
        Set<String> potentialWords = new HashSet<>(Collections.singletonList(""));
        for (char digit : number.toCharArray()) {
            Set<String> newPotentialWords = new HashSet<>();
            for (String potentialWord : potentialWords) {
                for (char replaceable : Constants.KEYPAD.get(digit)) {
                    newPotentialWords.add(potentialWord + replaceable);
                }
            }

            potentialWords = newPotentialWords;


        }

        return potentialWords;
    }
}















