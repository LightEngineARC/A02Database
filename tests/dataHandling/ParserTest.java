package dataHandling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {


    @Test
    void escapeQuotes() {
        try {
            //set up method access
            Method escQuotes = Parser.class.getDeclaredMethod("escapeQuotes", String.class);
            escQuotes.setAccessible(true);

            //test
            String raw = "Alec's \"Test\"";
            assertEquals("Alecs Test", escQuotes.invoke(new SongsParser(), raw));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            fail("class structure error or typo");
        }
    }
}