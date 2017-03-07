/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import events.DELEEvent;

/**
 *
 * @author yannick
 */
public class Update extends State
{
    public Update()
    {
        super(StateEnum.UPDATE);
    }

    @Override
    public String LauchAPOP(APOPEvent apop)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String LauchDELE(DELEEvent dele) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
