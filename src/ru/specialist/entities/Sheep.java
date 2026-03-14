/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.specialist.entities;

/**
 *
 * @author artyom
 */
public class Sheep implements ChangeDay {
    private static final int MAX_AGE = 1800;
    private static final int MATUR_AGE = 360;
    private static final double MAX_RESERVE_CAP = 50;  // reserve capacity
    
    private static final double PREG_RESERVE_CAP = 15;  // pregnancy reserve 
                                                    // capacity
    
    private static final double MATURE_DC = 2;   // daily consumption of mature
                                                // pregnant sheep
    
    private static final double DC = 1.7;   // daily consumption of mature and
                                            // immature sheep
    
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
    
                                                // also per day increase of 
                                                // resource capacity for 
                                                // immature sheep
    
    private static final double R_BUILD_PER_DAY_CONS = R_CONS_PART * MATURE_DC -
                                                R_FILL_PER_DAY_IMM;
                                                // daily consumption for 
                                                // buildup of reserve
                                                // capacity for immature sheep
    
    
    private static int sheepCounter = 0;
    private int sheepId;
    private boolean alive;
    private boolean mature;
    private int age;    // sheep age
    private double reserveCap;  // sheep reserve capacity
    private double reserveFill; // how much of resoruce is filled
    
    private double consInDay;   // how much of resource totally was consumed 
                                    // per day
    private double livingConsDay;   // how much of resource for living was 
                                    // consumed per day
    private double resBConsDay; // how much of resource for building reserves
                                // was consumed per day
    private double resFConsDay; // how much of resource for filling reserves
                                // was consumed per day
    
    public Sheep() {
        this.sheepId = sheepCounter;
        sheepCounter++;
        
        this.mature = false;
        this.alive = true;
        this.age = 0;
        this.reserveCap = 0;
        this.reserveFill = 0;
        
        this.consInDay = 0;
        this.livingConsDay = 0;
        this.resBConsDay = 0;
        this.resFConsDay = 0;
    }

    public double getConsInDay() {
        return consInDay;
    }

    public double getLivingConsDay() {
        return livingConsDay;
    }

    public double getResBConsDay() {
        return resBConsDay;
    }

    public double getResFConsDay() {
        return resFConsDay;
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
        /*if (this.age >= MATUR_AGE && isMature() == false) {
            setMature(true);
        }*/
        
        if (this.age >= MAX_AGE && isAlive() == true) {
            setAlive(false);
        }
    }

    public void setReserveCap(double reserveCap) {        
        if (reserveCap > MAX_RESERVE_CAP) {
            this.reserveCap = MAX_RESERVE_CAP;
            setMature(true);
        } else {
            this.reserveCap = reserveCap;
        }
    }

    public void setReserveFill(double reserveFill) {
        if (reserveFill > reserveCap) {
            this.reserveFill = reserveCap;
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

    public void setConsInDay(double consInDay) {
        this.consInDay = consInDay;
    }

    public void setLivingConsDay(double livingConsDay) {
        this.livingConsDay = livingConsDay;
    }

    public void setResBConsDay(double resBConsDay) {
        this.resBConsDay = resBConsDay;
    }

    public void setResFConsDay(double resFConsDay) {
        this.resFConsDay = resFConsDay;
    }
    
    public void updConsInDay() {
        if (mature) {
            setConsInDay(getLivingConsDay() + getResFConsDay());
        } else {
            setConsInDay(getLivingConsDay() + getResBConsDay() +
                        getResFConsDay());
        }
    }
    
    @Override
    public void dayPasses() {
        if (getResFConsDay() < LIV_CONS - getLivingConsDay()) {
            alive = false;
            setResBConsDay(0);
        } else {
            setResFConsDay(getResFConsDay() - (LIV_CONS - getLivingConsDay()));
            setAge(getAge() + 1);
            if (!isMature() && (getResBConsDay() == R_BUILD_PER_DAY_CONS)) {
                setReserveCap(getReserveCap() + R_FILL_PER_DAY_IMM);
                setResBConsDay(0);
            }
            setReserveFill(getReserveFill() + getResFConsDay());
        }
        setLivingConsDay(0);
        setResFConsDay(0);
        updConsInDay();
    }
    
    public double eat(double availRes) {
        if (mature) {
            return eatMatureNP(availRes);
        } else {
            return eatImmature(availRes);
        }
    }
    
    public double eatMatureNP(double availRes) {
        // how much resource is consumed during this iteration
        double resCons = Math.min(availRes, DC - getConsInDay());

        if (resCons > LIV_CONS - getLivingConsDay()) {
            // how much resource is left for reserves
            double resB = resCons - (LIV_CONS - getConsInDay());
            
            reserveFill += resB;
        } else {
            setLivingConsDay(getLivingConsDay() + resCons);
        }

        updConsInDay();
        
        return resCons;
    }
    
    
    public double eatImmature(double availRes) {
        double resCons = Math.min(availRes, DC - getConsInDay());
        double resB = 0;
        
        // Deficit of consumption required for living per day
        double LivingConsDayDeficit = LIV_CONS - getLivingConsDay();
        if (resCons < LivingConsDayDeficit) {
            setLivingConsDay(getLivingConsDay() + resCons);
        } else {
            // Deficit of consumption required for reserve building per day
            double resBConsDayDeficit = R_BUILD_PER_DAY_CONS - getResBConsDay();
            if (resCons < resBConsDayDeficit + LivingConsDayDeficit) {
                resB = resCons - LivingConsDayDeficit;
                setLivingConsDay(LIV_CONS);
                setResBConsDay(getResBConsDay() + resB);
            } else {
                // Deficit of consumption required for reserve filling per day
                double ResFConsDayLiving = R_FILL_PER_DAY_IMM -
                        getResFConsDay();
                if (resCons <= resBConsDayDeficit + LivingConsDayDeficit +
                        ResFConsDayLiving) {
                    resB = resCons - (LivingConsDayDeficit);
                    setLivingConsDay(LIV_CONS);
                    resB -= resBConsDayDeficit;
                    setResBConsDay(R_BUILD_PER_DAY_CONS);
                    setResFConsDay(getResFConsDay() + resB);
                }
            }
        }
        
        updConsInDay();
        
        return resCons;
    }
    
    public void matureNPReserveSpend() {
        if (LIV_CONS < getLivingConsDay()) {
            if (LIV_CONS - getLivingConsDay() <= getReserveFill()) {
                setReserveFill(getReserveFill() -
                        (LIV_CONS - getLivingConsDay()));
            } else {
                setAlive(false);
            }
        }
    }
    
    @Override
    public String toString() {
        return "Sheep id " + sheepId + "\n" +
                "Alive " + alive + "\n" +
                "Mature " + mature + "\n" +
                "Age " + age + "\n" +
                "ReserveCap " + getReserveCap() + "\n" +
                "ReserveFill " + getReserveFill() + "\n" +
                "getLivingConsDay " + getLivingConsDay() + "\n" +
                "resBConsDay " + getResBConsDay() + "\n" +
                "resFConsDay " + getResFConsDay() + "\n";
    }
}