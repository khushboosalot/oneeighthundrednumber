package com.oneeighthundred;

import com.oneeighthundred.core.OneEightHundredNumberGenerator;
import com.oneeighthundred.data.InputData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private final static Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        /**
         * 1. Initialize Environment
         * 2. Get user's input
         * 3. Process input
         * 4. Print output
         */

        //Initialize logger
        InputData userInput=new InputData(args);
        Stream<String> input = getInput(userInput);
        //Initialize dictionary
        Set<String> dictionary = getDictionary(userInput.getDictionaryPath());
        OneEightHundredNumberGenerator noGenerator = new OneEightHundredNumberGenerator(dictionary);
        Map<String, Set<String>> processedInput=findOneEightHundredNumber(input,noGenerator);
        displayNumber(processedInput);
    }

    /**
     * Initialize the Dictionary
     * @param dictionaryPath
     * @return
     */
    private static Set<String> getDictionary(String dictionaryPath) {
        try (Stream<String> stream = Files.lines(Paths.get(dictionaryPath))) {
            Set<String> dictionaryData = stream.map(data->data.toUpperCase())
                    .collect(Collectors.toSet());
            return dictionaryData;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }

    }

    /**
     * Get user's input
     * @param userInput
     * @return
     */
    private static Stream<String> getInput(InputData userInput){

        Stream<String> input;
        if (userInput.getInputFiles().isEmpty()) {

            LOG.info("Please enter the desired phone numbers, one per line. End the input by typing END.");

            Scanner scanner = new Scanner(System.in);
            List<String> result = new ArrayList<>();
            while (scanner.hasNext()) {
                String in = scanner.nextLine();
                if(in.equals("END")){
                    scanner.close();
                    break;
                }else{
                    result.add(in);
                }
            }
            input = result.stream();
        }else{
            input = userInput.getInputFiles().stream()
                    .flatMap(filePath -> {
                        try {
                            return Files.lines(Paths.get(filePath));
                        } catch (IOException e) {
                            LOG.warning("File " + filePath + " could not be read, skipping.");
                            return Stream.empty();
                        }
                    });
        }
        return input;
    }

    /**
     * Core logic - Find 1-800-Number
     * @param inputs
     * @param noGenerator
     * @return
     */
    private static Map<String, Set<String>> findOneEightHundredNumber(Stream<String> inputs, OneEightHundredNumberGenerator noGenerator ) {
        return inputs
                .map(input -> new AbstractMap.SimpleEntry<>(input, noGenerator.generate(input)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    /**
     * Display 1-800-Number for given input
     * @param processedInput
     */
    private static void displayNumber(Map<String, Set<String>> processedInput) {

        if(processedInput.isEmpty()){
            LOG.info("No match found for given number");
        }

        processedInput.forEach((number, wordsNumbers) -> {
            if (wordsNumbers.isEmpty()) {
                LOG.info(number+": No Match found");
                return;
            }
            String number_1800=getOneEightNumberFromPossibleWords(number,wordsNumbers);
            String output = number +":"+ number_1800;
            LOG.info(output);
        });
    }

    private static String getOneEightNumberFromPossibleWords(String number, Set<String> wordsNumbers) {
        String number_1800=null;
        for(String st:wordsNumbers){
            if(st.length()>=number.length()){
                if(number_1800!=null)
                    number_1800=number_1800+st+",";
                else
                    number_1800=st+",";
            }
        }
        if(number_1800!=null){
            number_1800= number_1800.substring(0,number_1800.length()-1);
        }else{
            number_1800="No Match found";
        }

        return number_1800;
    }


}
