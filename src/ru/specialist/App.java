/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.specialist;

import ru.specialist.entities.Sheep;

/**
 *
 * @author artyom
 */
public class App {
    public static void main(String[] args) throws Exception {
        /*
        EcoSystem ecoSystem = new EcoSystem(4, 1);

        System.out.println("Sheep quantity " + ecoSystem.sheep.size());
        
        Controller controller = new Controller();
        controller.setEcoSystem(ecoSystem);
        
        controller.run();
        
        System.out.println("Sheep quantity " + ecoSystem.sheep.size());
        */
        Sheep sheep = new Sheep(false);
        
        EcoSystem ecoSystem = new EcoSystem(0, 5);
        
        ecoSystem.sheep.add(sheep);
        
        Controller controller = new Controller(ecoSystem);
        
        sheep = controller.getEcoSystem().sheep.getFirst();
        
        while (sheep.getAge() < 400) {
            System.out.printf("%s\n", controller.getEcoSystem());   // Debug
            controller.dailyInteraction();
        }
    }
}