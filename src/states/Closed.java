/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;

/**
 *
 * @author yannick
 */
public class Closed extends State
{
    public Closed()
    {
        super(StateEnum.CLOSED);
    }

    @Override
    public void LauchAPOP(APOPEvent apop)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
