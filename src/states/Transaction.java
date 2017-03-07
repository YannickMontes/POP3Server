/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import events.DELEEvent;
import events.Event;
import java.util.ArrayList;
import json_parser.ParserJSON;
import model.Mail;
import utils.Utils;

/**
 *
 * @author yannick
 */
public class Transaction extends State
{
    public Transaction()
    {
        super(StateEnum.TRANSACTION);
    }

    @Override
    public String LauchAPOP(APOPEvent apop)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String LauchDELE(DELEEvent dele) {
        String message = "JUJUJUL";
        System.out.println("Tentative de suppression du message id: " + dele.getMsgID());
        
        ArrayList<Mail> mails = ParserJSON.getMails("yannick");
        
        /*if(Utils.UserInList(users, apop.getUser()))
        {
            System.out.println("User found.");
            message = "+OK Welcome "+apop.getUser();
        }
        else
        {
            System.out.println("User not found");
            message = "-ERR User not found";
        }*/
        return message;
    }

}
