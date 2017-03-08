/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.EventEnum;
import events.APOPEvent;
import events.DELEEvent;
import events.LISTEvent;
import events.RETREvent;
import events.STATEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yannick
 */
public abstract class State
{
    List<EventEnum> handledEvents;
    StateEnum currentState;
    
    public State(StateEnum state)
    {
        this.handledEvents = new ArrayList();
        this.currentState = state;
    }
    
    public String getStateName()
    {
        return this.currentState.toString();
    }
    
    public abstract StateAnswer LauchAPOP(APOPEvent apop);
    public abstract StateAnswer LauchSTAT(STATEvent stat);
    public abstract StateAnswer LauchLIST(LISTEvent list);
    public abstract StateAnswer LauchDELE(DELEEvent dele);
    //public abstract StateAnswer LauchQUIT(QUITEvent quit);
    public abstract StateAnswer LauchRETR(RETREvent retr);
}
