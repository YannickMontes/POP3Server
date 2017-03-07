/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import events.DELEEvent;
import events.Event;
import events.RETREvent;
import events.STATEvent;
import java.util.ArrayList;
import json_parser.ParserJSON;
import model.Mail;
import server.ThreadCommunication;
import utils.MailTagEnum;
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
    public StateAnswer LauchAPOP(APOPEvent apop)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(apop.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchDELE(DELEEvent dele) {
        String message;

        System.out.println("Tentative de suppression du message id: " + dele.getMsgID());
        System.out.println(ThreadCommunication.currentUser.get());
        
        ArrayList<Mail> mails = ParserJSON.getMails(ThreadCommunication.currentUser.get());

        try {
            if (mails.get(dele.getMsgID() - 1).getTag() == MailTagEnum.DELETED) {
                message = "-ERR message " + (dele.getMsgID() - 1) + " already deleted";
            }
            else {
                mails.get(dele.getMsgID() - 1).deleteMessage();
                message = "+OK message " + (dele.getMsgID() - 1) + " deleted";
            }
            
        } catch (Exception e) {
            message = "-ERR numéro de message invalide";
        }
        
        return new StateAnswer(null, message);
    }

    @Override
    public StateAnswer LauchSTAT(STATEvent stat)
    {
        String message;
        
        ArrayList<Mail> mails = ParserJSON.getMails(ThreadCommunication.currentUser.get());
        
        message = "+OK "+mails.size()+" "+Utils.GetTotalNbBytesMails(mails)+"\r\n";
        
        return new StateAnswer(null, message);
    }

    @Override
    public StateAnswer LauchRETR(RETREvent retr)
    {
        String message;
        
        ArrayList<Mail> mails = ParserJSON.getMails(ThreadCommunication.currentUser.get());
        
        try
        {
            message = "+OK "+Utils.GetNbBytesMail(mails.get(retr.getMessageID()-1))+" octets\r\n";
            message += mails.get(retr.getMessageID()-1).getBody()+"\r\n";
            message += ".\r\n";
        }
        catch(Exception e)
        {
            message = "-ERR numero de message invalide\r\n";
        }
        
        return new StateAnswer(null, message);
    }

}
