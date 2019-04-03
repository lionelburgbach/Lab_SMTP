package model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<Person> members = new ArrayList<Person>();

    public void addMember(Person person){
        members.add(person);
    }

    public List<Person> getMemebers(){
        return new ArrayList<>(members);
    }
}
