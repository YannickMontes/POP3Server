/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import events.DELEEvent;
import events.STATEvent;
import java.util.ArrayList;
import json_parser.ParserJSON;
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
            message = "+OK Welcome "+apop.getUser();
            nextState = new Transaction();
        }
        else
        {
            System.out.println("User not found");
            message = "-ERR User not found";
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
}
