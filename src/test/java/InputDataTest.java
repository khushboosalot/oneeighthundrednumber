import com.oneeighthundred.data.InputData;
import com.oneeighthundred.util.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InputDataTest {



    @Test
    public void noArgumentsTest() throws Exception {
        String [] args=new String[]{};
        InputData userInput = new InputData(args);
        assertEquals("Default dictionary is not available",Constants.DEFAULT_DICTIONARY_PATH,userInput.getDictionaryPath());
        assertTrue("File list is not empty",userInput.getInputFiles().isEmpty());
    }


    @Test
    public void AddDictionaryTest() throws Exception {
        String dictionaryPath = "newDictionary.txt";
        InputData userInput = new InputData(new String[]{"-d", dictionaryPath});
        assertEquals("Provided dictionary is not taken",dictionaryPath,userInput.getDictionaryPath());
        assertTrue("File list is not empty",userInput.getInputFiles().isEmpty());
    }

    @Test
    public void AddOneFileTest() throws Exception {
        String file = "inputFile.txt";
        InputData userInput = new InputData(new String[]{file});
        assertEquals("Default dictionary is not taken", Constants.DEFAULT_DICTIONARY_PATH,userInput.getDictionaryPath());
        assertEquals("File list does not have exact 1 file",1,userInput.getInputFiles().size());
        assertTrue("File list does not contain provided file",userInput.getInputFiles().contains(file));
    }

    @Test
    public void AddTwoFilesTest() throws Exception {
        String file1 = "inputFile1.txt";
        String file2 = "inputFile2.txt";
        InputData userInput = new InputData(new String[]{file1, file2});
        assertEquals("Default dictionary is not taken",Constants.DEFAULT_DICTIONARY_PATH,userInput.getDictionaryPath());
        assertEquals("File list does not contain exactly 2 items",2,userInput.getInputFiles().size());
        assertTrue("File list does not contain provided file 1",userInput.getInputFiles().contains(file1));
        assertTrue("File list does not contain provided file 2",userInput.getInputFiles().contains(file2));
    }

    @Test
    public void AddDictAndOneFileTest() throws Exception {
        String dictionaryPath = "newDictionary.txt";
        String file = "inputFile.txt";
        InputData userInput = new InputData(new String[]{"-d", dictionaryPath, file});
        assertEquals("Provided dictionary is not taken",dictionaryPath,userInput.getDictionaryPath());
        assertEquals("File list does not contain exactly 1 item",1,userInput.getInputFiles().size());
        assertTrue("File list does not contain provided file",userInput.getInputFiles().contains(file));
    }

    @Test
    public void AddDictAndTwoFilesTest() throws Exception {
        String dictionaryPath = "newDictionary.txt";
        String file1 = "inputFile1.txt";
        String file2 = "inputFile2.txt";
        InputData userInput = new InputData(new String[]{"-d", dictionaryPath, file1, file2});
        assertEquals("Provided dictionary is not taken",dictionaryPath,userInput.getDictionaryPath());
        assertEquals("File list does not have 2 items",2,userInput.getInputFiles().size());
        assertTrue("File list does not contain file 1",userInput.getInputFiles().contains(file1));
        assertTrue("File list does not contain file 2",userInput.getInputFiles().contains(file2));
    }
}
