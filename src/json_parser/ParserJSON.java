/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.MailTagEnum;

/**
 *
 * @author yannick
 */
public abstract class ParserJSON 
{
    public static ArrayList<String> getUsers()
    {
        ArrayList toRet = new ArrayList<String>();
        try
        {
            JSONParser parser = new JSONParser();
            
            JSONObject parsedFile = (JSONObject)parser.parse(new FileReader("infos.json"));
            
            JSONArray users = (JSONArray)parsedFile.get("users");
            Iterator<JSONObject> iterator = users.iterator();
            while(iterator.hasNext())
            {
                JSONObject user = iterator.next();
                toRet.add(user.get("user"));
            }
            
            return toRet;
        } catch (IOException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ArrayList<Mail> getMails(String user) {
        ArrayList toRet = new ArrayList<Mail>();
        try
        {
            JSONParser parser = new JSONParser();
            
            JSONObject parsedFile = (JSONObject)parser.parse(new FileReader("mails.json"));
            
            JSONArray users = (JSONArray)parsedFile.get("mails");
            Iterator<JSONObject> iterator = users.iterator();
            while(iterator.hasNext())
            {
                JSONObject mailJSON = iterator.next();
                
                JSONObject expJSON = (JSONObject) mailJSON.get("from");
                JSONObject recJSON = (JSONObject) mailJSON.get("to");
                
                String exp  = (String) expJSON.get("adress");
                String expName = (String) expJSON.get("name");
                String recName = (String) recJSON.get("name");
                String rec = (String) recJSON.get("adress");
                String sub = (String) mailJSON.get("subject");
                String dat = (String) mailJSON.get("date");
                String msgID = (String) mailJSON.get("message-id");
                String tagString = (String) mailJSON.get("balise");
                MailTagEnum tag = MailTagEnum.valueOf(tagString);
                String body = (String) mailJSON.get("body");
                
                if (user.equals(recName)) {
                    Mail m = new Mail(expName, exp, recName, rec, sub, dat, msgID, tag, body);
                    toRet.add(m);
                }
            }
            
            return toRet;
        } catch (IOException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    
}
