package model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * A group of people
 */
public class Group {

    private List<Person> members = new ArrayList<Person>();

    /**
     * Add Member to the group
     *
     * @param person
     */
    public void addMember(Person person){
        members.add(person);
    }

    /**
     * Return a list of Members
     *
     * @return a list of Members
     */
    public List<Person> getMemebers(){
        return new ArrayList<>(members);
    }
}
