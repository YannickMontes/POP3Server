/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import events.APOPEvent;
import events.Event;
import states.State;
import events.EventEnum;

/**
 *
 * @author yannick
 */
public class Manager
{
    
    
    public void HandleCommand(String receivedMessage, State currentState)
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
            return;
        }
                
        switch(command)
        {
            case APOP:
                System.out.println("APOP");
                
                if(message_split.length != 3)
                {
                    //Envoyer ERR
                    System.out.println("Arguments non valides pour la commande APOP");
                    return;
                }
                String user = message_split[1];
                String pass = message_split[2];
                
                
                currentState.LauchAPOP(new APOPEvent(user, pass));
                
                break;
                
            case STAT:
                System.out.println("STAT");
                break;
                
            case LIST:
                System.out.println("LIST");
                break;
                
            case DELE:
                System.out.println("DELE");
                break;
                
            case QUIT:
                System.out.println("QUIT");
                break;
            
            default:
                System.out.println("Switch case not handled yet");
                break;
        }
    }
}
