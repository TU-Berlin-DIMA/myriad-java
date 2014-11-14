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

public class HashRandomStream extends PRNG {


    private long PERIOD;

    public HashRandomStream(MyriadNode m){
        super(m);
    }
    
    private void init(){
        // TODO: determine period of hash function below
        this.PERIOD = 10000000000;
        super.OFFSET_SUBSTREAM = super.m.nID * this.PERIOD / super.m.N;
    }
    
    // is this code necessary?
    public long at(long pos){
        super.at(pos);
    }
    
    // Random hash function from "Numerical Recipes"
    private long compute(long pos){
        long x = pos;
        x = 3935559000370003845L * x + 2691343689449507681L;
        x = x ^ (x >> 21);
        x = x ^ (x << 37);
        x = x ^ (x >> 4);
        x = 4768777513237032717L * x;
        x = x ^ (x << 20);
        x = x ^ (x >> 41);
        x = x ^ (x << 5);
        return x;
    }
    
    /*
UInt64 x = _elementS.v[0];
x = 3935559000370003845LL * x + 2691343689449507681LL;
x = x ^ (x >> 21);
x = x ^ (x << 37);
x = x ^ (x >> 4);
x = 4768777513237032717LL * x;
x = x ^ (x << 20);
x = x ^ (x >> 41);
x = x ^ (x << 5);
return x;
}*/

}