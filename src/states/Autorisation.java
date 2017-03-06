/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import events.APOPEvent;
import json_parser.ParserJSON;

/**
 *
 * @author yannick
 */
public class Autorisation extends State
{
    
    public Autorisation()
    {
        super(StateEnum.AUTORISATION);
    }

    @Override
    public void LauchAPOP(APOPEvent apop)
    {
        System.out.println("Vérifications des informations");
        //Traitement APOP
        System.out.println("User: "+apop.getUser() + " Pass: "+ apop.getPass());
        
        ParserJSON.getUsers();
    }
}
