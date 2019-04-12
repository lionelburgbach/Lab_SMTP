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
     * Returns whether we use esmtp
     * @return true is we use esmtp, false is we use basic smtp
     */
    public boolean usingESMTP();

    /**
     *
     * @return the login used for authentification. null is there is none.
     */
    public String getBase64Login();

    /**
     *
     * @return the password used for authentification. null is there is none.
     */
    public String getBase64Password();

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
