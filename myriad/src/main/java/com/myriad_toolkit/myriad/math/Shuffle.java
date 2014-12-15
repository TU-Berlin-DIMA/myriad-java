/*
 * Copyright 2010-2014 DIMA Research Group, TU Berlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author: Marie Hoffmann <marie.hoffmann@tu-berlin.de>
 */

package com.myriad_toolkit.myriad.math;

/**
 *  This class implements a splittable shuffle with O(1) space and O(n) computational costs inspired by Rendezvous hashing.
 */
public class Shuffle {

    private HashRandomStream hash;
    private long range; // (excluded) upper bound of to be shuffled index

    // initialize PRNG with default seed 0
    public Shuffle (long range){
        this.hash = new HashRandomStream(0);
        this.range = range;
    }

    public Shuffle(long range, long seed){
        this.hash = new HashRandomStream(seed);
        this.range = range;
    }

    // Compute new position in ordered set of all hashed seq vals
    public long at(long i){
        long j = 0;
        long i_hashed = this.hash.at(i);
        for (long k = 0; k < range; ++k){
            if (this.hash.at(k) < i_hashed)
                ++j;
        }
        return j;
    }

}
