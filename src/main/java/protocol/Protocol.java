package protocol;

public class Protocol {

    public final static String CMD_HELLO = "EHLO Kim";
    public final static String CMD_MAIL_FROM = "MAIL FROM: ";
    public final static String CMD_RCPT_TO = "RCPT TO: ";
    public final static String CMD_DATA = "DATA";
    public final static String CMD_END_DATA = "\r\n.\r\n";
    public final static String CMD_QUIT = "QUIT\r\n";

    public  final static String CMD_AUTH_LOGIN = "AUTH LOGIN";

    //some error codes
    public final static int AUTHENTIFICATION_REQUIRED_ERROR = 530;
}
