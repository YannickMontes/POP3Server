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
public class PASSEvent extends Event
{
    private String pass;
    
    public PASSEvent(String pass)
    {
        super(EventEnum.PASS);
        this.pass = pass;
    }
    
    public String getPass()
    {
        return this.pass;
    }
}
