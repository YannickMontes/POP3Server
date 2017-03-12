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
public class TOPEvent extends Event
{
    private Integer msgID;
    
    public TOPEvent(Integer msgID)
    {
        super(EventEnum.TOP);
        this.msgID = msgID;
    }
    
    public Integer getMsgID()
    {
        return this.msgID;
    }
}
