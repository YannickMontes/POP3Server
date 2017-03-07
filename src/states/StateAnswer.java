/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

/**
 *
 * @author yannick
 */
public class StateAnswer
{
    private State nextState;
    private String answer;
    
    public StateAnswer(State nextState, String answer)
    {
        this.answer = answer;
        this.nextState = nextState;
    }

    public State getNextState()
    {
        return nextState;
    }

    public String getAnswer()
    {
        return answer;
    }
    
    
}
