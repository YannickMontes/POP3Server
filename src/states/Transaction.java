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
        
        ArrayList<Mail> mails = ParserJSON.getMails(ThreadCommunication.currentUser.get());

        try {
            if (mails.get(dele.getMsgID() - 1).getTag() == MailTagEnum.DELETED) 
            {
                message = "-ERR message " + dele.getMsgID() + " already deleted\r\n";
            }
            else 
            {
                ParserJSON.markMailAsForUser(ThreadCommunication.currentUser.get(), MailTagEnum.DELETED, dele.getMsgID());
                //mails.get(dele.getMsgID() - 1).deleteMessage();
                message = "+OK message " + dele.getMsgID() + " deleted\r\n";
            }
            
        } catch (Exception e) {
            message = "-ERR numéro de message invalide\r\n";
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
            message += mails.get(retr.getMessageID()-1).toString();
            message += ".\r\n";
        }
        catch(Exception e)
        {
            message = "-ERR numero de message invalide\r\n";
        }
        
        return new StateAnswer(null, message);
    }

    @Override
    public StateAnswer LauchLIST(LISTEvent list)
    {
        String message;
        
        ArrayList<Mail> mails = ParserJSON.getMails(ThreadCommunication.currentUser.get());
        
        if(list.getMessageID() != -1)
        {
            try
            {
                message = "+OK "+list.getMessageID()+" "+mails.get(list.getMessageID()-1).getBody().getBytes().length+"\r\n";
            }
            catch(Exception e)
            {
                message = "-ERR no such message, only "+mails.size()+" in mailbox\r\n";
            }
        }
        else
        {
            message = "+OK "+mails.size()+" messages\r\n";
            for(int i=0; i<mails.size(); i++)
            {
                message += (i+1)+" "+mails.get(i).getBody().getBytes().length+"\r\n";
            }
            message += ".\r\n";
        }
        
        return new StateAnswer(null, message);
    }

    @Override
    public StateAnswer LaunchUSER(USEREvent user)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(user.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LaunchPASS(PASSEvent pass)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(pass.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchQUIT(QUITEvent quit)
    {
        String message;
        State nextState = new Update();
        
        if(ParserJSON.deleteMailsMarkAsDeletedForUser(ThreadCommunication.currentUser.get()))
        {
            message = "+OK Messages mark as deleted were deleted. POP3 Server signing off\r\n";
        }
        else
        {
            message = "-ERR Issue while deleting the mails. POP3 server signing off\r\n";
        }
        
        return new StateAnswer(nextState, message);
    }

    @Override
    public StateAnswer LaunchNOOP(NOOPEvent noop)
    {
        return new StateAnswer(null, "+OK\r\n");
    }
    
    @Override
    public StateAnswer LaunchRSET(RSETEvent rset)
    {
        String message;
        
        if(ParserJSON.unmarkDeleteMails(ThreadCommunication.currentUser.get()))
        {
            message = "+OK Mails mark as DELETED are now unmarked\r\n";
        }
        else
        {
            message = "-ERR Error while unmarking the mails\r\n";
        }
        
        return new StateAnswer(null, message);
    }

    @Override
    public StateAnswer LaunchTOP(TOPEvent top) {
    String message;
        
        ArrayList<Mail> mails = ParserJSON.getMails(ThreadCommunication.currentUser.get());
        
        try
        {
            message = "+OK \r\n";
            message += mails.get(top.getMessageID()-1).toStringTop(top.getLineNumber());
            message += ".\r\n";
        }
        catch(Exception e)
        {
            message = "-ERR numero de message invalide\r\n";
        }
        
        return new StateAnswer(null, message);    
    }
}
