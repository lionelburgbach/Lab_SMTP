package model.mail;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private String from;
    private List<String> to;
    private List<String> cc;
    private String subject;

    public static final String RETURN = "\r\n";

    public Message (String from, List<String> to, List<String> cc, String subject) {
        this.from = from;
        this.to = new ArrayList<>(to);
        this.cc = new ArrayList<>(cc);
        this.subject = subject;
    }

    public String forgeMessage(){

        StringBuilder s = new StringBuilder();

        s.append("FROM: ").append(from);

        s.append(RETURN).append("To :");
        s.append(to.get(0));
        for (int i = 1; i < to.size(); i++){
            s.append(", " + to.get(i));
        }

        s.append(RETURN).append("CC :");
        s.append(cc.get(0));
        for (int i = 1; i < cc.size(); i++){
            s.append(", " + cc.get(i));
        }

        s.append(RETURN).append(subject);

        return s.toString();
    }

}