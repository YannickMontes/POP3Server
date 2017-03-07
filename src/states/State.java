/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.EventEnum;
import events.APOPEvent;
import events.DELEEvent;
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
    
    public abstract String LauchAPOP(APOPEvent apop);
    //public abstract void LauchSTAT(STATEvent stat);
    //public abstract void LauchLIST(LISTEvent list);
    public abstract void LauchDELE(DELEEvent dele);
    //public abstract void LauchQUIT(QUITEvent quit);
    //public abstract void LauchRETR(RETRvent retr);
    
    
}
