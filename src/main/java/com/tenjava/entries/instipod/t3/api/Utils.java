package com.tenjava.entries.instipod.t3.api;

import java.util.Random;

public class Utils {
    public static int getRandom(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
    public static int getRandom(int min, int max) {
        return getRandom(max) + min;
    }
}