package model.prank;

import configuration.ConfigurationManager;
import model.mail.Group;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrankGenerator {

    private ConfigurationManager configurationManager;

    public PrankGenerator(ConfigurationManager configurationManager){
        this.configurationManager = configurationManager;
    }

    public List<Prank> generatePrank() {

        ArrayList<Prank> prank = new ArrayList<Prank>();

        List<String> messages = configurationManager.getMessages();

        List<Person> victims = configurationManager.getVictims();

        List<Person> witToCC = configurationManager.getWitnessesToCC();

        int nbrGroups = configurationManager.getNumberOfGroups();
        int sizeGroup = victims.size()/nbrGroups;
        int sizeLastGroup = sizeGroup + (victims.size() % nbrGroups);

        //Check if the size of a group is big enough, if it's not, decrese nbrGroups
        if(sizeGroup < 3){

            do{

                nbrGroups -= 1;

                if(nbrGroups == 0){
                    throw new IllegalArgumentException("Number of victims is too small.");
                }
                else{
                    sizeGroup = victims.size()/nbrGroups;
                }

            }while(sizeGroup < 3 );

            sizeGroup = victims.size()/nbrGroups;
            sizeLastGroup = sizeGroup + (victims.size() % nbrGroups);
        }

        List<Group> groups = new ArrayList<Group>();

        for (int i = 0; i < nbrGroups; i++){
            groups.add(new Group());
        }

        int count = 0;

        for (int i = 0; i < nbrGroups; i++){

            if(i < nbrGroups-1){
                for (int j = 0; j < sizeGroup; j++){
                    groups.get(i).addMember(victims.get(count));
                    count++;
                }
            }
            else {
                for (int j = 0; j < sizeLastGroup; j++){
                    groups.get(i).addMember(victims.get(count));
                    count++;
                }
            }
        }

        for (Group group : groups) {

            List<Person> groupVictims = group.getMemebers();
            prank.add(new Prank(groupVictims.remove(random(groupVictims.size())), groupVictims, witToCC, messages.get(random(messages.size()))));
        }

        return prank;
    }

    public int random(int max){

        Random random = new Random();
        return random.nextInt(max);
    }

}
