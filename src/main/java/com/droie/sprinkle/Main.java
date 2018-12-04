package com.droie.sprinkle;

public class Main {

    public static void main(String[] args) {

        int[][] queues1 = {
                new int[]{3}, // 1
                new int[0], // 2
                new int[]{1}, // 3
                new int[]{0}, // 4
        };

        int[][] queues2 = {
                new int[0], // 1
                new int[0], // 2
                new int[]{5,5,5}, // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
                new int[0], // 7
        };
        Lift lift = new Lift();
        lift.theLift(queues2);
    }
}
