package protocol;

import model.prank.Prank;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private String smtpServerIPAddress;
    private int smtpServerPort;


    private List<Prank> prank;

    public SmtpClient(String smtpServerIPAddress, int port, List<Prank> prank){

        this.smtpServerIPAddress = smtpServerIPAddress;
        this.smtpServerPort = port;
        this.prank = new ArrayList<Prank>(prank);
    }

    @Override
    public void sendEmail() {
        //TODO




        return;
    }
}
