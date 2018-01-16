/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel.impl;

import modwiel.abstraction.INeighborhood;
import modwiel.model.Field;

/**
 *
 * @author student
 */
public class Newman implements INeighborhood{

    @Override
    public int getType() { 
       return Newman.MOORE;
    }
  
    @Override
    public Field[][] getBoundary(Field[][] data, int i, int j, boolean b) { 
        Moore more = new Moore();
        Field[][] bound = more.getBoundary(data, i, j, b); 
        bound[0][0] = null;
        bound[0][2] = null;
        bound[2][0] = null;
        bound[2][2] = null;
        return bound;
    }
        
    
}
