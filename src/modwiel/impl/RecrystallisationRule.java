/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel.impl;

import modwiel.abstraction.ITransitionRule;
import modwiel.helper.ColorHelper;
import modwiel.model.Field;
import modwiel.model.Rekrystalizacja;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author student
 */
public class RecrystallisationRule implements ITransitionRule
{

    private double A;
    private double B;
    private double pkr;

    private double lastP = 0;


    private Rekrystalizacja parent;

    public RecrystallisationRule(double a, double b, double pkr, Rekrystalizacja parent)
    {
        this.A = a;
        this.B = b;
        this.pkr = pkr;
        this.parent = parent;
    }

    @Override
    public Field transition(Field[][] allPanel,Field[][] boundary, int type, Field input)
    {
        if (input.getType() == Field.NEWBORN) return input;
        double t = parent.getT();
        double s = (double) parent.getSize();

        double p = parent.getP();
        double dp = Math.abs(lastP - p);
        lastP = parent.getLastP();

        boolean isOnBorder = isOnBorder(boundary);
        double rp = isOnBorder ?
                (((double) ColorHelper.randInt(120, 180)) / 100.0) * dp : // $ na granicy ziarna: ρ=(120% – 180%) ρ średniego
                (((double) ColorHelper.randInt(0, 30)) / 100.0) * dp;  // $ wewnątrz ziarna: ρ=(0%-30%) ρ średniego

        input.incP(rp);
        //System.err.println("t="+t+"dp="+dp+" p= " + input.getP() + " != " + (pkr/(s*s)) + " " + (input.getP()==(pkr/(s*s))) );

        //sprawdzanie wartości ρ w komórkach:  jeżeli ρ  > ρ krytyczne
        // to komórka zmienia stan na zrekrystalizowany, przyjmuje nowe, kolejne ID i ρ tej komórki maleje do 0.
        if (input.getP() > (pkr / (s * s)))
        {
            input.setType(Field.NEWBORN);
            input.setP(0);
            input.setColor(ColorHelper.gerRandomColor());
        } else /*if(input.getType()!=Field.NEWBORN)*/
        {
            ArrayList<Color> types = new ArrayList<>();
            for (int i = 0; i < boundary.length; i++)
                for (int j = 0; j < boundary[i].length; j++)
                    if (boundary[i][j] != null && boundary[i][j].getType() == Field.NEWBORN)
                        types.add(boundary[i][j].getColor());

            // rozrost zarodków: jeżeli w poprzednim kroku czasowym sąsiad komórki zrekrystalizował
            // to ta komórka ulega rekrystalizacji i jej ρ zmienia się na 0
            if (types.size() > 0)
            {
                Color c = types.size() == 1 ? types.get(0) : types.get(ColorHelper.randInt(0, types.size() - 1));
                Field f = new Field(Field.NEWBORN, c);
                f.setP(0);
                return f;
            }

        }
        return input;
    }

    public boolean isOnBorder(Field[][] boundary)
    {
        Color c = null;
        for (int i = 0; i < boundary.length; i++)
            for (int j = 0; j < boundary[i].length; j++)
            {
                if (boundary[i][j] == null) continue;
                if (c == null)
                {
                    c = boundary[i][j].getColor();
                } else if (!c.equals(boundary[i][j].getColor()))
                {
                    return true;
                }
            }
        return false;
    }

}
