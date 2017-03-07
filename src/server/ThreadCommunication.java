package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import states.Autorisation;
import states.StateAnswer;

/**
 *
 * @author Yoann LATHUILIERE
 */
public class ThreadCommunication extends Thread{
    // Création d'un socket pour la réponse
    Socket replySocket = null;
    states.State currentState;
    /**
     * Constructeur du thread
     */
    public ThreadCommunication(Socket s) {
        replySocket = s;  
        currentState = new Autorisation();
    }

    /**
     * Fonction run du thread, qui est chargé de récuperer la requête du serveur et de l'executer
     */
    @Override
    public void run() 
    {
        this.SendServerIsReadyMessage();
        String request;
        Manager manager = new Manager();
        try {
            replySocket.setKeepAlive(true);
        } catch (SocketException ex) {
            Logger.getLogger(ThreadCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                
                InputStream is = replySocket.getInputStream(); // Récupère la requete du client
                InputStreamReader r = new InputStreamReader(is);  // Création d'un buffer à partir du la requête
                BufferedReader br = new BufferedReader(r); // Création d'un buffer à partir du la requête
                request = br.readLine(); // Lit la première ligne de la requête
                System.out.println(request);
                
                StateAnswer server_response = manager.HandleCommand(request, currentState);
                if(server_response.getNextState() != null)
                    this.currentState = server_response.getNextState();
                
                System.out.println("[DEBUG] Current state: "+currentState.getStateName());
                
                /*switch(evt)
                {
                    case APOPEvent:
                        state.LauchAPOP();
                        break;
                    case "STAT":
                        state.LauchSTAT();
                        break;
                    case "RETR":
                        state.LauchRETR();
                        break;
                    case "QUIT":
                        state.LauchQUIT();
                        break;
                    case "LIST":
                        state.LauchLIST();
                        break;
                }*/
                
                this.SendMessage(server_response.getAnswer());
                
            } catch (IOException ex) {
                Logger.getLogger(ThreadCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void SendServerIsReadyMessage()
    {
        this.SendMessage("+OK POP3 Server is ready\n");
    }
    
    public void SendMessage(String messageString)
    {
        try
        {
            byte[] message = new byte[messageString.getBytes().length];
            System.arraycopy(messageString.getBytes(), 0, message, 0, messageString.getBytes().length);
            replySocket.getOutputStream().write(message);
        } catch (IOException ex)
        {
            Logger.getLogger(ThreadCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
