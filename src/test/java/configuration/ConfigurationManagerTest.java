package configuration;

import model.mail.Person;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationManagerTest {

    @Test
     public void itShouldHave4Message() throws IOException {

        ConfigurationManager config = new ConfigurationManager();
        List<String> list = new ArrayList<String>(config.getMessages());
        assertEquals(list.size(),4);
    }

    @Test
    public void itShouldHave83Victims() throws IOException {

        ConfigurationManager config = new ConfigurationManager();
        List<Person> list = new ArrayList<Person>(config.getVictims());
        assertEquals(list.size(), 83);
    }

    @Test
    public void itShouldHave2WinToCC() throws IOException {

        ConfigurationManager config = new ConfigurationManager();
        List<Person> list = new ArrayList<Person>(config.getWitnessesToCC());
        assertEquals(list.size(), 2);
    }
}
