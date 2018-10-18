/*
 * Copyright (c) 2017. Phasmid Software
 */

//package edu.neu.coe.info6205.randomwalk;

import java.util.Random;

public class RandomWalk {
    private int x = 0;
    private int y = 0;
    int nextStep = 0;

    private final Random random = new Random();

    private void move(int dx, int dy) {
        // TODO you need to implement this
        x += dx;
        y += dy;
    }

    /**
     * Perform a random walk of m steps
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        for (int i = 0; i < m; i++)
            randomMove();//Start walking, m steps, so we get move(0), move(1), move(2) .... move(m-1)
    }

    private void randomMove() {
        // TODO you need to implement this
        nextStep = random.nextInt(4)+1;
        if (nextStep <= 1) move(1,0);
        else if (nextStep <= 2) move(-1,0);
        else if (nextStep <= 3) move(0,1);
        else if (nextStep <= 4) move(0,-1);
    }

    public double distance() {
        double distance = Math.sqrt((x * x) + (y * y));
        return distance;
    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++){

            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);//move m times
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance/n ;
    }

    public static void main(String[] args) {
        if (args.length==0)
            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
        int m = Integer.parseInt(args[0]);
        int n = 100000;
        if (args.length > 1) n = Integer.parseInt(args[1]);
        double meanDistance = randomWalkMulti(m, n);
        System.out.println(m + " steps: " + meanDistance + " over "+ n + " experiments");
    }

}
