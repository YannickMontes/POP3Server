package server;

import java.io.IOException;
import json_parser.ParserJSON;

/**
 *
 * @author Yoann LATHUILIERE
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ParserJSON.initJSONFiles();
        POP3Server pop3server = new POP3Server();
        pop3server.initializeServer();
        
        ServerGUI frame = new ServerGUI();
        frame.setVisible(true);
    }
    
}
