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
import states.Closed;
import states.StateAnswer;
import states.Update;
import utils.Utils;

/**
 *
 * @author Yoann LATHUILIERE
 */
public class ThreadCommunication extends Thread{
    private Socket replySocket = null;
    private states.State currentState;
    private Manager manager;
    public static ThreadLocal<Long> currentTimestamp = new ThreadLocal<Long>()
    {
        @Override
        protected Long initialValue()
        {
            return Integer.toUnsignedLong(0);
        }
    };
    
    public static ThreadLocal<String> currentUser = new ThreadLocal<String>(){
        @Override
        protected String initialValue()
        {
            return "";
        }
    };

    public ThreadCommunication(Socket s) 
    {
        replySocket = s;  
        currentState = new Autorisation();
    }

    @Override
    public void run() 
    {
        this.SetKeepAlive();
        this.SendServerIsReadyMessage();
        this.manager = new Manager();
               
        while (KeepCommunicationAlive()) 
        {
            String clientRequest = this.WaitClientRequest();
            
            if(clientRequest == null)
                break;
            
            StateAnswer server_response = manager.HandleCommand(clientRequest, currentState);
            
            this.ChangeState(server_response.getNextState());

            this.SendMessage(server_response.getAnswer());
            
            System.out.println("[DEBUG] Current state: "+currentState.getStateName());
        }
        
        this.WaitASecond();
        
        this.CloseCommunication();
    }
    
    public String WaitClientRequest()
    {
        String request = "";
        try
        {
            InputStream is = replySocket.getInputStream(); // Récupère la requete du client
            InputStreamReader r = new InputStreamReader(is);  // Création d'un buffer à partir du la requête
            BufferedReader br = new BufferedReader(r); // Création d'un buffer à partir du la requête
            request = br.readLine(); // Lit la première ligne de la requête
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ThreadCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return request;
    }
    
    private void ChangeState(states.State nextState)
    {
        if(nextState != null)
        {
            this.currentState = nextState;
        }
    }
    
    public boolean KeepCommunicationAlive()
    {
        return !(currentState instanceof Update || currentState instanceof Closed);
    }
    
    public void SendServerIsReadyMessage()
    {
        ThreadCommunication.currentTimestamp.set(Utils.GetCurrentTimeStamp());
        this.SendMessage(String.format("+OK POP3 Server is ready <%s>\r\n", Long.toString(ThreadCommunication.currentTimestamp.get())));
    }
    
    public void SendMessage(String messageString)
    {
        try
        {
            byte[] message = new byte[messageString.getBytes().length];
            System.arraycopy(messageString.getBytes(), 0, message, 0, messageString.getBytes().length);
            replySocket.getOutputStream().write(message);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ThreadCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void SetKeepAlive()
    {
        try 
        {
            replySocket.setKeepAlive(true);
        } 
        catch (SocketException ex) 
        {
            Logger.getLogger(ThreadCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void WaitASecond()
    {
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(ThreadCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void CloseCommunication()
    {
        try
        {
            this.replySocket.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ThreadCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
