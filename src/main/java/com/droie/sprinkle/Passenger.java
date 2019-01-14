package com.droie.sprinkle;

public class Passenger {
    private final int destinationFloor;
    private boolean isVip;

    public Passenger(int destinationFloor) {
        this.destinationFloor = Math.abs(destinationFloor);
        //vip passengers will mark their destination floor as negative
        this.isVip = destinationFloor < 0;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public boolean isVip() {
        return isVip;
    }
}
