package com.droie.sprinkle;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class App {
    private Lift lift;

    public App(int[][] queues, int passengerCapacity) {
        Floor[] buildingFloors = createFloorsWithPersonQueues(queues);
        this.lift = new Lift(buildingFloors, passengerCapacity);
    }

    public static int[] theLift(final int[][] queues, final int personCapacity) {
        if (queues == null || queues.length == 0) {
            return new int[0];
        }
        App app = new App(queues, personCapacity);
        app.lift.operateLift();
        return app.lift.getStoppedFloors().stream().mapToInt(Floor::getFloorLevel).toArray();
    }

    private static Floor[] createFloorsWithPersonQueues(int[][] queues) {
        Floor[] buildingFloors = new Floor[queues.length];
        for (int floorLevel = 0; floorLevel < queues.length; floorLevel++) {
            Deque<Passenger> floorPersonQueue = Arrays.stream(queues[floorLevel])
                    .mapToObj(Passenger::new)
                    .collect(Collectors.toCollection(LinkedList::new));
            buildingFloors[floorLevel] = new Floor(floorLevel, floorPersonQueue);
        }
        return buildingFloors;
    }
}
