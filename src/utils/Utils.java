/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import model.Mail;
import model.User;

/**
 *
 * @author yannick
 */
public abstract class Utils
{
    public static boolean UserInList(ArrayList<User> list, String user)
    {
        for(User u : list) 
        {
            if(u.getName().equals(user))
            {
                return true;
            }
        }
        return false;
    }

    public static String GenerateHelpMessage()
    {
        return "-ERR Commande non reconnue\r\nVoici la liste des commandes disponibles:\r\n"
        + "APOP <user> <pass> - Permet de se connecter\r\n"
        + "STAT - Informations sur la boite\r\n"
        + "...\r\n";
    }
    
    public static String CreateStringCommandNotHandleInThisState(String eventName, String stateName)
    {
        return "-ERR "+eventName+" is not handled in "+stateName+" state\r\n";
    }
    
    public static int GetTotalNbBytesMails(ArrayList<Mail> mails)
    {
        int nbBytes = 0;
        
        for(Mail mail : mails)
        {
            nbBytes += Utils.GetNbBytesMail(mail);
        }
        
        return nbBytes;
    }
    
    public static int GetNbBytesMail(Mail mail)
    {
        return mail.getBody().getBytes().length;
    }

    public static boolean PassAreEquals(String pass, String password)
    {
        return pass.equals(password);
    }
    
    public static boolean PassAreEqualsForUserInList(ArrayList<User> users, String username, String pass)
    {
        for(User u : users)
        {
            if(u.getName().equals(username) && u.getPass().equals(pass))
            {
                return true;
            }
        }   
        return false;
    }
}
