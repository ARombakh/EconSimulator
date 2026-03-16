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
    
    private static final double LIV_CONS_PART = .35;  // part of consumption 
                                                    // spend on living
    
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
    
    private LivingContainer lc;
    private ResBuildContainer rbc;
    private ResFillContainer rfc;
    
    public Sheep() {
        this.sheepId = sheepCounter;
        sheepCounter++;
        
        this.mature = false;
        this.alive = true;
        this.age = 0;

        setLc(new LivingContainer());
        setRbc(new ResBuildContainer());
        setRfc(new ResFillContainer());
    }

    public LivingContainer getLc() {
        return lc;
    }

    public ResBuildContainer getRbc() {
        return rbc;
    }

    public ResFillContainer getRfc() {
        return rfc;
    }

    public int getAge() {
        return age;
    }

    public boolean isMature() {
        return mature;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setLc(LivingContainer lc) {
        this.lc = lc;
    }

    public void setRbc(ResBuildContainer rbc) {
        this.rbc = rbc;
    }

    public void setRfc(ResFillContainer rfc) {
        this.rfc = rfc;
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

    public void setMature(boolean mature) {
        this.mature = mature;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public double eat(double availRes) {
        double eatenRes = 0;
        
        eatenRes += this.lc.fill(availRes - eatenRes);
        if (availRes - eatenRes == 0) {
            return eatenRes;
        }
        
        if (!isMature()) {
            eatenRes += this.rbc.fill(availRes - eatenRes);
            if (availRes - eatenRes == 0) {
                return eatenRes;
            }            
        }        

        eatenRes += this.rfc.fill(availRes - eatenRes);
                
        return eatenRes;
    }
    
    @Override
    public void dayPasses() {
        lc.fill(rfc.extract(lc.getDeficit()));
        setAlive(lc.getDeficit() == 0);
        lc.update();
        rbc.update();
        rfc.update();
        setAge(getAge() + 1);
    }
    
    public class LivingContainer {
        private double deficit;
        
        public LivingContainer() {
            update();
        }

        public double getDeficit() {
            return deficit;
        }

        public void setDeficit(double deficit) {
            this.deficit = deficit;
        }

        public double fill(double input) {
            if (getDeficit() >= input) {
                setDeficit(getDeficit() - input);
                return input;
            } else {
                double output = getDeficit();
                setDeficit(0);
                return output;
            }
        }
        
        public void update() {
            setDeficit(LIV_CONS);
        }
        
        @Override
        public String toString() {
            return "Daily living consumption " + LIV_CONS + "\n" +
                    "Deficit " + getDeficit() + "\n";
        }
    }
    
    public class ResBuildContainer {
        private double deficit;
        private boolean todayRCUpdated;
        
        public ResBuildContainer() {
            setTodayRCUpdated(true);
            update();
        }

        public double getDeficit() {
            return deficit;
        }

        public boolean isTodayRCUpdated() {
            return todayRCUpdated;
        }

        public void setDeficit(double deficit) {
            this.deficit = deficit;
        }

        public void setTodayRCUpdated(boolean todayRCUpdated) {
            this.todayRCUpdated = todayRCUpdated;
        }

        public double fill(double input) {
            if (getDeficit() >= input) {
                setDeficit(getDeficit() - input);
                return input;
            } else {
                double output = getDeficit();
                setDeficit(0);
                rfc.setReserveCap(rfc.getReserveCap() +
                        R_FILL_PER_DAY_IMM);
                setTodayRCUpdated(true);
                return output;
            }
        }
        
        public void update() {
            if (todayRCUpdated) {
                setDeficit(R_BUILD_PER_DAY_CONS);
                setTodayRCUpdated(false);
            }
        }
        
        @Override
        public String toString() {
            return "Daily consumtion reserve building " + R_BUILD_PER_DAY_CONS +
                    "\n" +
                    "Deficit " + getDeficit() + "\n" +
                    "Today RC updated " + isTodayRCUpdated() + "\n";
        }
    }
    
    public class ResFillContainer {
        private double deficit;
        
        private double reserveCap;
        private double reserveFill;
        
        public ResFillContainer() {
            setReserveFill(0);
            setReserveCap(0);
            update();
        }

        public double getDeficit() {
            return deficit;
        }

        public double getReserveCap() {
            return reserveCap;
        }

        public double getReserveFill() {
            return reserveFill;
        }

        public void setDeficit(double deficit) {
            this.deficit = deficit;
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
            this.reserveFill = reserveFill;
        }

        // Least of two numbers:
        //  filling up to reserve capacity
        //  deficit per day
        public double minDeficit() {
            return Math.min(getDeficit(), getReserveCap() -
                    getReserveFill());
        }
        
        public double fill(double input) {
            if (getDeficit() >= input) {
                setDeficit(getDeficit() - input);
                setReserveFill(getReserveFill() + input);
                return input;
            } else {
                double minDeficit = minDeficit();
                setDeficit(getDeficit() - minDeficit);
                setReserveFill(getReserveFill() + minDeficit);
                return minDeficit;
            }
        }
        
        public double extract(double needed) {
            if (needed >= getReserveFill()) {
                double output = getReserveFill();
                setReserveFill(0);
                return output;
            } else {
                setReserveFill(getReserveFill() - needed);
                return needed;
            }
        }
        
        public void update() {
            setDeficit(R_FILL_PER_DAY_IMM);
        }
        
        @Override
        public String toString() {
            return "Max consumption for filling daily " + R_FILL_PER_DAY_IMM +
                    "\n" +
                    "Deficit " + getDeficit() + "\n" +
                    "Reserve Capacity " + getReserveCap() + "\n" +
                    "Reserve Fill " + getReserveFill() + "\n";
        }
    }
    
    @Override
    public String toString() {
        return "Sheep id " + sheepId + "\n" +
                "Alive " + alive + "\n" +
                "Mature " + mature + "\n" +
                "Age " + age + "\n\n" +
                "LC " + lc + "\n" +
                (!mature ? "RBC " + rbc + "\n" : "") +
                "RFC " + rfc + "\n";
    }
}