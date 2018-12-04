package com.droie.sprinkle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class LiftTest {

    private Lift lift;

    @Before
    public void setUp() {
        lift = new Lift();
    }

    @Test
    public void fromSprinkle() {
        final int[][] queues = {
                new int[]{3}, // 1
                new int[0], // 2
                new int[]{1}, // 3
                new int[]{0}, // 4
        };
        final int[] result = lift.theLift(queues);
        assertArrayEquals(new int[]{0,3,2,1,0}, result);
    }

    @Test
    public void testUp() {
        final int[][] queues = {
                new int[0], // 1
                new int[0], // 2
                new int[]{5,5,5}, // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
                new int[0], // 7
        };
        final int[] result = lift.theLift(queues);
        assertArrayEquals(new int[]{0,2,5,0}, result);
    }

    @Test
    public void testDown() {
        final int[][] queues = {
                new int[0], // 1
                new int[0], // 2
                new int[]{1,1}, // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
                new int[0], // 7
        };
        final int[] result = lift.theLift(queues);
        assertArrayEquals(new int[]{0,2,1,0}, result);
    }

    @Test
    public void testUpAndUp() {
        final int[][] queues = {
                new int[0], // 1
                new int[]{3}, // 2
                new int[]{4}, // 3
                new int[0], // 4
                new int[]{5}, // 5
                new int[0], // 6
                new int[0], // 7
        };
        final int[] result = lift.theLift(queues);
        assertArrayEquals(new int[]{0,1,2,3,4,5,0}, result);
    }

    @Test
    public void testDownAndDown() {
        final int[][] queues = {
                new int[0], // 1
                new int[]{0}, // 2
                new int[0], // 3
                new int[0], // 4
                new int[]{2}, // 5
                new int[]{3}, // 6
                new int[0], // 7
        };
        final int[] result = lift.theLift(queues);
        assertArrayEquals(new int[]{0,5,4,3,2,1,0}, result);
    }
}
