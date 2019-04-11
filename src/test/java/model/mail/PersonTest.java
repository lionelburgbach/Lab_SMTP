package model.mail;

import configuration.ConfigurationManager;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void firstPersonShouldBeOk() throws IOException {

        ConfigurationManager configurationManager = new ConfigurationManager( "resourcesTest");
        List<Person> list = configurationManager.getVictims();

        assertEquals(list.get(0).getFirstName(), "Alexandre");
        assertEquals(list.get(0).getLastName(), "Gabrielli");
        assertEquals(list.get(0).getAddress(), "alexandre.gabrielli@heig-vd.ch");
    }
}
