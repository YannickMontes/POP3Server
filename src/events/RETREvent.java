/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

/**
 *
 * @author yannick
 */
public class RETREvent extends Event
{
    private int messageID;
    
    public RETREvent(int messageId)
    {
        super(EventEnum.RETR);
        this.messageID = messageId;
    }

    public int getMessageID()
    {
        return messageID;
    }
    
    
}
