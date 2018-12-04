package com.droie.sprinkle;

public class Lift {
    public static int[] theLift(final int[][] queues) {
        for (int i = 0; i < queues.length; i++) {
            int[] queue = queues[i];
            for (int levelToGo : queue) {
                if (levelToGo > i) {
                    System.out.printf("Подобрал человека на %d этаже%n", i + 1);
                    System.out.printf("Принял команду перемещения на %d этаж%n", levelToGo + 1);
                }
            }
            System.out.printf("Двигаюсь на %d этаж%n", i + 2);
        }

        for (int i = queues.length - 1; i > 0; i--) {
            int[] queue = queues[i];
            for (int levelToGo : queue) {
                if (levelToGo < i) {
                    System.out.printf("Подобрал человека на %d этаже%n", i + 1);
                    System.out.printf("Принял команду перемещения на %d этаж%n", levelToGo + 1);
                }
            }
            System.out.printf("Двигаюсь на %d этаж%n", i);
        }
        return new int[0];
    }
}
