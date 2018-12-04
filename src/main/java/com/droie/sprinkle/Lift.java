package com.droie.sprinkle;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Lift {
    private TreeSet<Integer> orderUp = new TreeSet<>(Arrays.asList(0));
    private TreeSet<Integer> orderDown = new TreeSet<>();

    public int[] theLift(final int[][] queues) {
        for (int i = 0; i < queues.length; i++) {
            int[] queue = queues[i];
            for (int floorToGo : queue) {
                if (floorToGo > i) {
                    addToOrderList(orderUp, i, floorToGo);
                }
            }
        }

        for (int i = queues.length - 1; i > 0; i--) {
            int[] queue = queues[i];
            for (int floorToGo : queue) {
                if (floorToGo < i) {
                    addToOrderList(orderDown, i, floorToGo);
                }
            }

        }
        orderDown.add(0);
        System.out.println("Двигаюсь на первый этаж");
        return Stream.concat(orderUp.stream(), orderDown.descendingSet().stream()).mapToInt(Integer::intValue).toArray();
    }

    private void addToOrderList (TreeSet<Integer> order, int floor, int floorToGo) {
        if (order.isEmpty() || order.last() != floorToGo) {
            order.add(floor);
            order.add(floorToGo);
            System.out.printf("Двигаюсь на %d этаж%n", floor + 1);
            System.out.printf("Подобрал человека на %d этаже%n", floor + 1);
            System.out.printf("Принял команду перемещения на %d этаж%n", floorToGo + 1);
        }
    }
}
