package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author Yoann LATHUILIERE
 */
public class POP3Server {

    // Informations du serveur 
    private static final int DEFAULT_PORT = 1024; // Port utilisé
    private ServerSocketFactory socketFactory;
    private SSLServerSocket serverSocket = null; // Serveur socket

    /**
     * Fonction qui initilise le serveur en créant un serveur socket sur le port définie et attend les connexions
     */
    public void initializeServer() {
        try {
            socketFactory = SSLServerSocketFactory.getDefault();
            serverSocket = (SSLServerSocket) socketFactory.createServerSocket(DEFAULT_PORT);// Création du serveur socket sur le port définie
            this.EnableAnonCipherSuite();
            System.out.println("Listening for connection on port 1024 ....");
            // Boucle infinie qui attend les connexions
            while (true) {
                Socket replySocket;
                try
                {
                    replySocket = serverSocket.accept(); // Accepte la connexion
                    ThreadCommunication tc = new ThreadCommunication(replySocket); // Crée un nouveau ThreadCommunication (thread qui gère les communications avec le client)
                    tc.start(); // Démarrage du thread
                    System.out.println("Start a new communication.");
                } catch (IOException ex)
                {
                    Logger.getLogger(POP3Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(POP3Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Création du socket sur le port 1024 impossible car le port est occupé");
        }

    }
    
    private void EnableAnonCipherSuite()
    {
        String[] supported = serverSocket.getSupportedCipherSuites();
        List<String> list= new ArrayList<String>();

        for(int i = 0; i < supported.length; i++)
        {
            if(supported[i].indexOf("_anon_") > 0)
            {
                list.add(supported[i]);
            }
        }
        String[] anonCipherSuitesSupported = list.toArray(new String[0]);
        serverSocket.setEnabledCipherSuites(anonCipherSuitesSupported);
    }

}
