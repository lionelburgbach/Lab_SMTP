package configuration;

import model.mail.Person;

import java.io.IOException;
import java.util.List;

/**
 * Read and load configuration from external files.
 */
public interface IConfigurationManager {


    /**
     * Return the Smtp serveur IPaddress
     *
     * @return the Smtp server IPaddress
     */
    public String getSmtpServerIpAddress();

    /**
     * Return the Smtp server port
     *
     * @return the Smtp server port
     */
    public int getSmtpServerPort();

    /**
     * Return the number of groups
     *
     * @return the number of groups
     */
    public int getNumberOfGroups();

    /**
     * Return a List of messages from the file
     *
     * @param filename file path
     * @return a List of messages
     * @throws IOException
     */
    public List<String> messages(String filename) throws IOException;

    /**
     * Return a List of messages
     *
     * @return a List of messages
     */
    public List<String> getMessages();

    /**
     * Return a List of victims from the file
     *
     * @param filename file path
     * @return a List of victims
     * @throws IOException
     */
    public List<Person> victims(String filename) throws IOException;

    /**
     * Return a List of victims
     *
     * @return a List of victims
     */
    public List<Person> getVictims();

    /**
     * Return a List of witnnessesToCC
     *
     * @return a List of witnnessesToCC
     */
    public List<Person> getWitnessesToCC();
}
