package com.oneeighthundred.data;

import java.util.Arrays;
import java.util.List;
import com.oneeighthundred.util.Constants;

public class InputData {
    /**
     * Input file
     */
    private final List<String> inputFiles;
    /**
     * Dictionary path
     */
    private final String dictionaryPath;

    /**
     * Process command line arguments
     * @param arguments
     */
    public InputData(String[] arguments) {
        List<String> lstArgs= Arrays.asList(arguments);
        if (lstArgs.size() > 1 && "-d".equals(lstArgs.get(0))) {
            dictionaryPath = lstArgs.get(1);
            inputFiles = lstArgs.subList(2, lstArgs.size());
        } else {
            dictionaryPath = Constants.DEFAULT_DICTIONARY_PATH;
            inputFiles = lstArgs;
        }
    }

    /**
     * Getter for input file
     * @return
     */
    public List<String> getInputFiles() {
        return inputFiles;
    }

    /**
     * Getter for dictionary path
     * @return
     */
    public String getDictionaryPath() {
        return dictionaryPath;
    }
}
