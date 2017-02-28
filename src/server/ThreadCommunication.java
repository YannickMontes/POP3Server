/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yoann LATHUILIERE
 */
public class ThreadCommunication extends Thread{
    // Création d'un socket pour la réponse
    Socket replySocket = null;

    /**
     * Constructeur du thread
     */
    public ThreadCommunication(Socket s) {
        replySocket = s;    
    }

    /**
     * Fonction run du thread, qui est chargé de récuperer la requête du serveur et de l'executer
     */
    @Override
    public void run() 
    {
        String request;
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
            } catch (IOException ex) {
                Logger.getLogger(ThreadCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
