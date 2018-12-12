package com.droie.sprinkle;

import java.util.*;

public class Lift {
    private Map<Integer, LinkedList> queuesUp = new TreeMap<>();
    private Map<Integer, LinkedList> queuesDown = new TreeMap<>(Collections.reverseOrder());
    private List<Integer> peopleInLift = new LinkedList<>();

    public int[] theLift(final int[][] queues) {
        initMapsWithQueues(queues);

        while (!queuesUp.isEmpty() || !queuesDown.isEmpty()) {
            liftGoesUp();
            liftGoesDown();

        }
        return new int[0];
    }

    private void liftGoesUp() {
        Iterator<Map.Entry<Integer, LinkedList>> entryIt = queuesUp.entrySet().iterator();
        while (entryIt.hasNext()){
            Map.Entry<Integer, LinkedList> floor = entryIt.next();
            processFloor(floor);
            if (((LinkedList<Integer>) floor.getValue()).size() == 0){
                entryIt.remove();
            }
        }
    }

    private void liftGoesDown() {
        Iterator<Map.Entry<Integer, LinkedList>> entryIt = queuesDown.entrySet().iterator();
        while (entryIt.hasNext()){
            Map.Entry<Integer, LinkedList> floor = entryIt.next();
            processFloor(floor);
            if (((LinkedList<Integer>) floor.getValue()).size() == 0){
                entryIt.remove();
            }
        }
    }

    private void processFloor(Map.Entry floor) {
        System.out.printf("Двигаюсь на %d этаж%n", (int)floor.getKey() + 1);
        peopleInLift.remove(floor.getKey());
        LinkedList<Integer> floorQueue = (LinkedList<Integer>) floor.getValue();
        int floorQueueSize = floorQueue.size();
        for (int i = 0; i < floorQueueSize; i++) {
            System.out.printf("Подобрал человека на %d этаже%n", (int)floor.getKey() + 1);
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
