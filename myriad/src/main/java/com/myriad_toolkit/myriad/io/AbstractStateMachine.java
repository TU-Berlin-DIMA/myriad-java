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

package com.myriad_toolkit.myriad.io;

//import HashRandomStream;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.myriad_toolkit.myriad.math.AbstractPRNG;
import com.myriad_toolkit.myriad.math.HashRandomStream;
import com.myriad_toolkit.myriad.core.MyriadNode;

/*
 * Abstract class for constructing and running a finite state machine. 
 */

public abstract class AbstractStateMachine {

    /* Pseudorandom number generator instance for transition probabilities */
    private AbstractPRNG prng;
    // Starting state
    private State start;
    private double EPS = Double.MIN_VALUE * 100;
    private String output;

    public class State {

        private boolean isFinal;
        private HashMap<State, Transition> trans;

        State(Boolean isFinal) {
            this.isFinal = isFinal;
            trans = new HashMap<State, Transition>();
        }

        public class Transition {
            public Double delta;
            public String output;

            public Transition(Double delta, String output) {
                this.delta = delta;
                this.output = output;
            }
        }

        public class Traversal<State, String> implements Map.Entry<State, String> {
            State state;
            String output;

            public Traversal(State state, String output) {
                this.state = state;
                this.output = output;
            }

            public State getKey() {
                return this.state;
            }

            public String getValue() {
                return this.output;
            }

            public String setValue(String value) {
                String old = this.output;
                this.output = value;
                return old;
            }
        }

        public class TransitionException extends Exception {
            public TransitionException() {
                super();
            }

            public TransitionException(String message) {
                super(message);
            }
        }

        public void setIsFinal(boolean isFinal) {
            this.isFinal = isFinal;
        }

        public Boolean getIsFinal() {
            return this.isFinal;
        }

        // Collect transition probs of all outgoing edges
        public Vector<Double> getValues() {
            Vector<Double> tmp = new Vector<Double>(trans.size());
            for (Transition t : trans.values())
                tmp.add(t.delta);
            return tmp;
        }

        // add a new transition
        public void addTrans(State state, Double delta, String output) {
            trans.put(state, new Transition(delta, output));
        }

        // remove successor state non-recursively, i.e. children will rest in
        // automaton
        public boolean remTrans(State state) {
            return null != trans.remove(state);
        }

        // traverse w.r.t. transition probabilities and return new state
        public Map.Entry<State, String> traverse() throws TransitionException {
            double p = AbstractStateMachine.this.prng.nextDouble();
            double cumSum = 0;
            for (Map.Entry<State, Transition> edge : this.trans.entrySet()) {
                cumSum += edge.getValue().delta;
                if (p <= cumSum)
                    return new Traversal<State, String>(edge.getKey(), edge.getValue().output);
            }
            throw new TransitionException("No transition state found!");
        }
    }

    /* Constructor */
    public AbstractStateMachine(String path, MyriadNode m) throws FileNotFoundException {
        this.start = null;
        initMachine(path);
        this.prng = new HashRandomStream(m);
        this.output = "";
    }

    public boolean addStart(State start, Boolean isFinal) {
        if (this.start != null)
            return false;
        this.start = start;
        this.start.isFinal = isFinal;
        return true;
    }

    // Return start state
    public State getStart() {
        return this.start;
    }

    // collect all distinct states of machine
    public HashSet<State> getStates() {
        HashSet<State> S = new HashSet<State>();
        if (this.start == null)
            return S;
        LinkedList<State> Q = new LinkedList<State>();
        Q.add(this.start);
        while (!Q.isEmpty()) {
            State v = Q.poll();
            S.add(v);
            for (State w : v.trans.keySet()) {
                if (!S.contains(w))
                    Q.add(w);
            }
        }
        return S;
    }

    // normalize transition probs
    public boolean normalizeDelta() {
        Boolean touched = false;
        State curr = this.start;
        if (curr == null)
            return true;
        Set<State> states = getStates();
        for (State state : states) {
            double deltaCum = 0;
            // Todo: use iterator, foreach not possible for AbstractMaps
            for (Map.Entry<State, State.Transition> succ : state.trans.entrySet())
                // double delta : state.trans.getValues())
                deltaCum += succ.getValue().delta;
            if (deltaCum < 1 - this.EPS || deltaCum > 1 + EPS) {
                for (Map.Entry<State, State.Transition> succ : state.trans.entrySet()) {
                    state.trans.put(succ.getKey(), state.new Transition(succ.getValue().delta / deltaCum, succ.getValue().output));
                }
                touched = true;
            }
        }
        return touched;
    }

    // check for sanity: normalized transition probs
    public boolean checkSanity() {
        State curr = this.start;
        if (curr == null)
            return true;
        Set<State> states = getStates();
        for (State state : states) {
            double deltaCum = 0;
            for (double delta : state.getValues())
                deltaCum += delta;
            if (deltaCum < 1 - this.EPS || deltaCum > 1 + EPS)
                return false;
        }
        return true;
    }

    /* Initialize model from its textual representation */
    abstract void initMachine(String path) throws FileNotFoundException;

    /* Produce output string by AbstractPRNG supported graph traversal */
    abstract String produce();

}