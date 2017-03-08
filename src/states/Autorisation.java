/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import events.DELEEvent;
import events.LISTEvent;
import events.RETREvent;
import events.STATEvent;
import java.util.ArrayList;
import json_parser.ParserJSON;
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
        
        ArrayList<String> users = ParserJSON.getUsers();
        
        if(Utils.UserInList(users, apop.getUser()))
        {
            System.out.println("User found.");
            
            ThreadCommunication.currentUser.set(apop.getUser());
            
            message = "+OK Welcome "+apop.getUser()+"\r\n";
            nextState = new Transaction();
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
}
