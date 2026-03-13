/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.specialist.entities;

/**
 *
 * @author artyom
 */
public class GrassAcre implements ChangeDay {
    private static final double maxLen = 3;     // maximum length that grass can
                                                // grow to
    private static final double minLenToEat = 1;    // minimal length where
                                                    // sheep can eat grass
    private static int grassCounter = 0;
    private int grassId;
    private double length;  // length of grass
    

    public GrassAcre(double len) {
        this.grassId = grassCounter;
        grassCounter++;
        setLength(len);
    }
    
    public void setLength(double length) {
        if (length <= maxLen) {
            this.length = length;
        } else {
            this.length = maxLen;
        }
    }

    public double getLength() {
        return length;
    }
    
    public void increment() {
        setLength(getLength() + 1);
    }
    
    @Override
    public void dayPasses() {
        increment();
    }
    
    public double availRes() {
        return length - minLenToEat > 0 ? length - minLenToEat : 0;
    }
    
    public void resourceEaten(double res) {
        setLength(getLength() - res);
    }
    
    @Override
    public String toString() {
        return "Grass id " + grassId + "\n" +
                "Grass length " + getLength() + "\n";
    }
}