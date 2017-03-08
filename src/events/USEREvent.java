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
public class USEREvent extends Event
{
    private String username;
    
    public USEREvent(String user)
    {
        super(EventEnum.USER);
        this.username = user;
    }
    
    public String getUserName()
    {
        return this.username;
    }
}
