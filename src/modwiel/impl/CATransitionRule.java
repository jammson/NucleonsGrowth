/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel.impl;

import java.awt.Color;
import java.util.ArrayList;
import modwiel.ZiarnaJFrame;
import modwiel.abstraction.ITransitionRule;
import modwiel.helper.ColorHelper;
import modwiel.model.Field;

/**
 *
 * @author student
 */
public class CATransitionRule implements ITransitionRule{

    @Override
    public Field transition(Field[][] allPanel,Field[][] boundary, int type, Field input) { 
        if(input.getType()==Field.ALIVE)return null;
        
        ArrayList<Color> types = new ArrayList<>();
        for(int i = 0 ; i < boundary.length; i++)
            for(int j = 0 ; j < boundary[i].length; j++)
                if(boundary[i][j]!=null && boundary[i][j].getType() == Field.ALIVE)
                    types.add(boundary[i][j].getColor());
              
        //ColorHelper.randInt(0, types.size()-1)
        if( types.size() > 0 ){
            int iteratorFor_0 = 0;
            int iteratorFor_1 = 0;
            if(types.size()==2 && (!types.get(0).equals(types.get(1)))){
                for(int i =0;i<allPanel.length;i++){
                    for(int j=0;j<allPanel[i].length;j++){
                        Color col_panel = allPanel[i][j].getColor();
                        String col_fromPanel = String.format("#%06x", col_panel.getRGB() & 0x00FFFFFF);
                        String col_0 = String.format("#%06x", types.get(0).getRGB() & 0x00FFFFFF);
                        String col_1 = String.format("#%06x", types.get(1).getRGB() & 0x00FFFFFF);
                        
                        if(col_fromPanel.equals(col_0)){
                            iteratorFor_0++;
                        }
                        if(col_fromPanel.equals(col_1)){
                            iteratorFor_1++;
                        }
                    }
                }
            }
            int max = 0;
            
            if(iteratorFor_0 <iteratorFor_1){
                max = 1;
            }
            
            Color c = types.size()==1 ? types.get(0): types.get(max);
            Field f = new Field(Field.ALIVE,c );
            return f;
        } 
        return null;
    }
    
    public class ColorsTmp
    {
        Color color;
        int colorcCount;

        public ColorsTmp() {
        }
        
        public ColorsTmp(Color color, int colorcCount) {
            this.color = color;
            this.colorcCount = colorcCount;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public int getColorcCount() {
            return colorcCount;
        }

        public void setColorcCount(int colorcCount) {
            this.colorcCount = colorcCount;
        }
        
        
    }
    
}
