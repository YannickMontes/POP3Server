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
    private Integer lineNumber;
    
    public TOPEvent(Integer msgID, Integer lNum)
    {
        super(EventEnum.TOP);
        this.msgID = msgID;
        this.lineNumber = lNum;
    }
    
    public Integer getMessageID()
    {
        return this.msgID;
    }
    
    public Integer getLineNumber()
    {
        return this.lineNumber;
    }
}
