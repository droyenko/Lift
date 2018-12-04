package com.droie.sprinkle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Lift {
    public static int[] theLift(final int[][] queues) {
        Set<Integer> orderUp = new HashSet<>(Arrays.asList(0));
        TreeSet<Integer> orderDown = new TreeSet<>();
        for (int i = 0; i < queues.length; i++) {
            int[] queue = queues[i];
            for (int levelToGo : queue) {
                if (levelToGo > i) {
                    orderUp.add(i);
                    orderUp.add(queue[levelToGo]);
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
                    orderDown.add(i);
                    orderDown.add(queue[levelToGo]);
                    System.out.printf("Подобрал человека на %d этаже%n", i + 1);
                    System.out.printf("Принял команду перемещения на %d этаж%n", levelToGo + 1);
                }
            }

            System.out.printf("Двигаюсь на %d этаж%n", i);
        }
        orderDown.add(0);
        return Stream.concat(orderUp.stream(), orderDown.descendingSet().stream()).mapToInt(Integer::intValue).toArray();
    }
}
