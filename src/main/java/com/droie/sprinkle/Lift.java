package com.droie.sprinkle;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lift {
    private Floor currentFloor;
    private Floor[] buildingFloors;
    private boolean isGoingUpwards = true;
    private List<Floor> stoppedFloors;
    private int personCapacity;
    private List<Passenger> loadedPersons;

    public Lift(Floor[] buildingFloors, int personCapacity) {
        this.buildingFloors = buildingFloors;
        this.currentFloor = buildingFloors[0];
        this.personCapacity = personCapacity;
        this.loadedPersons = new LinkedList<>();
        this.stoppedFloors = new LinkedList<>();
        this.stoppedFloors.add(buildingFloors[0]);
    }

    /**
     * Operates this lift, which will transport all {@link Passenger}s to their destination {@link Floor}s
     * and return in the end back to the first {@link Floor}.
     */
    public void operateLift() {
        boolean hasMoved = false;
        while (true) {
            unloadPersons();
            loadPersons();

            Optional<Floor> nextFloor = firstFloorInTravelDirection();
            Optional<Passenger> vipPassenger = getVipPassenger();

            if (!nextFloor.isPresent()) {
                nextFloor = lastFloorInDirectionWherePersonWantToEnter();
                reverseLiftDirection();
            }
            if (vipPassenger.isPresent()) {
                moveLift(buildingFloors[vipPassenger.get().getDestinationFloor()]);
                hasMoved = true;
            } else if (nextFloor.isPresent()) {
                moveLift(nextFloor.get());
                hasMoved = true;
            } else if (!hasMoved) {
                // repeat till this lift hasn't moved any more (no more persons want to ride)
                break;
            } else {
                hasMoved = false;
            }
        }

        moveLift(buildingFloors[0]);
        System.out.println("finish with stoppedFloors: " + stoppedFloors);
    }

    /**
     * Unloads all {@link Passenger}s from the {@link Lift#loadedPersons},
     * which have as destination the {@link #currentFloor}.
     */
    private void unloadPersons() {
        for (int i = 0; i < loadedPersons.size(); i++) {
            Passenger passenger = loadedPersons.get(i);
            if (passenger.getDestinationFloor() == currentFloor.getFloorLevel()) {
                loadedPersons.remove(i--);
                currentFloor.getPersonQueue().addLast(passenger);
                System.out.printf("Unload person to Floor %d%n", currentFloor.getFloorLevel() + 1);
            }
        }
    }

    /**
     * Loads all {@link Passenger}s from {@link #currentFloor}, which want to ride in lifts direction.
     * Following {@link Floor#personQueue} in order and given {@link #personCapacity}.
     */
    private void loadPersons() {
        List<Passenger> personsEntering;
        if (isGoingUpwards) {
            personsEntering = currentFloor.personsGoingUpwards().limit(getFreePersonCapacity()).collect(Collectors.toList());
        } else {
            personsEntering = currentFloor.personsGoingDownwards().limit(getFreePersonCapacity()).collect(Collectors.toList());
        }
        personsEntering.forEach(person -> {
            currentFloor.getPersonQueue().remove(person);
            loadedPersons.add(person);
            System.out.printf("Load person from floor %d%n", currentFloor.getFloorLevel() + 1);
        });
    }

    /**
     * @return {@link Optional} first (nearest) {@link Floor} in lift travel direction,
     * where some {@link Passenger} want to enter OR exit.
     */
    private Optional<Floor> firstFloorInTravelDirection() {
        Optional<Floor> firstFloorPersonWantToEnter = firstFloorInDirectionWherePersonWantToEnter();
        Optional<Floor> firstFloorPersonWantToExit = firstFloorInDirectionWherePersonWantToExit();
        return Stream.of(firstFloorPersonWantToEnter, firstFloorPersonWantToExit)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparingInt(this::getFloorDistance));
    }

    /**
     * @return {@link Optional} first (nearest) {@link Floor} in lift travel direction,
     * where some {@link Passenger} wants to enter.
     */
    private Optional<Floor> firstFloorInDirectionWherePersonWantToEnter() {
        return floorsInTravelDirection().filter(this::hasPersonWantToEnterInDirection).findFirst();
    }

    private boolean hasPersonWantToEnterInDirection(Floor floor) {
        if (isGoingUpwards) {
            return floor.personsGoingUpwards().findAny().isPresent();
        } else {
            return floor.personsGoingDownwards().findAny().isPresent();
        }
    }

    /**
     * @return {@link Optional} first (nearest) {@link Floor}, where some {@link #loadedPersons} wants
     * to exit in lift travel direction
     */
    private Optional<Floor> firstFloorInDirectionWherePersonWantToExit() {
        return floorsInTravelDirection().filter(this::hasLoadedPersonWithDestination).findFirst();
    }

    private boolean hasLoadedPersonWithDestination(Floor floor) {
        return loadedPersons.stream()
                .map(Passenger::getDestinationFloor)
                .anyMatch(personDesinationFloorLevel -> floor.getFloorLevel() == personDesinationFloorLevel);
    }

    /**
     * @return {@link Optional} last (fares) {@link Floor} in lift travel direction,
     * where some {@link Passenger} want to enter in any direction.
     */
    private Optional<Floor> lastFloorInDirectionWherePersonWantToEnter() {
        return floorsInTravelDirection()
                .filter(floor -> floor.personsGoingToOtherFloor().findAny().isPresent())
                .max(Comparator.comparingInt(this::getFloorDistance));
    }

    /**
     * Moves the {@link Lift} to given destinationFloor and add it to {@link #stoppedFloors}
     *
     * @param destinationFloor next Floor, where this lift moves to and stops
     */
    private void moveLift(Floor destinationFloor) {
        if (!currentFloor.equals(destinationFloor)) {
            System.out.printf("Move lift from Floor %d to Floor %d%n",
                    currentFloor.getFloorLevel() + 1, destinationFloor.getFloorLevel() + 1);
            currentFloor = destinationFloor;
            stoppedFloors.add(destinationFloor);
        }
    }

    private void reverseLiftDirection() {
        isGoingUpwards = !isGoingUpwards;
    }


    private int getFreePersonCapacity() {
        return personCapacity - loadedPersons.size();
    }

    /**
     * @return A {@link Stream} containing upcoming {@link Floor}s in order for lift travel direction.
     */
    private Stream<Floor> floorsInTravelDirection() {
        if (isGoingUpwards) {
            return IntStream.range(currentFloor.getFloorLevel() + 1, buildingFloors.length)
                    .mapToObj(floorLevel -> buildingFloors[floorLevel]);
        } else {
            return IntStream.rangeClosed(1, currentFloor.getFloorLevel())
                    .mapToObj(floorLevel -> buildingFloors[currentFloor.getFloorLevel() - floorLevel]);
        }
    }

    /**
     * @return {@link Optional} VIP {@link Passenger} from the {@link Lift#loadedPersons}
     */
    private Optional<Passenger> getVipPassenger() {
        return loadedPersons.stream().filter(Passenger::isVip).findFirst();
    }

    private int getFloorDistance(Floor floor) {
        return Math.abs(currentFloor.getFloorLevel() - floor.getFloorLevel());
    }

    public List<Floor> getStoppedFloors() {
        return stoppedFloors;
    }
}
