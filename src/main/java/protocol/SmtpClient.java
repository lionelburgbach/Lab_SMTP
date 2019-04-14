package protocol;

import configuration.ConfigurationManager;
import model.mail.Message;
import model.mail.Person;
import model.prank.Prank;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SmtpClient implements ISmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private String smtpServerIPAddress;
    private int smtpServerPort;

    private List<Prank> prank;

    public static final String RETURN = "\r\n";

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private ConfigurationManager configurationManager;

    public SmtpClient(ConfigurationManager cm, List<Prank> prank){

        this.smtpServerIPAddress = cm.getSmtpServerIpAddress();
        this.smtpServerPort = cm.getSmtpServerPort();
        this.configurationManager = cm;
        this.prank = new ArrayList<Prank>(prank);
    }

    @Override
    public void sendEmail() {

        String line;

        try {
            LOG.info("Sending message via SMTP");

            //We try to connect with the smtp server
            try{
                socket = new Socket(smtpServerIPAddress, smtpServerPort);
            } catch(ConnectException ce){
                LOG.log(Level.SEVERE, "Connection with the server failed");
                return;
            }

            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            line = reader.readLine();
            System.out.println(line); //

            writer.write(Protocol.CMD_HELLO + RETURN);
            System.out.println(Protocol.CMD_HELLO + RETURN); //
            writer.flush();

            line = reader.readLine();
            System.out.println(line); //

            while (line.startsWith("250-")){
                line = reader.readLine();
                System.out.println(line); //
            }

            //if authentification is setup, we use AUTH LOGIN
            if(configurationManager.usingESMTP()){
                if(configurationManager.getBase64Login() == null ||
                configurationManager.getBase64Password() == null){
                    LOG.log(Level.SEVERE, "if using esmtp, login and password should also be setup in config file" +
                            "\n aborting");
                    socket.close();
                    reader.close();
                    writer.close();
                    return;
                }
                writer.write(Protocol.CMD_AUTH_LOGIN + RETURN);
                writer.flush();
                System.out.println(Protocol.CMD_AUTH_LOGIN);
                line = reader.readLine();
                System.out.println(line); //
                if(errorCode(line) != 334){
                    LOG.log(Level.SEVERE, "It seems the server doesn't support authentification" +
                            " (at east the one we are using which is" + Protocol.CMD_AUTH_LOGIN + "in our case)" +
                            "Here is the server error message: " + line);
                }
                writer.write(configurationManager.getBase64Login() + RETURN);
                System.out.println(configurationManager.getBase64Login()); //
                writer.flush();
                line = reader.readLine();
                System.out.println(line);
                writer.write(configurationManager.getBase64Password() + RETURN);
                System.out.println(configurationManager.getBase64Password());
                writer.flush();
                line = reader.readLine();
                System.out.println(line);
                if(errorCode(line) != 235){
                    LOG.log(Level.SEVERE, "It seems the authentification failed probably because of bad credentials" +
                            "\nHere is the error message returned by the server: " + line);
                }
            }

            for(Prank p : prank){

                writer.write(Protocol.CMD_MAIL_FROM + '<' + p.getVictimSender().getAddress() + '>' + RETURN);
                System.out.println(Protocol.CMD_MAIL_FROM + '<' + p.getVictimSender().getAddress() + '>'); //
                writer.flush();
                String response = reader.readLine();

                //On check si le server smtp oblige à utiliser l'authentificatinon
                if(errorCode(response) == Protocol.AUTHENTIFICATION_REQUIRED_ERROR){
                    LOG.log(Level.SEVERE, "The smtp server requires authentification:\n" +
                            "Here is the error returned by the server: " + response +
                            "\nMaybe you can solve this problem by modifying the config file: " +
                            configurationManager.getConfigFilePath());
                    socket.close();
                    writer.close();
                    reader.close();
                    return;
                }

                ArrayList<String> victims = new ArrayList<>();
                ArrayList<String> cc = new ArrayList<>();

                for(Person person : p.getVictimsRecip()){
                    victims.add(person.getAddress());
                    writer.write(Protocol.CMD_RCPT_TO + '<' + person.getAddress() + '>' + RETURN);
                    System.out.println(Protocol.CMD_RCPT_TO + '<' + person.getAddress() + '>' );
                    writer.flush();
                    line = reader.readLine();
                    System.out.println(line); //
                }

                for(Person person : p.getWitnessReceip()){
                    cc.add(person.getAddress());
                    writer.write(Protocol.CMD_RCPT_TO + '<' + person.getAddress() + '>'  + RETURN);
                    System.out.println(Protocol.CMD_RCPT_TO + '<' + person.getAddress() + '>' );
                    writer.flush();
                    line = reader.readLine();
                    System.out.println(line); //
                }

                writer.write(Protocol.CMD_DATA + RETURN);
                System.out.println(Protocol.CMD_DATA); //
                writer.flush();
                line = reader.readLine();
                System.out.println(line); //
                if(errorCode(line) != 354){
                    socket.close();
                    reader.close();
                    writer.close();
                    LOG.log(Level.SEVERE, "Server Error: " + line);
                    return;
                }

                Message message = new Message(p.getVictimSender().getAddress(), victims, cc , p.getMessage());

                writer.write(message.forgeMessage());
                System.out.println(message.forgeMessage()); //
                writer.flush();

                //writer.write(Protocol.CMD_END_DATA + RETURN);
                writer.write(Protocol.CMD_END_DATA);
                System.out.println(Protocol.CMD_END_DATA); //
                writer.flush();
                line = reader.readLine();
                System.out.println(line); //


            }

            writer.write(Protocol.CMD_QUIT + RETURN);
            writer.flush();
            socket.close();
            writer.close();
            reader.close();

            LOG.info("Task finised: connection closed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * return the error code of the smtp communication line
     * @param message : line of the smtp communication line
     * @return 0 is no error is detected, the number of the error otherwise
     */
    private int errorCode(String message){
        if(message.length() > 3){
            int errorCode;
            try{
                errorCode = Integer.parseInt(message.substring(0,3));
            } catch (NumberFormatException e){
                return 0;
            }
            return errorCode;
        }
        return 0;
    }

}
