package com.droie.sprinkle;

public class Main {

    public static void main(String[] args) {

        int[][] queues = {
                new int[]{3}, // 1
                new int[0], // 2
                new int[]{1}, // 3
                new int[]{0}, // 4
        };

        Lift lift = new Lift();
        lift.theLift(queues);
    }
}
