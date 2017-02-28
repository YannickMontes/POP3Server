package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yoann LATHUILIERE
 */
public class POP3Server {

    // Informations du serveur 
    private static final int DEFAULT_PORT = 1024; // Port utilisé
    private ServerSocket serverSocket = null; // Serveur socket

    /**
     * Fonction qui initilise le serveur en créant un serveur socket sur le port définie et attend les connexions
     */
    public void initializeServer() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT); // Création du serveur socket sur le port définie
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

}
