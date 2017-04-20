/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;
import model.Mail;
import model.User;
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
    private static String infosFile;
    private static String mailsFile;
    
    public static void initJSONFiles()
    {        
        String home = System.getProperty("user.home");
        if(Files.exists(Paths.get(home + "/Bureau"), LinkOption.NOFOLLOW_LINKS))
        {
            ParserJSON.infosFile = System.getProperty("user.home")+"/Bureau/infos.json";
            ParserJSON.mailsFile = System.getProperty("user.home")+"/Bureau/mails.json";
        }
        else
        {
            ParserJSON.infosFile = System.getProperty("user.home")+"/Desktop/infos.json";
            ParserJSON.mailsFile = System.getProperty("user.home")+"/Desktop/mails.json";
        }
    }
    
    public static ArrayList<User> getUsers()
    {
        ArrayList<User> toRet = new ArrayList();
        try
        {
            JSONParser parser = new JSONParser();
   
                        
            JSONObject parsedFile = (JSONObject)parser.parse(new FileReader(infosFile));
            
            JSONArray users = (JSONArray)parsedFile.get("users");
            Iterator<JSONObject> iterator = users.iterator();
            while(iterator.hasNext())
            {
                JSONObject user = iterator.next();
                toRet.add(new User(user.get("user").toString(), user.get("pass").toString()));
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
            
            JSONObject parsedFile = (JSONObject)parser.parse(new FileReader(mailsFile));
            
            JSONArray mails = (JSONArray)parsedFile.get("mails");
            Iterator<JSONObject> iterator = mails.iterator();
            while(iterator.hasNext())
            {
                JSONObject mailJSON = iterator.next();
                
                JSONObject expJSON = (JSONObject) mailJSON.get("from");
                JSONObject recJSON = (JSONObject) mailJSON.get("to");
                
                String exp  = (String) expJSON.get("adress");
                String expName = (String) expJSON.get("adress");
                String recName = (String) recJSON.get("adress");
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
    
    public static boolean markMailAsForUser(String user, MailTagEnum tag, int mailNumber)
    {
        int cptMailUser = 0;
        try
        {
            JSONParser parser = new JSONParser();
            
            FileReader reader = new FileReader(mailsFile);
            
            JSONObject parsedFile = (JSONObject)parser.parse(reader );
            
            JSONArray mails = (JSONArray)parsedFile.get("mails");
            Iterator<JSONObject> iterator = mails.iterator();
            while(iterator.hasNext())
            {
                JSONObject mailJSON = iterator.next();
                
                JSONObject recJSON = (JSONObject) mailJSON.get("to");
                
                if(recJSON.get("adress").equals(user))
                {
                    cptMailUser++;
                    if(cptMailUser == mailNumber)
                    {
                        mailJSON.put("balise", tag.toString());
                    }
                }
            }
            
            reader.close();
            
            FileWriter writer = new FileWriter(mailsFile);
            
            writer.write(parsedFile.toJSONString());
            writer.flush();
            writer.close();
            
            return true;
        } catch (IOException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ParseException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static String getPassForUser(String username)
    {
        String pass = "";
        
        try
        {
            JSONParser parser = new JSONParser();
            
            JSONObject parsedFile = (JSONObject)parser.parse(new FileReader(infosFile));
            
            JSONArray users = (JSONArray)parsedFile.get("users");
            Iterator<JSONObject> iterator = users.iterator();
            while(iterator.hasNext())
            {
                JSONObject user = iterator.next();
                if(username.equals(user.get("user")))
                {
                    pass = user.get("pass").toString();
                    return pass;
                }
            }
            
            return pass;
        } catch (IOException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean deleteMailsMarkAsDeletedForUser(String username)
    {
        try
        {
            JSONParser parser = new JSONParser();
            
            FileReader reader = new FileReader(mailsFile);
            
            JSONObject parsedFile = (JSONObject)parser.parse(reader);
            
            JSONArray mails = (JSONArray)parsedFile.get("mails");
            ArrayList<JSONObject> mailsToRemove = new ArrayList();
            Iterator<JSONObject> iterator = mails.iterator();
            while(iterator.hasNext())
            {
                JSONObject mailJSON = iterator.next();
                
                JSONObject recJSON = (JSONObject) mailJSON.get("to");
                
                if(recJSON.get("adress").equals(username) && mailJSON.get("balise").equals(MailTagEnum.DELETED.toString()))
                {
                    mailsToRemove.add(mailJSON);
                }
            }
            for(JSONObject mail : mailsToRemove)
            {
                mails.remove(mail);
            }
            
            reader.close();
            
            FileWriter writer = new FileWriter(mailsFile);
            
            writer.write(parsedFile.toJSONString());
            writer.flush();
            writer.close();
            
            return true;
        } catch (IOException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ParseException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean unmarkDeleteMails(String user)
    {
        try
        {
            JSONParser parser = new JSONParser();
            
            FileReader reader = new FileReader(mailsFile);
            
            JSONObject parsedFile = (JSONObject)parser.parse(reader);
            
            JSONArray mails = (JSONArray)parsedFile.get("mails");
            Iterator<JSONObject> iterator = mails.iterator();
            while(iterator.hasNext())
            {
                JSONObject mailJSON = iterator.next();
                
                JSONObject recJSON = (JSONObject) mailJSON.get("to");
                
                if(recJSON.get("adress").equals(user))
                {
                    if(mailJSON.get("balise").equals(MailTagEnum.DELETED.toString()))
                    {
                        mailJSON.put("balise", MailTagEnum.READ.toString());
                    }
                }
            }
            
            reader.close();
            
            FileWriter writer = new FileWriter(mailsFile);
            
            writer.write(parsedFile.toJSONString());
            writer.flush();
            writer.close();
            
            return true;
        } catch (IOException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ParseException ex)
        {
            Logger.getLogger(ParserJSON.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
