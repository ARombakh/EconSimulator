/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.specialist;

import java.util.ArrayList;
import java.util.List;
import ru.specialist.entities.GrassAcre;
import ru.specialist.entities.Sheep;

/**
 *
 * @author artyom
 */
public class EcoSystem {
    List<Sheep> sheep = new ArrayList<Sheep>();
    List<GrassAcre> grass = new ArrayList<GrassAcre>();
    
    public EcoSystem(int sheep, int grassAcre) {
        for (int i = 0; i < sheep; i++) {
            this.sheep.add(new Sheep());
        }
        
        for (int i = 0; i < grassAcre; i++) {
            this.grass.add(new GrassAcre(3));
        }
    }
    
    // function to test if all the sheep are dead
    public boolean isDead() {
        for (Sheep sheep1 : sheep) {
            if (sheep1.isAlive() == true) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean feedSheep(Sheep sheep, GrassAcre grassAcre) {
        double eaten = sheep.eat(grassAcre.availRes());
        if (eaten == 0) {
            return false;
        } else {
            grassAcre.resourceEaten(eaten);
            return true;
        }
    }
}
