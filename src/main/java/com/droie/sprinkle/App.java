package com.droie.sprinkle;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class App {
    private static Floor[] buildingFloors;
    private Lift lift;

    public App(Floor[] buildingFloors, int passengerCapacity) {
        this.buildingFloors = buildingFloors;
        this.lift = new Lift(buildingFloors[0], passengerCapacity);
    }

    public int[] theLift(final int[][] queues, final int personCapacity) {
        if (queues == null || queues.length == 0) {
            return new int[0];
        }
        Floor[] buildingFloors = createFloorsWithPersonQueues(queues);
        App app = new App(buildingFloors, personCapacity);
        app.lift.operateLift();
        return app.lift.getStoppedFloors().stream().mapToInt(Floor::getFloorLevel).toArray();
    }

    private Floor[] createFloorsWithPersonQueues(int[][] queues) {
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
