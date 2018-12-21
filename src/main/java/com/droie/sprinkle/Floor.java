package com.droie.sprinkle;

import java.util.Deque;
import java.util.stream.Stream;

public class Floor {
    private int floorLevel;
    private Deque<Passenger> personQueue;


    public Floor(int floorLevel, Deque<Passenger> personQueue) {
        this.floorLevel = floorLevel;
        this.personQueue = personQueue;
    }

    public Stream<Passenger> personsAtDesinationFloor() {
        return personQueue.stream().filter(passenger -> floorLevel == passenger.getDestinationFloor());
    }

    public Stream<Passenger> personsGoingToOtherFloor() {
        return personQueue.stream().filter(passenger -> floorLevel != passenger.getDestinationFloor());
    }

    public Stream<Passenger> personsGoingUpwards() {
        return personQueue.stream().filter(passenger -> floorLevel < passenger.getDestinationFloor());
    }

    public Stream<Passenger> personsGoingDownwards() {
        return personQueue.stream().filter(passenger -> floorLevel > passenger.getDestinationFloor());
    }

    public int getFloorLevel() {
        return floorLevel;
    }

    public Deque<Passenger> getPersonQueue() {
        return personQueue;
    }

    @Override
    public String toString() {
        return String.valueOf(floorLevel);
    }

}