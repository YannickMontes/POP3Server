/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import events.APOPEvent;
import states.State;
import events.EventEnum;
import utils.Utils;

/**
 *
 * @author yannick
 */
public class Manager
{
    
    
    public String HandleCommand(String receivedMessage, State currentState)
    {
        //Split the received message by space, to know what is the command
        //and what are the args
        String[] message_split = receivedMessage.split(" ");
        EventEnum command;
        
        try
        {
            command = EventEnum.valueOf(message_split[0].toUpperCase());
        }
        catch(IllegalArgumentException e)
        {
            //Envoyer ERR
            return "-ERR Somthing wrong happened\n";
        }
        
        String returnedMessage="";
                
        switch(command)
        {
            case APOP:
                System.out.println("[DEBUG]APOP recu");
                
                if(message_split.length != 3)
                {
                    //Envoyer ERR
                    System.out.println("Arguments non valides pour la commande APOP");
                    returnedMessage = "-ERR APOP command take two arguments\n";
                }
                else
                {
                    String user = message_split[1];
                    String pass = message_split[2];          
                    returnedMessage = currentState.LauchAPOP(new APOPEvent(user, pass));
                }
                
                break;
                
            case STAT:
                System.out.println("[DEBUG]STAT recu");
                
                if(message_split.length != 1)
                {
                    returnedMessage = "-ERR STAT command take no arguments\n";
                }
                else
                {
                    //returnedMessage = currentState.LauchSTAT(new STATEvent());
                }
                
                break;
                
            case LIST:
                System.out.println("[DEBUG]LIST recu");
                
                if(message_split.length > 2)
                {
                    returnedMessage = "-ERR LIST command take only one optional argument\n";
                }
                else if(message_split.length == 2)
                {
                    //returnedMessage = currentState.LauchLIST(new LISTEvent(message_split[1]));
                }
                else
                {
                    //returnedMessage = currentState.LauchLIST(new LISTEvent());
                }
                
                break;
                
            case DELE:
                System.out.println("[DEBUG]DELE recu");
                
                if(message_split.length != 2)
                {
                    returnedMessage = "-ERR DELE command take one argument\n";
                }
                else
                {
                    //returnedMessage = currentState.LaunchDELE(new DELEEvent(message_split[1]));
                }
                break;
                
            case QUIT:
                System.out.println("[DEBUG]QUIT recu");
                if(message_split.length != 1)
                {
                    returnedMessage = "-ERR QUIT Command only take no arguments\n";
                }
                else
                {
                    //returnedMessage = currentState.LaunchQUIT(new QUITEvent());
                }
                break;
            
            default:
                System.out.println("Switch case not handled yet");
                returnedMessage = Utils.GenerateHelpMessage();
                break;
        }
        
        return returnedMessage;
    }
}
