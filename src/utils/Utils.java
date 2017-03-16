/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public static String GetCurrentTimeStamp()
    {
        Date d = new Date();
        
        return Long.toString(d.getTime());
    }
    
    public static boolean PassEncodedAreEquals(String passSend, String passReal, String timestamp)
    {
        try
        {
            String to_encode = String.format("%s%s", timestamp, passReal);
            byte[] encoded_pass_bytes =  MessageDigest.getInstance("MD5").digest(to_encode.getBytes());
            String pass_encoded = Utils.GetHexStringFromByteArray(encoded_pass_bytes);
            
            return (pass_encoded.equals(passSend));
            
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static String GetHexStringFromByteArray(byte[] byte_array)
    {
        StringBuilder hexString = new StringBuilder();
            
        for (int i = 0; i < byte_array.length; i++) 
        {
            if ((0xff & byte_array[i]) < 0x10) 
            {
                hexString.append("0" + Integer.toHexString((0xFF & byte_array[i])));
            } 
            else 
            {
                hexString.append(Integer.toHexString(0xFF & byte_array[i]));
            }
        }

        return hexString.toString();
}
}
