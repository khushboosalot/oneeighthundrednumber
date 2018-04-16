import com.oneeighthundred.core.OneEightHundredNumberGenerator;
import com.oneeighthundred.util.Constants;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class PossibleWordGenerationTest {
    private OneEightHundredNumberGenerator oneEightHundredNumberGenerator;
    @Before
    public void setUp() throws Exception {
        Set<String> dictionaryData;
        try (Stream<String> stream = Files.lines(Paths.get(Constants.DEFAULT_DICTIONARY_PATH))) {
            dictionaryData = stream.map(data->data.toUpperCase())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            dictionaryData = Collections.emptySet();
        }
        this.oneEightHundredNumberGenerator = new OneEightHundredNumberGenerator(dictionaryData);
    }

    @Test
    public void replaceNumberWithLetters1() throws Exception{
        String input="3";
        Set<String> expected = new HashSet<>();
        expected.add("D");
        expected.add("E");
        expected.add("F");

        Set<String> actual= oneEightHundredNumberGenerator.replaceNumbers(input);
        assertEquals(expected,actual);
    }

    @Test
    public void replaceNumberWithLetters2() throws Exception{
        String input="22";
        Set<String> expected = new HashSet<>();
        expected.add("AA");
        expected.add("AB");
        expected.add("AC");
        expected.add("BA");
        expected.add("BB");
        expected.add("BC");
        expected.add("CA");
        expected.add("CB");
        expected.add("CC");

        Set<String> actual= oneEightHundredNumberGenerator.replaceNumbers(input);
        assertEquals(expected,actual);
    }


    @Test
    public void getPossibleWordsFromDictionary() throws Exception {
        String input="225563";
        Set<String> expected=new HashSet<String>();
        expected.add("CALL");
        expected.add("ME");
        expected.add("CALL-ME");
        expected.add("");
        Set<String> possibleWords=oneEightHundredNumberGenerator.generate(input);
       assertEquals(expected,possibleWords);

    }



}
