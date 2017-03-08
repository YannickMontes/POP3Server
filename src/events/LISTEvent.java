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
public class LISTEvent extends Event
{
    private int messageID;
    
    public LISTEvent()
    {
        super(EventEnum.LIST);
        this.messageID = -1;
    }
    
    public LISTEvent(int msgId)
    {
        super(EventEnum.LIST);
        this.messageID = msgId;
    }

    public int getMessageID()
    {
        return messageID;
    }
}
