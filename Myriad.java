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

public class Myriad{

   private const int baseSize = 1000;
   private int scale = 1;
   private PRNG prng;
    

    public static void main(String[] args){

        // parse input arguments
        if (args.length < 3 || args.length > 4){
            System.out.println("Usage: javac Myriad <nID> <N> <sf> [<seed>]");
            return;
        }
        
        int seed = args.length == 3 ? Random().nextInt() : Integer.parsInt(args[4]);
        
        // create node information object
        MyriadNode m = new MyriadNode(Integer.parsInt(args[0]), Integer.parsInt(args[1]), Integer.parsInt(args[2]), seed);
        
        // read n-gram/grammar/regex input file and create model object
        LanguageModel l = new LanguageModel(m);
        
    }


    public class MyriadNode{
        
        /*
        * Node identifier in [0..N-1]
        */
        public int nID;
    
        /*
        * Total number of processes.
        */
        public int N;
    
        /*
        * Total table size.
        */
        public int size;
        
        /*
        * Seed for PRNG.
        */
        
        public MyriadNode(int nID, int N, int size, int seed){
            this.nID = nID;
            this.N = N;
            this.size = size;
            this.seed = seed;
        }
        
    
    }

}