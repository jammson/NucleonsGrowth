/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel.service;

import java.awt.EventQueue;
import modwiel.model.Rekrystalizacja;


/**
 *
 * @author student
 */
public class WorkerThread2 extends Thread{
    
    private boolean isRunning = true;
    private boolean isPaused = false;
    
    private int milis = 5;
    private Runnable endAction;
    private Rekrystalizacja model;
    
    public WorkerThread2( Rekrystalizacja model , Runnable endAction ) {
        this.model = model;
        this.endAction = endAction;
    }

    public int getMilis()
    {
        return milis;
    }

    public void setMilis(int milis)
    {
        this.milis = milis;
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
        while (isRunning) {   
            //System.err.println("isRunning");
            if(!isPaused){
                model.step();
                
            }
            try{
                Thread.sleep(milis);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            if( model.isFilled() ){
                isRunning = false;
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        endAction.run();
                    }
                }); 
            }
        } 
    } 
}
