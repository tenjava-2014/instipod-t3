package com.tenjava.entries.instipod.t3.api;

import java.util.Random;

public class Utils {
    /**
     * Returns a random number between 0 and max
     * @param max the maximum value the random number can be
     * @return a random number
     */
    public static int getRandom(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
    
    /**
     * Returns a random number between min and max
     * @param min the minimum value the random number can be
     * @param max the maximum value the random number can be
     * @return a random number
     */
    public static int getRandom(int min, int max) {
        return getRandom(max) + min;
    }
}