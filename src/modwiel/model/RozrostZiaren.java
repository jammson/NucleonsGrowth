/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel.model;
 
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Random;

import modwiel.abstraction.IModelInteractionListener;
import modwiel.abstraction.INeighborhood;
import modwiel.abstraction.ITransitionRule;
import modwiel.impl.CATransitionRule;

/**
 *
 * @author student
 */
public class RozrostZiaren {

    private IModelInteractionListener listener;
    private INeighborhood neighborhood;
    private ArrayList<ITransitionRule> rules;
     
    private Field[][] fields;

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }
    
    private int size = 20;
    private boolean periodic = false;
    
    public RozrostZiaren( boolean periodic , INeighborhood neighborhood ) {
        this.neighborhood = neighborhood;
        this.rules = new ArrayList<ITransitionRule>(); 
        this.rules.add(new CATransitionRule());
        this.periodic = periodic;
        clear();
    }
    
    public void setListener( IModelInteractionListener listener ){
        fields = listener.getData();
        size = fields.length;
        this.listener = listener;
    }
    
    public void clear(){
        fields = new Field[size][size];
        for( int i = 0 ; i < fields.length ; i++ ){
            for( int j = 0 ; j < fields[i].length ; j++ ){
                   fields[i][j] = new Field(Field.DEAD, Color.BLACK );
            }
        }
        refreshData();
    }
    
    public void step(){
        Field[][] newFields = new Field[size][size];
        for( int i = 0 ; i < fields.length ; i++ ){
            for( int j = 0 ; j < fields[i].length ; j++ ){
                Field[][] boundary = neighborhood.getBoundary( fields , i  , j , periodic );
                for(ITransitionRule r : rules){
                    Field f = r.transition(fields, boundary , neighborhood.getType() , fields[i][j] );
                    if( f != null ){
                        newFields[i][j] = f;
                        break;
                    }else{
                        newFields[i][j] = fields[i][j];
                    }
                }
            }
        }
        fields = newFields;
        refreshData();
    }
    
    public boolean isFilled(){
       for( int i = 0 ; i < fields.length ; i++ ) 
            for( int j = 0 ; j < fields[i].length ; j++ ) 
                if(fields[i][j].getType()==Field.DEAD)return false;
        return true;
    }
    
    private void refreshData(){
        if(listener!=null){
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    listener.onRefresh(fields);
                }
            });
        }
    }
    
    private int randColor(){
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b).getRGB();
    }
    
}
