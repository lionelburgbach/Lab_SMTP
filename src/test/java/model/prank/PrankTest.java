package model.prank;

import model.mail.Person;
import model.prank.Prank;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class PrankTest {

    @Test
    public void CreatePrankAndReatrieveContentTest(){
        Person kim = new Person("Kim", "Jong-un", "Kim.Jung-un@korea-dpr.com");
        Person trump = new Person("Donald", "Trump", "donald.trump@whitehouse.gov");
        Person trumpVariant = new Person("Donald", "Trump", "trump@whitehouse.gov");
        Person randomGuy = new Person("Random", "Guy", "guy@random.random");
        List<Person> victims = new LinkedList<>();
        victims.add(trump);
        victims.add(trumpVariant);
        List<Person> witnesses = new LinkedList<>();
        witnesses.add(randomGuy);
        String message = "...";

        Prank prank = new Prank(kim, victims, witnesses, message);

        assert(prank.getMessage().equals(message));
        assert(prank.getVictimSender().equals(kim));
        assert(prank.getVictimsRecip().equals(victims));
        assert(prank.getWitnessReceip().equals(witnesses));
    }

}
