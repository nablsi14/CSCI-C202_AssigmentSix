package assignmentsix;

import java.io.*;
import java.util.*;

/**
 * AssignmentSix.java
 * Purpose: Solution to Programming Assignment Six
 * @author Nathaniel Sigafoos
 * @version 1.0 4/26/17
 */
public class AssignmentSix {

    public final int CITIES;
    public int[][] adjacency;

    /**
     * Constructor for AssignmentSix class
     * @param cities The number of cities 
     */
    public AssignmentSix (int cities) {
        CITIES = cities;
        adjacency = new int[CITIES][CITIES];
        populateMatrix();
    }
    /**
     * Fills the adjacency matrix with the values from the file
     */
    private void populateMatrix() {
        File f = new File("tsp" + CITIES + ".txt");
        try (Scanner input = new Scanner(f)) {
            int value, i, j;
            for (i = 0; i < CITIES && input.hasNext(); i++) {
                for (j = i; j < CITIES && input.hasNext(); j++) {
                    if (i == j) {
                        adjacency[i][j] = 0;
                    } else {
                        value = input.nextInt();
                        adjacency[i][j] = value;
                        adjacency[j][i] = value;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid number of cities: " + CITIES + ". File "
                + f.getName() + " does not exist.");
        }
    }

    /**
     * Finds a Minimum Spanning Tree
     * Prints the results to the console.
     */
    public void mst () {
        long startTime = System.nanoTime();

        Stack<Integer> pathStack = new Stack<>();
        boolean[] visitedCities = new boolean[CITIES];//values default to false
        
        visitedCities[0] = true;
        pathStack.push(0);

        int closestCity = 0;
        boolean minFlag = false;
        System.out.print("Path: " + 0);
        int cost = 0;
        while (!pathStack.empty()) {
            int currentCity = pathStack.peek();
            int min = Integer.MAX_VALUE;
            for (int i = 1; i < CITIES; i++) {
                int distance = adjacency[currentCity][i];
                if (distance != 0 && !visitedCities[i]) {
                    if (distance < min) {
                        min = distance;
                        closestCity = i;
                        minFlag = true;
                    }
                }
            }
            if (minFlag) {
                cost += min;
                visitedCities[closestCity] = true;
                pathStack.push(closestCity);
                minFlag = false;
                System.out.print(" " + closestCity);
                continue;
            }
            pathStack.pop();
        }
        long endTime = System.nanoTime();
        System.out.println();
        System.out.println("Cost: " + cost);
        System.out.println("Execution time (ns.): " + (endTime - startTime));
    }

    /**
     * Runs the the Minimum Spanning Tree algorithm 
     *  with a given input from the command line.
     * @param args The number of cities to run the MST algorithm with. 
     *  Only args[0] is used.
     */
    public static void main(String[] args) {
        AssignmentSix aSix = new AssignmentSix(Integer.parseInt(args[0]));
        aSix.mst();
    }

}