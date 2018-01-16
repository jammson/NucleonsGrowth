/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel.model;

import java.util.Random;

/**
 *
 * @author student
 */
public class Automat {
    
    private int[][] data;
    private boolean b;
    private int size;
    
    public Automat(int size, boolean b) {
        this.size = size;
        data = new int[size][size];
        this.b = b;
    }
    
    public void setType( boolean b ){
        this.b = b;
    }
    
    public void nexStep(){
        int[][] newData = new int[size][size];
        for(int i = 0 ; i < size ; i++){
            for(int j = 0 ; j < size ; j++){
                int N = 0;
                
                if(i==0){
                    if(b){
                        N+=data[size-1][j];
                        
                        if(j==0){
                            if(b){
                                N+=data[size-1][size-1];
                            }
                        }else{
                            N+=data[size-1][j-1];
                        }
                        
                        if(j==size-1){
                            if(b){
                                N+=data[size-1][0];
                            }
                        }else{
                            N+=data[size-1][j+1];
                        } 
                    }
                }else{
                    N+=data[i-1][j];

                    if(j==0){
                        if(b){
                            N+=data[i-1][size-1];
                        }
                    }else{
                        N+=data[i-1][j-1];
                    }

                    if(j==size-1){
                        if(b){
                            N+=data[i-1][0];
                        }
                    }else{
                        N+=data[i-1][j+1];
                    }  
                }
                
                if(j==0){
                    if(b){
                        N+=data[i][size-1];
                    }
                }else{
                    N+=data[i][j-1];
                }

                if(j==size-1){
                    if(b){
                        N+=data[i][0];
                    }
                }else{
                    N+=data[i][j+1];
                }  
                 
                
                if(i==size-1){
                    if(b){
                        N+=data[0][j];
                        
                        if(j==0){
                            if(b){
                                N+=data[0][size-1];
                            }
                        }else{
                            N+=data[0][j-1];
                        }
                        
                        if(j==size-1){
                            if(b){
                                N+=data[0][0];
                            }
                        }else{
                            N+=data[0][j+1];
                        } 
                    }
                }else{
                    N+=data[i+1][j];

                    if(j==0){
                        if(b){
                            N+=data[i+1][size-1];
                        }
                    }else{
                        N+=data[i+1][j-1];
                    }

                    if(j==size-1){
                        if(b){
                            N+=data[i+1][0];
                        }
                    }else{
                        N+=data[i+1][j+1];
                    }  
                } 
                
                if(N==3 && data[i][j]==0){
                    newData[i][j] = 1;
                }
                if(data[i][j]==1 && (N==3 ||N==2)){
                    newData[i][j] = 1;
                }
                if(data[i][j]==1 && (N>3)){
                    newData[i][j] = 0;
                }
                if(data[i][j]==1 && (N<2)){
                    newData[i][j] = 0;
                }
                
            }
        }
        data = newData;
    }
    
    public void clear(){
        for(int i = 0 ; i < size ; i++){
            for(int j = 0 ; j < size ; j++){
                data[i][j] = Field.DEAD;
            }
        }
    }
    
    
    public void randData(){
        Random rand = new Random();
        data = new int[size][size];
        for(int i = 0 ; i < size ; i++){
            for(int j = 0 ; j < size ; j++){
                int r = rand.nextInt(100);
                if(r > 70){
                    data[i][j] = Field.ALIVE; 
                }else{
                    data[i][j] = Field.DEAD; 
                }
            }
        } 
    }
    
    public void reverse(int i , int j){
        if(data[i][j]==Field.ALIVE){
            data[i][j] = Field.DEAD;
        }else{
            data[i][j] = Field.ALIVE;
        }
    }
    
    public int[][] getData(){
        return data;
    }
    
    
}
