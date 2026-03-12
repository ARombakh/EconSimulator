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
    
    public boolean feedOneSheep(Sheep sheep, GrassAcre grassAcre) {
        double eaten = sheep.eat(grassAcre.availRes());
        if (eaten == 0) {
            return false;
        } else {
            grassAcre.resourceEaten(eaten);
            return true;
        }
    }
    
    public void feedAllSheep() {
        for (Sheep sheep1 : sheep) {
            for (GrassAcre grass1 : grass) {
                feedOneSheep(sheep1, grass1);
                
                System.out.printf("Feeding sheep:\n");   // Debug
                System.out.println(sheep1);   // Debug
                System.out.println(grass1);   // Debug
            }
        }
    }
    
    @Override
    public String toString() {
        String output = "";
        
        for (int i = 0; i < sheep.size(); i++) {
            output += "Sheep no " + i + "\n";
            
            output += sheep.get(i) + "\n";
        }
        
        for (int i = 0; i < grass.size(); i++) {
            output += "Grass no " + i + "\n";
            
            output += grass.get(i) + "\n";
        }        
        return output;
    }
}
