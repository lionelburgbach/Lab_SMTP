package model.prank;

import model.mail.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Use to build a Prank
 */
public class Prank {

    private Person victimSender;
    private List<Person> victimsRecip;
    private List<Person> witnessReceip;
    private String message;

    public Prank(Person victimSender, List<Person> victimsRecip, List<Person> witnessReceip, String message ){
        this.victimSender = victimSender;
        this.victimsRecip = new ArrayList<Person>(victimsRecip);
        this.witnessReceip = new ArrayList<Person>(witnessReceip);
        this.message = message;
    }

    public Person getVictimSender(){
        return victimSender;
    }

    public List<Person> getVictimsRecip() {
        return victimsRecip;
    }

    public List<Person> getWitnessReceip() {
        return witnessReceip;
    }

    public String getMessage(){
        return message;
    }

}