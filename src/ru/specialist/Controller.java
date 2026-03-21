/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.specialist;

import ru.specialist.entities.ChangeDay;

/**
 *
 * @author artyom
 */
public class Controller {
    private EcoSystem ecoSystem;

    public Controller(EcoSystem ecoSystem) {
        this.ecoSystem = ecoSystem;
    }

    public EcoSystem getEcoSystem() {
        return ecoSystem;
    }

    public void setEcoSystem(EcoSystem ecoSystem) {
        this.ecoSystem = ecoSystem;
    }
    
    public void dailyInteraction() {
        getEcoSystem().feedAllSheep();
        getEcoSystem().dayPasses();        
    }
    
    public void run() {
        while (!getEcoSystem().isDead()) {            
            dailyInteraction();
        }
    }
}
