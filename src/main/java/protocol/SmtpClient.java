package protocol;

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

            writer.write(Protocol.CMD_HELLO + "\r\n");
            writer.flush();

            String line = reader.readLine();
            System.out.println(line);

            //note: maybe this is not general enough
            while (line.startsWith("250-")){
                line = reader.readLine();
                System.out.println(line);
            }

            System.out.println("(our turn now)");


        }catch(IOException e){
            e.printStackTrace();
            return;
        }


        return;
    }
}
