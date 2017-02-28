package server;

import java.io.IOException;

/**
 *
 * @author Yoann LATHUILIERE
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        POP3Server pop3server = new POP3Server();
        pop3server.initializeServer();
    }
    
}
