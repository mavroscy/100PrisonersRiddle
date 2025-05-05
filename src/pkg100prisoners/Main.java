/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg100prisoners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author https://github.com/mavroscy
 *
 * @version 1.0.0
 *
 */
public class Main {

    /*
    * Title:        100 Prisoners Riddle implemented by mavroscy
    * Description:  Java implementeation of the 100 Prisoners riddle as @veritasium 
    *               explains it in his video https://www.youtube.com/watch?v=iSNsgj1OCLA 
     */
    //Turn on/off debug messages (turn this off for a faster run - skip debug message and show only the final success rate)
    static boolean debug = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Random number implementation type 
        //1=using class Random
        //2=using Collections.shuffle
        int randomNumberImplementationType = 2;
        //Number of prisoners in the pool 
        int numberOfPrisoners = 50;
        //number of total boxes in the pool
        int numberOfTotalBoxes = numberOfPrisoners * 2;
        //number of boxes the prisoners are allowed to open 
        int numberOfTotalBoxesAllowedToOpenPerPrisoner = numberOfTotalBoxes / 2;
        //number of different runs of the game (to get a better aproximation try increasing the numberOfGames)
        int numberOfGames = 10000;
        //counter that holds success for all games run so far
        int totalGameSucceses = 0;
        //counter that holds failures for all games run so far
        int totalGameFailures = 0;

        //Initial loop to go through the number of games
        for (int gamesCounter = 0; gamesCounter < numberOfGames; gamesCounter++) {
            if (debug) {
                System.out.println("Game:" + gamesCounter);
            }

            //get new random configuration for each run
            ArrayList<Integer> boxConfigutarion = getNewConfiguration(numberOfTotalBoxes, randomNumberImplementationType);
            //flag to evaluate success for each run inside the following loop
            int success = 0;
            //Loop that will go through each prisoners and simulate box selection
            for (int prisoner = 0; prisoner < numberOfPrisoners; prisoner++) {
                if (debug) {
                    System.out.println("Game:" + gamesCounter + " Prisoner:" + prisoner);
                }
                //this is the next bos that the prisoner will go for. The initial value is the prisoner number
                int gotoBox = prisoner;
                //counter the number of boxes that a prisoner has oppened so far
                int counterOfBoxesOpenedSoFar = 0;
                //flag that keeps track if the prisoner number matches the slip number
                boolean found = false;
                //Loop that will similate the prisoner's box selection
                //if the box is not found yet, and the number of boxes opened is less that what the prisoner is allowed to open then go to the next box...
                while (!found && counterOfBoxesOpenedSoFar < numberOfTotalBoxesAllowedToOpenPerPrisoner) {

                    if (debug) {
                        System.out.println("Game:" + gamesCounter + " Prisoner:" + prisoner + " BoxesOpenedSoFar:" + counterOfBoxesOpenedSoFar);
                    }

                    if (boxConfigutarion.get(gotoBox) == prisoner) {
                        //prisoner found the slip that contains his number
                        found = true;
                        //increment of the counter is not needed
                        counterOfBoxesOpenedSoFar++;
                    } else {
                        //increment the counter of boxes opened so far
                        counterOfBoxesOpenedSoFar++;
                        //set the next bos a prisoner should open
                        gotoBox = boxConfigutarion.get(gotoBox);
                        //reset the found to false
                        found = false;
                    }
                }
                //check if the prisoner found his slip and increment success count if true
                if (found) {
                    success++;
                    if (debug) {
                        System.out.println("Game:" + gamesCounter + " Prisoner:" + prisoner + " Found " + " Success:" + success);
                    }
                }
            }
            //check if all prisoners found their slip number
            if (numberOfTotalBoxesAllowedToOpenPerPrisoner == success) {
                totalGameSucceses++;
            } else {
                totalGameFailures++;
            }
        }
        
        double percentSuccess = totalGameSucceses * 1.0 / numberOfGames;
        
        System.out.println("Success Rate:" + percentSuccess);

    }

    /*
    *   This is a helper method that will return a new Random configuration of
    *   a List of Integers to be used as prisoner slips.
    *   The index of the array will be used as prisoner number
     */
    public static ArrayList<Integer> getNewConfiguration(int numbersOfBoxes, int randomNumberImplementationType) {
        List<Integer> list = new ArrayList<>();
        if (randomNumberImplementationType == 1) {
            //implementation 1
            Random I = new Random();
            int number;
            for (int counter = 1; counter <= numbersOfBoxes; counter++) {
                number = I.nextInt(numbersOfBoxes);
                while (list.contains(number)) {
                    number = I.nextInt(numbersOfBoxes);
                }
                list.add(number);
            }
        } else if (randomNumberImplementationType == 2) {
            //implementation 2
            for (int counter = 0; counter < numbersOfBoxes; counter++) {
                list.add(counter);
            }
            Collections.shuffle(list);
        }
        if (debug) {
            System.out.println("New Configuration:" + list);
        }
        return (ArrayList<Integer>) list;
    }
}
