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
public class APOPEvent extends Event
{
    private String user;
    private String pass;
    
    public APOPEvent(String user, String pass)
    {
        super(EventEnum.APOP);
        this.user = user;
        this.pass = pass;
    }
    
    public String getUser()
    {
        return this.user;
    }  
    
    public String getPass()
    {
        return this.pass;
    }
}
