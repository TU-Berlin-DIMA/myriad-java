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

import java.util.Random;

public abstract class PRNG{

    /*
    *  Node information object.
    */
    private MyriadNode m;
    
    /*
    * Very large or maximum value (period) that can be generated.
    */
    private long OFFSET_SUBSTREAM;
    
    /*
    * Position polonger in chunk of current node.
    */
    private long pos;
    
    public PRNG(MyriadNode m){
        this.m = m;
        this.OFFSET_SUBSTREAM = 0;
        init();
    }
    
    /*
    * Assign chunk and move cursor 'pos' before first element of substream. 
    * Use #MyriadNode.seed and MAX_RAND.
    */
    private void init();
    
    /* 
    * Return random number in assigned substream at given position. 
    */
    public long at(long pos){
        return compute(pos + OFFSET_SUBSTREAM);
    }
    
    /*
    * Implementation of the random number generation function.
    */
    private long compute(long pos);
    
}