import configuration.ConfigurationManager;
import model.prank.PrankGenerator;
import protocol.SmtpClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final String configFolderPath = "config/";
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        ConfigurationManager configurationManager;
        try{
            if(args.length > 0) configurationManager = new ConfigurationManager(args[0]);
            else configurationManager = new ConfigurationManager(configFolderPath);
        } catch(IOException ioe){
            LOG.log(Level.SEVERE, "Config folder was not found." +
                    "\nPlease provide the config folder path as a program parameter." +
                    "\nBy default, you should have a folder named: " + configFolderPath +
                    " in the same folder as the executable");
            return;
        }
        PrankGenerator prankGenerator = new PrankGenerator(configurationManager);
        SmtpClient smtpClient = new SmtpClient(configurationManager, prankGenerator.generatePrank());
        smtpClient.sendEmail();
    }
}
