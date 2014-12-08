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

package com.myriad_toolkit.myriad.core;

import java.io.FileNotFoundException;
import java.util.Random;

public class Myriad {

    // TODO:
    private final int baseSize = 1000;
    private int scale = 1;

    public static void main(String[] args) throws FileNotFoundException {

        // parse input arguments
        if (args.length < 4 || args.length > 5) {
            // spec_src = Scala or XML data description
            System.out.println("Usage: javac Myriad <spec_src> <nID> <N> <sf> [<seed>]");
            return;
        }

        int seed = args.length == 4 ? new Random().nextInt() : Integer.parseInt(args[4]);

        // 1. Receive data generation description (scala class or XML speck)
        Generator plan = new Generator(args[0]);

        // 2. Create Myriad node and generate output
        new MyriadNode(plan, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), seed).run();

    }

}