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
public class DELEEvent extends Event
{
    private Integer msgID;
    
    public DELEEvent(Integer msgID)
    {
        super(EventEnum.DELE);
        this.msgID = msgID;
    }
    
    public Integer getMsgID()
    {
        return this.msgID;
    }
}
