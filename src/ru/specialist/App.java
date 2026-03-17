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
    public static void main(String[] args) {
        EcoSystem ecoSystem = new EcoSystem(4, 3);
        /*
        System.out.println(system.sheep.get(0));
        System.out.println(system.grass.get(0));
        
        if (system.feedOneSheep(system.sheep.get(0), system.grass.get(0))) {
            System.out.println("Eating was successful\n");
        } else {
            System.out.println("Eating was UNsuccessful\n");
        }

        System.out.println(system.sheep.get(0));
        System.out.println(system.grass.get(0));
        
        if (system.feedOneSheep(system.sheep.get(1), system.grass.get(0))) {
            System.out.println("Eating was successful\n");
        } else {
            System.out.println("Eating was UNsuccessful\n");
        }
        
        System.out.println(system.sheep.get(1));
        System.out.println(system.grass.get(0));*/

        System.out.println("Sheep quantity " + ecoSystem.sheep.size());
        
        while (!ecoSystem.sheep.getFirst().isMature() ||
                (ecoSystem.sheep.getFirst().getRfc().getReserveFill() <
                ecoSystem.sheep.getFirst().getRfc().getReserveCap())) {
//            System.out.println(ecoSystem);

            ecoSystem.feedAllSheep();

            System.out.println("All sheep are fed\n");

//            System.out.println(ecoSystem);

            ecoSystem.dayPasses();

//            System.out.println(ecoSystem);

            if (ecoSystem.isDead()) {
                break;
            }
        }
        
        System.out.println("Sheep quantity " + ecoSystem.sheep.size());
    }
}