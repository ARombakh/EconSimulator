/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.specialist;

import java.util.ArrayList;
import java.util.List;
import ru.specialist.entities.ChangeDay;
import ru.specialist.entities.GrassAcre;
import ru.specialist.entities.Sheep;

/**
 *
 * @author artyom
 */
public class EcoSystem implements ChangeDay {
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
        return sheep.size() == 0;
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
                System.out.printf(sheep1 + "\n");   // Debug
                System.out.println(grass1);   // Debug
                
                feedOneSheep(sheep1, grass1);
                
                System.out.printf("Feeding sheep:\n");   // Debug
                System.out.println(sheep1);   // Debug
                System.out.println(grass1);   // Debug
            }
        }
    }
    
    @Override
    public void dayPasses() {        
        for (int i = sheep.size() - 1; i >= 0; i--) {
            sheep.get(i).dayPasses();
            if (!sheep.get(i).isAlive()) {
                System.out.printf("Sheep %d died\n", i);   // Debug
                sheep.remove(i);
            }
        }
        
        for (GrassAcre grassAcre : grass) {
            grassAcre.dayPasses();
        }
        
        System.out.println("Day passed");
    }
    
    @Override
    public String toString() {
        String output = "";
        
        for (int i = 0; i < sheep.size(); i++) {            
            output += sheep.get(i) + "\n";
        }
        
        for (int i = 0; i < grass.size(); i++) {
            output += grass.get(i) + "\n";
        }        
        return output;
    }
}
