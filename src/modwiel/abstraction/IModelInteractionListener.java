/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel.abstraction;

import modwiel.model.Field;

/**
 *
 * @author student
 */
public interface IModelInteractionListener {
    public void onRefresh( Field[][] data );
    public Field[][] getData();
}
