/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.specialist.entities;

/**
 *
 * @author artyom
 */
public class Sheep {
    private static final int maxAge = 1800;
    private static final int maturAge = 360;
    private static final double maxReserveCap = 50;  // reserve capacity
    
    private static final double pregReserveCap = 15;  // pregnancy reserve 
                                                    // capacity
    
    private static final double matureDC = 2;   // daily consumption of mature
                                                // sheep
    
    private static final double livConsPart = .35;  // part of consumption spend
                                                    // on living
    
    private static final double pregRConsPart = .15;  // part of consumption 
                                                    // spend on filling
                                                    // pregnancy reserve
    
    private static final double rConsPart = .50;  // part of consumption
                                                    // spend on filling reserve
    
    private static final double livCons = livConsPart * matureDC;  
                                                    // consumption spend on 
                                                    // living
    
    private static final double pregRCons = pregRConsPart * matureDC;  
                                                    // consumption spend on 
                                                    // filling pregnancy reserve

    private static final double rCons = rConsPart * matureDC;  // consumption
                                                    //  spend on filling
                                                    // reserve

    private static final double rFillPerDayImm = maxReserveCap / maturAge;
                                                // daily fill of reserve
                                                // for immature sheep
    
                                                // also fill per day of resource
                                                // capacity for immature sheep
    
    private static final double rBuildPerDayCons = rConsPart * matureDC -
                                                rFillPerDayImm;
                                                // daily consumption for 
                                                // buildup of reserve
                                                // capacity for immature sheep
    
    
    private boolean alive;
    private boolean mature;
    private int age;    // sheep age
    private double reserveCap;  // sheep reserve capacity
    private double reserveFill; // how much of resoruce is filled
    
    public Sheep() {
        this.mature = false;
        this.alive = true;
        this.age = 0;
        this.reserveCap = 0;
        this.reserveFill = 0;
    }

    public int getAge() {
        return age;
    }

    public double getReserveCap() {
        return reserveCap;
    }

    public double getReserveFill() {
        return reserveFill;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setReserveCap(double reserveCap) {
        if (reserveCap > maxReserveCap) {
            this.reserveCap = maxReserveCap;
            this.mature = true;
        } else {
            this.reserveCap = reserveCap;
        }
    }

    public void setReserveFill(double reserveFill) {
        if (reserveFill > maxReserveCap) {
            this.reserveFill = maxReserveCap;
        } else {
            this.reserveFill = reserveFill;
        }
    }
    
    public double eat(double availRes) throws Exception {
        if (mature) {
            return eatMatureNP(availRes);
        } else {
            return eatImmature(availRes);
        }
    }


    public double eatMatureNP(double availRes) throws Exception {
        if (availRes < livCons) {
            if (getReserveFill() < livCons - availRes) {
                alive = false;
                return availRes;
            } else {
                setReserveFill(getReserveFill() - (livCons - availRes));
                return availRes;
            }
        }
        
        if (availRes < livCons + rCons) {
            setReserveFill(getReserveFill() + livCons - availRes);
            return availRes;
        }
        
        if (availRes > livCons + rCons) {
            availRes = livCons + rCons;            
        }
        
        if (availRes <= livCons + rCons) {
            double availResB = Math.min(reserveCap - reserveFill,
                    availRes - livCons);
            setReserveFill(getReserveFill() + availResB);
            return livCons + availResB;
        }
        
        throw new Exception("The number of available resource " + availRes +
                " is not in the " +
                "list!");
    }
    
    public double eatImmature(double availRes) throws Exception {
        if (availRes < livCons) {
            age += 0;
            setReserveCap(getReserveCap() + 0);
            if (getReserveFill() < livCons - availRes) {
                alive = false;
                return availRes;
            } else {
                setReserveFill(getReserveFill() - (livCons - availRes));
                return availRes;
            }
        }
        
        if (availRes < rBuildPerDayCons + livCons) {
            age += 0;
            setReserveCap(getReserveCap() + 0);
            double factCons = availRes - livCons;
            double toRes = Math.min(factCons, getReserveCap() -
                    getReserveFill());
            setReserveFill(getReserveFill() + toRes);
            factCons -= toRes;
            availRes -= factCons;
            return availRes;
        }

        // ??? doubtful practice
        if (availRes > livCons + rCons) {
            availRes = livCons + rCons;
        }
        
        if (availRes <= livCons + rCons) {
            age += 1;
            setReserveCap(getReserveCap() + rFillPerDayImm);
            setReserveFill(getReserveFill() + availRes - livCons -
                    rBuildPerDayCons);
            return availRes;
        }
        
        throw new Exception("The number of available resource " + availRes +
                " is not in the " +
                "list!");
    }
    
    @Override
    public String toString() {
        return "Alive " + alive + "\n" +
                "Mature " + mature + "\n" +
                "Age " + age + "\n" +
                "ReserveCap " + getReserveCap() + "\n" +
                "ReserveFill " + getReserveFill() + "\n";
    }
    
    public static void main(String[] args) throws Exception {
        Sheep sheep = new Sheep();
        
        int i;
        int j;
        int k;
        
        for (i = 0; i < 370; i++) {
            System.out.println("Day " + i + "\n" + sheep.toString());
            System.out.println("Consume " + sheep.eat(1.7) + " grass\n");
        }
        
        System.out.println("==================================================");
        
        for (j = 0; j < 10; j++) {
            System.out.println("Day " + (i + j) + "\n" + sheep.toString());
            System.out.println("Consume " + sheep.eat(0.4) + " grass\n");
        }        
        
        System.out.println("==================================================");
        
        for (k = 0; k < 50; k++) {
            System.out.println("Day " + (i + j + k) + "\n" + sheep.toString());
            System.out.println("Consume " + sheep.eat(2.0) + " grass\n");
        }
    }
}