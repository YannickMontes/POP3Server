/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import model.Mail;

/**
 *
 * @author yannick
 */
public abstract class Utils
{
    public static boolean UserInList(ArrayList<String> list, String user)
    {
        return list.contains(user);  
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
}
