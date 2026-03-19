/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.specialist;

/**
 *
 * @author artyom
 */
public class Controller {
    private EcoSystem ecoSystem;

    public EcoSystem getEcoSystem() {
        return ecoSystem;
    }

    public void setEcoSystem(EcoSystem ecoSystem) {
        this.ecoSystem = ecoSystem;
    }
    
    public void run() {
        while (!getEcoSystem().isDead()) {            
            getEcoSystem().feedAllSheep();
            getEcoSystem().dayPasses();
        }
    }
}
