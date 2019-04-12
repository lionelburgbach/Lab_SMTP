import configuration.ConfigurationManager;
import model.prank.PrankGenerator;
import protocol.SmtpClient;

import java.io.IOException;

public class Main {

    /*
    public static void main(String[] args){

    }
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
        List<Prank> pranks = new LinkedList<>();
        pranks.add(prank);
        //run ncat listening on port 2323 to test this
        SmtpClient smtp = new SmtpClient("127.0.0.1", 2323, pranks);
        smtp.sendEmail();*/


    public static void main(String[] args) throws IOException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        System.out.println("qnhheé");
        PrankGenerator prankGenerator = new PrankGenerator(configurationManager);
        SmtpClient smtpClient = new SmtpClient(configurationManager, prankGenerator.generatePrank());
        smtpClient.sendEmail();
    }
}
