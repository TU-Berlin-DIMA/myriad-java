package com.myriad_toolkit.myriad.textgen;

import java.util.Random;

/**
 * 
 * @author Umar Maqsud (umar.maqsud@campus.tu-berlin.de)
 * 
 */
public class SeedRandomGenerator implements RandomGenerator {

    private Random random;

    public SeedRandomGenerator(double seed) {
        random = new Random(("" + seed).hashCode());
    }

    public SeedRandomGenerator(long seed) {
        random = new Random(seed);
    }

    public Double nextDouble() {
        return random.nextDouble();
    }

    public Integer nextInt(int n) {
        return random.nextInt(n);
    }

    public Integer nextInt() {
        return random.nextInt();
    }

}