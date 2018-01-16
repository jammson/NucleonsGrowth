/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modwiel.model;

import modwiel.abstraction.IModelInteractionListener;
import modwiel.abstraction.INeighborhood;
import modwiel.abstraction.ITransitionRule;
import modwiel.impl.RecrystallisationRule;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author student
 */
public class Rekrystalizacja
{
    private IModelInteractionListener listener;
    private INeighborhood neighborhood;
    private ArrayList<ITransitionRule> rules;

    private Field[][] fields;

    private int size = 20;
    private boolean periodic = false;

    private double tmax;
    private double t = 0;
    private double dt = 0.001;
    private double lastP = 0;
    private double A;
    private double B;
    private double pkr;

    public Rekrystalizacja(boolean periodic, INeighborhood neighborhood, double a, double b, double pkr, double tmax)
    {
        this.neighborhood = neighborhood;
        this.rules = new ArrayList<ITransitionRule>();
        this.rules.add(new RecrystallisationRule(a, b, pkr, this));
        this.periodic = periodic;
        this.tmax = tmax;
        this.A = a;
        this.B = b;
        this.pkr = pkr;
        clear();
        System.err.println("a=" + a);
        System.err.println("b=" + b);
        System.err.println("pkr=" + pkr);
        System.err.println("tmax=" + tmax);
    }

    public double getT()
    {
        return t;
    }

    //ρ = A/B + (1-A/B)e –Bt
    public double getP()
    {
        double s = (double) getSize();
        return (A / B + (1 - (A / B)) * Math.exp(-B * t)) / (s * s);
    }

    public double getLastP()
    {
        return lastP;
    }

    public int getSize()
    {
        return fields.length;
    }

    public void setListener(IModelInteractionListener listener)
    {
        fields = listener.getData();
        size = fields.length;
        this.listener = listener;
    }

    public void clear()
    {
        fields = new Field[size][size];
        for (int i = 0; i < fields.length; i++)
        {
            for (int j = 0; j < fields[i].length; j++)
            {
                fields[i][j] = new Field(Field.DEAD, Color.BLACK);
            }
        }
        refreshData();
    }

    public void step()
    {
        t += dt;
        Field[][] newFields = new Field[size][size];
        for (int i = 0; i < fields.length; i++)
        {
            for (int j = 0; j < fields[i].length; j++)
            {
                Field[][] boundary = neighborhood.getBoundary(fields, i, j, periodic);
                for (ITransitionRule r : rules)
                {
                    Field f = r.transition(fields, boundary, neighborhood.getType(), fields[i][j]);
                    if (f != null)
                    {
                        newFields[i][j] = f;
                        break;
                    } else
                    {
                        newFields[i][j] = fields[i][j];
                    }
                }
            }
        }
        System.out.printf(t + " %f" + " " + wyswietlRo() + "\n", suma(fields));
        lastP = getP();
        fields = newFields;
        refreshData();

    }

    public boolean isFilled()
    {
        return t >= tmax;
    }

    private void refreshData()
    {
        if (listener != null)
        {
            EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    listener.onRefresh(fields);
                }
            });
        }
    }

    private int randColor()
    {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b).getRGB();
    }

    public double suma(Field[][] fields)
    {
        double suma = 0;

        for (int i = 0; i < fields.length; i++)
        {
            for (int j = 0; j < fields.length; j++)
            {
                if (fields[i][j] != null)
                {
                    suma += fields[i][j].getP();
                }
            }

        }
        return suma;
    }

    public double wyswietlRo()
    {
        return (A / B + (1 - (A / B)) * Math.exp(-B * t));
    }
}
