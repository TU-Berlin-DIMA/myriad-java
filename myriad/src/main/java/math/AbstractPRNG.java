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

package math;

import core.MyriadNode;

public abstract class AbstractPRNG {

    /*
     * Node information object.
     */
    public MyriadNode m;

    /*
     * Very large or maximum value (period) that can be generated.
     */
    public long OFFSET_SUBSTREAM;

    /*
     * Position in chunk of current node.
     */
    public long pos;

    public AbstractPRNG(MyriadNode m) {
        this.m = m;
        this.OFFSET_SUBSTREAM = 0;
        init();
    }

    /*
     * Assign chunk and move cursor 'pos' before first element of substream. Use
     * #MyriadNode.seed and MAX_RAND. Transfer PRNG into initial state if necessary.
     */
    public abstract void init();

    /*
     * Return random number in assigned substream at given position.
     */
    public abstract long at(long pos);

    /*
     * Return next random number as 64 bit integer.
     */
    public abstract long nextLong();

    /*
    * Return next random number as 32 bit integer.
     */
    public abstract int nextInt();

    /*
     * Returns hash of u as a double-precision floating value between 0. and 1.
     * */
    public abstract double nextDouble();


}