package com.droie.sprinkle;

import java.util.*;
import java.util.stream.IntStream;

public class InitialLift {
    private Map<Integer, LinkedList> queuesUp = new TreeMap<>();
    private Map<Integer, LinkedList> queuesDown = new TreeMap<>(Collections.reverseOrder());
    private List<Integer> peopleInLift = new LinkedList<>();
    private int numberOfFloors;
    private LinkedList<Integer> orderOfStops = new LinkedList<>(Arrays.asList(0));

    public int[] theLift(final int[][] queues) {
        initMapsWithQueues(queues);

        while (queuesUp.size() > 0 || queuesDown.size() > 0) {
            liftGoesUp();
            liftGoesDown();
        }
        orderOfStops.add(0);

        return filterConsecutiveDuplicates(orderOfStops);
    }

    private int[] filterConsecutiveDuplicates(LinkedList<Integer> listToFilter) {
        return IntStream.range(0, listToFilter.size())
                .filter(i -> ((i < listToFilter.size() - 1
                        && !listToFilter.get(i).equals(this.orderOfStops.get(i + 1))
                        || i == listToFilter.size() - 1)))
                .mapToObj(listToFilter::get)
                .mapToInt(Integer::intValue).toArray();
    }

    private void liftGoesUp() {
        for (int i = 0; i < numberOfFloors; i++) {
            if (queuesUp.containsKey(i) || peopleInLift.contains(i)){
                processFloor(i, queuesUp.get(i));
                queuesUp.remove(i);
                orderOfStops.add(i);
            }
        }
    }

    private void liftGoesDown() {
        for (int i = numberOfFloors - 1; i >= 0; i--) {
            if (queuesDown.containsKey(i) || peopleInLift.contains(i)){
                processFloor(i, queuesDown.get(i));
                queuesDown.remove(i);
                orderOfStops.add(i);
            }
        }
    }

    private void processFloor(Integer floor, LinkedList floorQueue) {
        System.out.printf("Двигаюсь на %d этаж%n", floor + 1);
        peopleInLift.remove(floor);
        if (floorQueue != null){
            System.out.printf("Подобрал человека на %d этаже%n", floor + 1);
            int floorQueueSize = floorQueue.size();
            for (int i = 0; i < floorQueueSize; i++) {
                System.out.printf("Принял команду перемещения на %d этаж%n", (Integer) floorQueue.peek() + 1);
                peopleInLift.add((Integer) floorQueue.poll());
            }
        }
    }

    private void initMapsWithQueues(int[][] queues) {
        numberOfFloors = queues.length;
        for (int i = 0; i < numberOfFloors; i++) {
            int[] queue = queues[i];
            if (queue.length == 0)
                continue;
            LinkedList<Integer> floorQueueUp = new LinkedList<>();
            LinkedList<Integer> floorQueueDown = new LinkedList<>();
            for (int floorToGo : queue) {
                if (floorToGo > i) {
                    floorQueueUp.add(floorToGo);
                } else {
                    floorQueueDown.add(floorToGo);
                }
            }
            if (!floorQueueUp.isEmpty()){
                queuesUp.put(i, floorQueueUp);
            }
            if (!floorQueueDown.isEmpty()){
                queuesDown.put(i, floorQueueDown);
            }
        }
    }
}
