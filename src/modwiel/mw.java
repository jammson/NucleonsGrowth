/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel;

import java.awt.*;

/**
 * @author student
 */
public class mw
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new ZiarnaJFrame().setVisible(true);
            }
        });
    }

}
