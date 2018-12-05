package com.droie.sprinkle;

import java.util.*;

public class Lift {
    private Map<Integer, LinkedList> queuesUp = new TreeMap<>();
    private Map<Integer, LinkedList> queuesDown = new TreeMap<>(Collections.reverseOrder());
    private List<Integer> peopleInLift = new LinkedList<>();

    public int[] theLift(final int[][] queues) {
        initMapsWithQueues(queues);

        while (!queuesUp.isEmpty() && !queuesDown.isEmpty()) {
            liftGoesUp();
            liftGoesDown();

        }
        return new int[0];
    }

    private void liftGoesUp() {
        for (Map.Entry entry : queuesUp.entrySet()){
            processFloor(entry);
            if (((LinkedList<Integer>) entry.getValue()).size() == 0){
                queuesUp.remove(entry.getKey());
            }
        }
    }

    private void liftGoesDown() {
        for (Map.Entry entry : queuesDown.entrySet()){
            processFloor(entry);
            if (((LinkedList<Integer>) entry.getValue()).size() == 0){
                queuesDown.remove(entry.getKey());
            }
        }
    }

    private void processFloor(Map.Entry entry) {
        System.out.printf("Двигаюсь на %d этаж%n", (int)entry.getKey() + 1);
        peopleInLift.remove(entry.getKey());
        LinkedList<Integer> floorQueue = (LinkedList<Integer>) entry.getValue();
        int floorQueueSize = floorQueue.size();
        for (int i = 0; i < floorQueueSize; i++) {
            System.out.printf("Подобрал человека на %d этаже%n", (int)entry.getKey() + 1);
            System.out.printf("Принял команду перемещения на %d этаж%n", floorQueue.peek() + 1);
            peopleInLift.add(floorQueue.poll());
        }
    }

    private void initMapsWithQueues(int[][] queues) {
        for (int i = 0; i < queues.length; i++) {
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
