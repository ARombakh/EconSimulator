/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.specialist;

/**
 *
 * @author artyom
 */
public class App {
    public static void main(String[] args) {
        EcoSystem system = new EcoSystem(1, 1);
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
        
        while (!system.sheep.getFirst().isMature()) {            
            System.out.println(system);

            system.feedAllSheep();

            System.out.println("All sheep are fed\n");

            System.out.println(system);

            system.passDay();

            System.out.println(system);
        }
    }
}