/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel.service;

import modwiel.model.Automat;
import java.awt.EventQueue;
import modwiel.FieldJPanel;

/**
 *
 * @author student
 */
public class AnimationThread extends Thread{
    
    private boolean isRunning = true;
    private boolean isPaused = false;
    
    private int milis = 100;
    private int steps;
    private Automat automat;
    private FieldJPanel panel;
    
    public AnimationThread( FieldJPanel panel , Automat automat , int steps , int milis ) {
        this.milis = milis;
        this.steps = steps;
        this.automat = automat;
        this.panel = panel;
    }
    
    public void stopAnim(){
        isRunning = false;
    }
    
    public void pauseAnim(){
        isPaused = true;
    }
     
    public void resumeAnim(){
        isPaused = false;
    }

    @Override
    public void run() {
        
        new Thread(new Runnable() {
            @Override
            public void run() { 
                /* tutaj kod */
            }
        });
        
        while (isRunning) {   
            System.err.println("isRunning");
            if(!isPaused){
                automat.nexStep();
                EventQueue.invokeLater(new Runnable(){
                  public void run(){
                    panel.refreshData(automat.getData());
                 }
                });
            }
            try{
                Thread.sleep(milis);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        } 
    } 
}
