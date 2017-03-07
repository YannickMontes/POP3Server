/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;

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
        return "-ERR Commande non reconnue\nVoici la liste des commandes disponibles:\n"
        + "APOP <user> <pass> - Permet de se connecter\n"
        + "STAT - Informations sur la boite\n"
        + "...\n";
    }
}
