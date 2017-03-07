/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import events.DELEEvent;
import utils.Utils;

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
    public String LauchAPOP(APOPEvent apop)
    {
        return Utils.CreateStringCommandNotHandleInThisState(apop.getEventName(), this.getStateName());
    }

    @Override
    public String LauchDELE(DELEEvent dele) {
        return Utils.CreateStringCommandNotHandleInThisState(dele.getEventName(), this.getStateName());
    }
    
}
