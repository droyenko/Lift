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
                    System.out.printf("Двигаюсь на %d этаж%n", i + 1);
                    orderUp.add(i);
                    System.out.printf("Подобрал человека на %d этаже%n", i + 1);
                    orderUp.add(levelToGo);
                    System.out.printf("Принял команду перемещения на %d этаж%n", levelToGo + 1);
                }
            }
        }

        for (int i = queues.length - 1; i > 0; i--) {
            int[] queue = queues[i];
            for (int levelToGo : queue) {
                if (levelToGo < i) {
                    System.out.printf("Двигаюсь на %d этаж%n", i + 1);
                    orderDown.add(i);
                    System.out.printf("Подобрал человека на %d этаже%n", i + 1);
                    orderDown.add(levelToGo);
                    System.out.printf("Принял команду перемещения на %d этаж%n", levelToGo + 1);
                }
            }

        }
        orderDown.add(0);
        System.out.println("Двигаюсь на первый этаж");
        return Stream.concat(orderUp.stream(), orderDown.descendingSet().stream()).mapToInt(Integer::intValue).toArray();
    }
}
