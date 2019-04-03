package configuration;

import model.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationManager implements IConfigurationManager {


    private String smtpServerIpAddress;
    private int smtpServerPort;
    private int numberOfGroups;
    private List<String> messages;
    private List<Person> victims;
    private List<Person> witnessesToCC;


    public ConfigurationManager() throws IOException {

        Properties properties = new Properties();
        FileInputStream file = new FileInputStream("./src/main/resources/config.properties");
        properties.load(file);
        smtpServerIpAddress = properties.getProperty("smtpServerAddress");
        smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
        witnessesToCC = new ArrayList<Person>();
        String mail = properties.getProperty("witnessesToCC");
        String[] mailTab = mail.split(",");
        for (String s: mailTab) {
            witnessesToCC.add(new Person(s));
        }

        victims = victims("./src/main/resources/victims.utf8");
        messages = messages("./src/main/resources/messages.utf8");
    }

    @Override
    public String getSmtpServerIpAddress() {
        return smtpServerIpAddress;
    }

    @Override
    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    @Override
    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    @Override
    public List<String> messages(String filename) throws IOException {

        List<String> messages = new ArrayList<String>();
        FileInputStream file = new FileInputStream(filename);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
        String line;
        StringBuilder stringb = new StringBuilder();
        while((line = buffer.readLine())  != null) {
            if(line.equals("==")){
                messages.add(stringb.toString());
                stringb = new StringBuilder();
            }
            else {
                stringb.append(line).append("\n");
            }
        }

        return messages;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public List<Person> victims(String filename) throws IOException {

        List<Person> victims = new ArrayList<Person>();
        FileInputStream file = new FileInputStream(filename);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
        String addressMail;
        String firstName;
        String lastName;
        while((addressMail = buffer.readLine()) != null){
            Pattern pattern = Pattern.compile("(.*)\\.(.*)@");
            Matcher matcher = pattern.matcher(addressMail);
            firstName = "";
            lastName = "";
            boolean found = matcher.find();
            if(found){
                firstName = matcher.group(1);
                firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
                lastName = matcher.group(2);
                lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1);
            }
            victims.add(new Person(firstName, lastName, addressMail));
        }

        return victims;
    }

    @Override
    public List<Person> getVictims() {

        return victims;
    }

    @Override
    public List<Person> getWitnessesToCC() {
        return new ArrayList<>(witnessesToCC);
    }
}
