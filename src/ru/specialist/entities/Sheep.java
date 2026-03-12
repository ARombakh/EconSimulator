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
    private static final int MAX_AGE = 1800;
    private static final int MATUR_AGE = 360;
    private static final double MAX_RESERVE_CAP = 50;  // reserve capacity
    
    private static final double PREG_RESERVE_CAP = 15;  // pregnancy reserve 
                                                    // capacity
    
    private static final double MATURE_DC = 2;   // daily consumption of mature
                                                // sheep
    
    private static final double LIV_CONS_PART = .35;  // part of consumption spend
                                                    // on living
    
    private static final double PREG_R_CONS_PART = .15;  // part of consumption 
                                                    // spend on filling
                                                    // pregnancy reserve
    
    private static final double R_CONS_PART = .50;  // part of consumption
                                                    // spend on filling reserve
    
    private static final double LIV_CONS = LIV_CONS_PART * MATURE_DC;  
                                                    // consumption spend on 
                                                    // living
    
    private static final double PREG_R_CONS = PREG_R_CONS_PART * MATURE_DC;  
                                                    // consumption spend on 
                                                    // filling pregnancy reserve

    private static final double R_CONS = R_CONS_PART * MATURE_DC;  // consumption
                                                    //  spend on filling
                                                    // reserve

    private static final double R_FILL_PER_DAY_IMM = MAX_RESERVE_CAP / MATUR_AGE;
                                                // daily fill of reserve
                                                // for immature sheep
    
                                                // also fill per day of resource
                                                // capacity for immature sheep
    
    private static final double R_BUILD_PER_DAY_CONS = R_CONS_PART * MATURE_DC -
                                                R_FILL_PER_DAY_IMM;
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

    public boolean isMature() {
        return mature;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAge(int age) {
        this.age = age;
        // ??? should we check is-flags here?
        if (this.age >= MATUR_AGE && isMature() == false) {
            setMature(true);
        }
        
        if (this.age >= MAX_AGE && isAlive() == true) {
            setAlive(false);
        }
    }

    public void setReserveCap(double reserveCap) {
        if (reserveCap > MAX_RESERVE_CAP) {
            this.reserveCap = MAX_RESERVE_CAP;
            this.mature = true;
        } else {
            this.reserveCap = reserveCap;
        }
    }

    public void setReserveFill(double reserveFill) {
        if (reserveFill > MAX_RESERVE_CAP) {
            this.reserveFill = MAX_RESERVE_CAP;
        } else {
            this.reserveFill = reserveFill;
        }
    }

    public void setMature(boolean mature) {
        this.mature = mature;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public double eat(double availRes) {
        if (mature) {
            return eatMatureNP(availRes);
        } else {
            return eatImmature(availRes);
        }
    }

    public double eatMatureNP(double availRes) {
        setAge(getAge() + 1);
        if (availRes < LIV_CONS) {
            if (getReserveFill() < LIV_CONS - availRes) {
                alive = false;
                return availRes;
            } else {
                setReserveFill(getReserveFill() - (LIV_CONS - availRes));
                return availRes;
            }
        }
        
        if (availRes < LIV_CONS + R_CONS) {
            setReserveFill(getReserveFill() + LIV_CONS - availRes);
            return availRes;
        }
        
        // ??? doubtful practice
        if (availRes > LIV_CONS + R_CONS) {
            availRes = LIV_CONS + R_CONS;            
        }
        
        if (availRes <= LIV_CONS + R_CONS) {
            double availResB = Math.min(reserveCap - reserveFill,
                    availRes - LIV_CONS);
            setReserveFill(getReserveFill() + availResB);
            return LIV_CONS + availResB;
        }
        
        return availRes;
        /* ???
        throw new Exception("The quantity of available resource " + availRes +
                " is not in the list!");*/
    }
    
    public double eatImmature(double availRes) {
        setAge(getAge() + 1);
        if (availRes < LIV_CONS) {
            setReserveCap(getReserveCap() + 0);
            if (getReserveFill() < LIV_CONS - availRes) {
                alive = false;
                return availRes;
            } else {
                setReserveFill(getReserveFill() - (LIV_CONS - availRes));
                return availRes;
            }
        }
        
        if (availRes < R_BUILD_PER_DAY_CONS + LIV_CONS) {
            setReserveCap(getReserveCap() + 0);
            double factCons = availRes - LIV_CONS;
            double toRes = Math.min(factCons, getReserveCap() -
                    getReserveFill());
            setReserveFill(getReserveFill() + toRes);
            factCons -= toRes;
            availRes -= factCons;
            return availRes;
        }

        // ??? doubtful practice
        if (availRes > LIV_CONS + R_CONS) {
            availRes = LIV_CONS + R_CONS;
        }
        
        if (availRes <= LIV_CONS + R_CONS) {
            setReserveCap(getReserveCap() + R_FILL_PER_DAY_IMM);
            setReserveFill(getReserveFill() + availRes - LIV_CONS -
                    R_BUILD_PER_DAY_CONS);
            return availRes;
        }
        
        return availRes;
        
        /* ??? - how to handle this Exception?
        throw new Exception("The quantity of available resource " + availRes +
                " is not in the " +
                "list!");*/
    }
    
    @Override
    public String toString() {
        return "Alive " + alive + "\n" +
                "Mature " + mature + "\n" +
                "Age " + age + "\n" +
                "ReserveCap " + getReserveCap() + "\n" +
                "ReserveFill " + getReserveFill() + "\n";
    }
    /*
    public static void main(String[] args) throws Exception {
        Sheep sheep = new Sheep();
        
        int i;
        int j;
        int k;
        
        GrassAcre ga = new GrassAcre(3);
        
        for (i = 0; i < 370; i++) {
            ga.increment();
            System.out.println("Day " + i + "\n" + ga);
            System.out.println("Day " + i + "\n" + sheep);
            double eaten = sheep.eat(ga.availRes());
            System.out.println("Consume " + eaten + " grass\n");
            ga.resourceEaten(eaten);
            System.out.println(ga);
        }
        
        System.out.println("==================================================");
        
        for (j = 0; j < 10; j++) {
            System.out.println("Day " + (i + j) + "\n" + sheep.toString());
            System.out.println("Consume " + sheep.eat(0.4) + " grass\n");
            System.out.println(ga);
        }        
        
        System.out.println("==================================================");
        
        for (k = 0; k < 50; k++) {
            System.out.println("Day " + (i + j + k) + "\n" + sheep.toString());
            System.out.println("Consume " + sheep.eat(2.0) + " grass\n");
            System.out.println(ga);
        }
    }*/
}