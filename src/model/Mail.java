/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import utils.MailTagEnum;

/**
 *
 * @author yoannlathuiliere
 */
public class Mail {
    private String expeditorName;
    private String expeditor;
    private String receptorName;
    private String receptor;
    private String subject;
    private String date;
    private String messageID;
    private MailTagEnum tag;
    private String body;
    
    public Mail(String expName, String exp, String recName, String rec, String sub, String dat, String msgID, MailTagEnum tag, String body) {
        this.expeditorName = expName;
        this.expeditor = exp;
        this.receptorName = recName;
        this.receptor = rec;
        this.subject = sub;
        this.date = dat;
        this.messageID = msgID;
        this.tag = tag;
        this.body = body;
    }
    
    public String getExpeditorName() {
        return expeditorName;
    }

    public String getExpeditor() {
        return expeditor;
    }

    public String getReceptorName() {
        return receptorName;
    }

    public String getReceptor() {
        return receptor;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getMessageID() {
        return messageID;
    }

    public MailTagEnum getTag() {
        return tag;
    }

    public String getBody() {
        return body;
    }
    
    public String getBody(Integer lines) {
        String body = this.body;
        
        String[] bodyLines = body.split("\r\n");
        
        if(lines >= bodyLines.length) {
            return this.body;
        } else {
            String returnString = "";
            
            for(int i=0; i<=lines -1; i++){
                if(i != 0){
                    returnString += "\r\n";
                }
                returnString += bodyLines[i];
            }
            
            return returnString;
        }
    }
    
    public void deleteMessage() {
        this.tag = MailTagEnum.DELETED;
    }

    @Override
    public String toString()
    {
        String toString="";
    
        toString+= "From: "+this.expeditorName+" <"+this.expeditor+">\r\n";
        toString+= "To: "+this.receptorName+" <"+this.receptor+">\r\n";
        toString+= "Date: "+this.date+"\r\n";
        toString+= "Subject: "+this.subject+"\r\n\r\n";
        toString+= this.body+"\r\n";
        
        return toString;
    }
    
    public String toStringTop(Integer lineNum)
    {
        String toString="";
    
        toString+= "From: "+this.expeditorName+" <"+this.expeditor+">\r\n";
        toString+= "To: "+this.receptorName+" <"+this.receptor+">\r\n";
        toString+= "Date: "+this.date+"\r\n";
        toString+= "Subject: "+this.subject+"\r\n\r\n";
        toString+= this.getBody(lineNum) +"\r\n";
        
        return toString;
    }
}
