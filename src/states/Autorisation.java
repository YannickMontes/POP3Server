/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import events.DELEEvent;
import events.LISTEvent;
import events.NOOPEvent;
import events.PASSEvent;
import events.QUITEvent;
import events.RETREvent;
import events.RSETEvent;
import events.STATEvent;
import events.TOPEvent;
import events.USEREvent;
import java.util.ArrayList;
import json_parser.ParserJSON;
import model.User;
import server.ThreadCommunication;
import utils.Utils;

/**
 *
 * @author yannick
 */
public class Autorisation extends State
{
    
    public Autorisation()
    {
        super(StateEnum.AUTORISATION);
    }

    @Override
    public StateAnswer LauchAPOP(APOPEvent apop)
    {
        String message;
        State nextState = null;
        System.out.println("Vérifications des informations");
        System.out.println("User: "+apop.getUser() + " Pass: "+ apop.getPass());
        
        ArrayList<User> users = ParserJSON.getUsers();
        
        if(Utils.UserInList(users, apop.getUser()))
        {            
            String password = ParserJSON.getPassForUser(apop.getUser());
            if(Utils.PassEncodedAreEquals(apop.getPass(), password, ThreadCommunication.currentTimestamp.get()))
            {
                ThreadCommunication.currentUser.set(apop.getUser());
            
                message = "+OK Welcome "+apop.getUser()+"\r\n";
                nextState = new Transaction();
            }
            else
            {
                message = "-ERR Incorrect password\r\n";
                nextState = null;
            }    
        }
        else
        {
            System.out.println("User not found");
            message = "-ERR User not found\r\n";
            nextState = null;
        }
        return new StateAnswer(nextState, message);
    }
    
    @Override
    public StateAnswer LaunchUSER(USEREvent user)
    {
        String message;
        
        ArrayList<User> users = ParserJSON.getUsers();
        
        if(Utils.UserInList(users, user.getUserName()))
        {
            ThreadCommunication.currentUser.set(user.getUserName());
            
            message = "+OK User found. Please send your pass\r\n";
        }
        else
        {
            message = "-ERR User not found\r\n";
        }
        
        return new StateAnswer(null, message);
    }

    @Override
    public StateAnswer LaunchPASS(PASSEvent pass)
    {
        String message;
        State nextState = null;
        String password = ParserJSON.getPassForUser(ThreadCommunication.currentUser.get());
        
        if(ThreadCommunication.currentUser.get().isEmpty())
        {
            message = "-ERR Send your user name before\r\n";
            nextState = null;
        }
        else if(Utils.PassAreEquals(pass.getPass(), password))
        {
            message = "+OK Welcome "+ThreadCommunication.currentUser.get()+"\r\n";
            nextState = new Transaction();
        }
        else
        {
            message = "-ERR Password incorect\r\n";
            nextState = null;
        }
        
        return new StateAnswer(nextState, message);
    }

    @Override
    public StateAnswer LauchDELE(DELEEvent dele) 
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(dele.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchSTAT(STATEvent stat)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(stat.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchRETR(RETREvent retr)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(retr.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchLIST(LISTEvent list)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(list.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchQUIT(QUITEvent quit)
    {
        String message;
        State nextState = new Closed();
        
        message = "+OK POP3 Server is signing off\r\n";
        
        
        return new StateAnswer(nextState, message);
    }

    @Override
    public StateAnswer LaunchNOOP(NOOPEvent noop)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(noop.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LaunchRSET(RSETEvent rset)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(rset.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LaunchTOP(TOPEvent top) {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(top.getEventName(), this.getStateName()));
    }
}
