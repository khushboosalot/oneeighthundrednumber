import com.oneeighthundred.core.OneEightHundredNumberGenerator;
import com.oneeighthundred.data.PhoneNumber;
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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class PhoneNumberSplitTest {
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
    public void splitNumber() throws Exception {
        String input="123";
        Set<PhoneNumber> expected = new HashSet<>();
        expected.add(new PhoneNumber("1", "2", "3"));
        expected.add(new PhoneNumber("1", 2, "3"));
        expected.add(new PhoneNumber(1, "2", 3));
        expected.add(new PhoneNumber("1", "23"));
        expected.add(new PhoneNumber(1, "23"));
        expected.add(new PhoneNumber("12", "3"));
        expected.add(new PhoneNumber("12", 3));
        expected.add(new PhoneNumber("123"));

        Set<PhoneNumber> actual = oneEightHundredNumberGenerator.splitNumber(input);
        assertEquals(expected, actual);
    }

    @Test
    public void numberAvailable() throws Exception {
        String input = "225563";
        Set<PhoneNumber> actual = oneEightHundredNumberGenerator.splitNumber(input);
        assertTrue(actual.contains(new PhoneNumber("2255", "63")));
    }

    @Test
    public void checkNumberValidity() throws Exception{
        String input="213-2134";
        String expected="2132134";
        String actual = oneEightHundredNumberGenerator.validateNumber(input);
        assertEquals(expected,actual);

        input="1800-2500";
        expected="2500";
        actual = oneEightHundredNumberGenerator.validateNumber(input);
        assertEquals(expected,actual);

        input="2500.87";
        expected="250087";
        actual = oneEightHundredNumberGenerator.validateNumber(input);
        assertEquals(expected,actual);


        input="abc23";
        expected=null;
        actual = oneEightHundredNumberGenerator.validateNumber(input);
        assertEquals(expected,actual);


    }
}
