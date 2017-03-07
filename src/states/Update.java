/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import events.DELEEvent;
import events.STATEvent;
import utils.Utils;

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
    public StateAnswer LauchAPOP(APOPEvent apop)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(apop.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchDELE(DELEEvent dele) {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(dele.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchSTAT(STATEvent stat)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(stat.getEventName(), this.getStateName()));
    }
    
}
