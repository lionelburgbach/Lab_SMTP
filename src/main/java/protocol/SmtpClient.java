package protocol;

import model.mail.Message;
import model.mail.Person;
import model.prank.Prank;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

    public SmtpClient(String smtpServerIPAddress, int port, List<Prank> prank){

        this.smtpServerIPAddress = smtpServerIPAddress;
        this.smtpServerPort = port;
        this.prank = new ArrayList<Prank>(prank);
    }

    @Override
    public void sendEmail() {

        try {
            LOG.info("Sending message via SMTP");

            socket = new Socket(smtpServerIPAddress, smtpServerPort);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            reader.readLine();

            writer.write(Protocol.CMD_HELLO + RETURN);
            writer.flush();

            String line = reader.readLine();

            //Note: does it always end with 250- ?
            while (line.startsWith("250-")){
                line = reader.readLine();
            }

            for(Prank p : prank){

                writer.write(Protocol.CMD_MAIL_FROM + p.getVictimSender().getAddress() + RETURN);
                writer.flush();
                reader.readLine();

                ArrayList<String> victims = new ArrayList<>();
                ArrayList<String> cc = new ArrayList<>();

                for(Person person : p.getVictimsRecip()){
                    victims.add(person.getAddress());
                    writer.write(Protocol.CMD_RCPT_TO + person.getAddress() + RETURN);

                    writer.flush();
                    reader.readLine();
                }

                for(Person person : p.getWitnessReceip()){
                    cc.add(person.getAddress());
                    writer.write(Protocol.CMD_RCPT_TO + person.getAddress() + RETURN);
                    writer.flush();
                    reader.readLine();
                }

                writer.write(Protocol.CMD_DATA + RETURN);
                writer.flush();
                reader.readLine();

                Message message = new Message(p.getVictimSender().getAddress(), victims, cc , p.getMessage());

                writer.write(message.forgeMessage());
                writer.flush();

                writer.write(Protocol.CMD_END_DATA + RETURN);
                writer.flush();
                reader.readLine();

            }

            writer.write(Protocol.CMD_QUIT + RETURN);
            writer.flush();
            socket.close();
            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
