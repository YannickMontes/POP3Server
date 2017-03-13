/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import events.DELEEvent;
import events.LISTEvent;
import events.NOOPEvent;
import events.PASSEvent;
import events.QUITEvent;
import events.RETREvent;
import events.RSETEvent;
import events.STATEvent;
import events.TOPEvent;
import events.USEREvent;
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

    @Override
    public StateAnswer LauchRETR(RETREvent retr)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(retr.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchLIST(LISTEvent list)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(list.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LaunchUSER(USEREvent user)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(user.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LaunchPASS(PASSEvent pass)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(pass.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LauchQUIT(QUITEvent quit)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(quit.getEventName(), this.getStateName()));
    }
    
    @Override
    public StateAnswer LaunchNOOP(NOOPEvent noop)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(noop.getEventName(), this.getStateName()));
    }
    
    @Override
    public StateAnswer LaunchRSET(RSETEvent rset)
    {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(rset.getEventName(), this.getStateName()));
    }

    @Override
    public StateAnswer LaunchTOP(TOPEvent top) {
        return new StateAnswer(null, Utils.CreateStringCommandNotHandleInThisState(top.getEventName(), this.getStateName()));
    }
}
