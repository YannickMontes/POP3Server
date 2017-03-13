/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import events.APOPEvent;
import events.DELEEvent;
import states.State;
import events.EventEnum;
import events.LISTEvent;
import events.NOOPEvent;
import events.PASSEvent;
import events.QUITEvent;
import events.RETREvent;
import events.RSETEvent;
import events.STATEvent;
import events.TOPEvent;
import events.USEREvent;
import states.StateAnswer;
import utils.Utils;

/**
 *
 * @author yannick
 */
public class Manager
{
    
    
    public StateAnswer HandleCommand(String receivedMessage, State currentState)
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
            return new StateAnswer(null, Utils.GenerateHelpMessage());
        }
        
        StateAnswer response = null;
        String returnedMessage = "";
                
        switch(command)
        {
            case APOP:
                System.out.println("[DEBUG]APOP recu par " + ThreadCommunication.currentUser.get());
                
                if(message_split.length != 3)
                {
                    //Envoyer ERR
                    System.out.println("Arguments non valides pour la commande APOP");
                    returnedMessage = "-ERR APOP command take two arguments\r\n";
                }
                else
                {
                    String user = message_split[1];
                    String pass = message_split[2];          
                    response = currentState.LauchAPOP(new APOPEvent(user, pass));
                }
                
                break;
                
            case USER:
                System.out.println("[DEBUG]USER recu par " + ThreadCommunication.currentUser.get());
                
                if(message_split.length != 2)
                {
                    returnedMessage = "-ERR USER command take on argument\r\n";
                }
                else
                {
                    String user = message_split[1];
                    response = currentState.LaunchUSER(new USEREvent(user));
                }
                
                break;
            
            case PASS:
                System.out.println("[DEBUG]PASS recu par " + ThreadCommunication.currentUser.get());
                
                if(message_split.length != 2)
                {
                    returnedMessage = "-ERR PASS command take on argument\r\n";
                }
                else
                {
                    String pass = message_split[1];
                    response = currentState.LaunchPASS(new PASSEvent(pass));
                }
                
                break;
                
            case STAT:
                System.out.println("[DEBUG]STAT recu par " + ThreadCommunication.currentUser.get());
                
                if(message_split.length != 1)
                {
                    returnedMessage = "-ERR STAT command take no arguments\r\n";
                }
                else
                {
                    response = currentState.LauchSTAT(new STATEvent());
                }
                
                break;
                
            case LIST:
                System.out.println("[DEBUG]LIST recu par " + ThreadCommunication.currentUser.get());
                
                if(message_split.length > 2)
                {
                    returnedMessage = "-ERR LIST command take only one optional argument\r\n";
                }
                else if(message_split.length == 2)
                {
                    response = currentState.LauchLIST(new LISTEvent(Integer.parseInt(message_split[1])));
                }
                else
                {
                    response = currentState.LauchLIST(new LISTEvent());
                }
                
                break;
                
            case RETR:
                System.out.println("[DEBUG]RETR recu");
                
                if(message_split.length != 2)
                {
                    returnedMessage = "-ERR RETR command take one argument\r\n";
                }
                else
                {
                    response = currentState.LauchRETR(new RETREvent(Integer.parseInt(message_split[1])));
                }
                break;
                
            case TOP:
                System.out.println("[DEBUG]TOP recu");
                
                if(message_split.length != 3)
                {
                    returnedMessage = "-ERR TOP command take three argument\r\n";
                }
                else if(Integer.parseInt(message_split[2]) < 0)
                {
                    returnedMessage = "-ERR TOP line number can't be negative\r\n";
                }
                else
                {
                    response = currentState.LaunchTOP(new TOPEvent(Integer.parseInt(message_split[1]), Integer.parseInt(message_split[2])));
                }
                break;
                
            case DELE:
                System.out.println("[DEBUG]DELE recu par " + ThreadCommunication.currentUser.get());
                
                if(message_split.length != 2)
                {
                    returnedMessage = "-ERR DELE command take one argument\r\n";
                }
                else
                {
                    response = currentState.LauchDELE(new DELEEvent(Integer.parseInt(message_split[1])));
                }
                break;
                
            case RSET:
                System.out.println("[DEBUG]RSET recu par " + ThreadCommunication.currentUser.get());
                
                if(message_split.length != 1)
                {
                    returnedMessage = "-ERR RSET command take no argument\r\n";
                }
                else
                {
                    response = currentState.LaunchRSET(new RSETEvent());
                }
                break;
                
            case QUIT:
                System.out.println("[DEBUG]QUIT recu par " + ThreadCommunication.currentUser.get());
                if(message_split.length != 1)
                {
                    returnedMessage = "-ERR QUIT Command take no arguments\r\n";
                }
                else
                {
                    response = currentState.LauchQUIT(new QUITEvent());
                }
                break;
                
            case NOOP:
                System.out.println("[DEBUG]NOOP recu par " + ThreadCommunication.currentUser.get());
                if(message_split.length != 1)
                {
                    returnedMessage = "-ERR NOOP Command take no arguments\r\n";
                }
                else
                {
                    response = currentState.LaunchNOOP(new NOOPEvent());
                }
                break;
            
            default:
                System.out.println("Switch case not handled yet");
                returnedMessage = Utils.GenerateHelpMessage();
                break;
        }
        
        if(response == null)
        {
            return new StateAnswer(null, returnedMessage);
        }
        
        return response;
    }
}
