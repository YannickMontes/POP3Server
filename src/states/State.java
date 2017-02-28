/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.EventEnum;
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
    
    public abstract void LauchAPOP();
    public abstract void LauchSTAT();
    public abstract void LauchLIST();
    public abstract void LauchDELE();
    public abstract void LauchQUIT();
    public abstract void LauchRETR();
}
