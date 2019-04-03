import configuration.ConfigurationManager;
import model.prank.PrankGenerator;
import protocol.SmtpClient;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        PrankGenerator prankGenerator = new PrankGenerator(configurationManager);
        SmtpClient smtpClient = new SmtpClient(configurationManager.getSmtpServerIpAddress(), configurationManager.getSmtpServerPort(), prankGenerator.generatePrank());
        smtpClient.sendEmail();
    }
}
