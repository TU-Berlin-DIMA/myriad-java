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

import java.io.*;

/*
 * Abstract class for constructing and running a finite state machine. 
*/

public abstract class AbstractStateMachine{
    
    /* Pseudorandom number generator instance for transition probabilities */
    private PRNG prng;
    
    private double EPS = Double.MIN_VALUE*100;

    public enum State{
        
        private boolean isFinal;
        private Vector<Transition> trans;
        
        public State(Boolean isFinal){
            this.isFinal = isFinal;
            trans = new Map<State, Transition>();
        }
        
        public class Transition{
            public Float delta;
            public String output;
            public Transition(Float delta, String output){
                this.delta = delta;
                this.output = output;
            }
        }
        
        public class TransitionException extends Exception{
            public TransitionException() { super(); }
            public TransitionException(String message) { super(message); }
        }
        
        public void setIsFinal(boolean isFinal){
            this.isFinal = isFinal;
        }
        
        public Boolean getIsFinal(){
            return this.isFinal;
        }
        
        // add a new transition 
        public void addTrans(State state, Float delta, String output){
            trans.put(state, new Transition(delta, output));
        }
        
        // remove successor state non-recursively, i.e. children will rest in automaton
        public boolean remTrans(State state){
            trans.remove(state);
        }
        
        // traverse w.r.t. transition probabilities
        public Pair<State, String> traverse(){
            double p = State.this.prng.nextDouble();
            double cumSum = 0;
            
            for (Map.Entry<State, Transition> edge : this.trans.entrySet()){
                cumSum += edge.getValue().delta;
                if (p <= cumSum)
                    return new Pair<State, String>(edge.getKey(), edge.getValue().output);
            }
            throw new TransitionException("No transition state found!");
        }
    }
    
    private State start;
    
    /* Constructor */
    public AbstractStateMachine(String path, MyriadNode m){
        this.start = null;
        initMachine(path);
        this.prng = new PRNG(m);
    }
    
    public boolean addStart(State start, Boolean isFinal){
        if (!this.start != null)
        this.start = start;
        this.isFinal = isFinal;
    }
    
    // Return start state
    public State getStart(){
        return this.start;
    }
    
    // collect all distinct states of machine
    public Set<State> getStates(){
        Set<State> S = new Set<State>();
        if (this.start == null) return S;
        Queue<State> Q = new Queue<State>();
        Q.enqueue(this.start);
        while (!Q.isEmpty()){
            v = Q.poll();
            S.add(v);
            for (State w : v.trans.keySet()){
                if (!S.contains(w))
                    Q.add(w);
            }
        }
        return S;
    }
    
    // normalize transition probs
    public boolean normalizeDelta(){
        Boolean touched = false;
        State curr = this.start;
        if (this.curr = null) return true;
        Set<State> states = getStates();
        for (State state : states){
            double deltaCum = 0;
            for (double delta : state.trans.getValues())
                deltaCum += delta;
            if (deltaCum < 1-this.EPS || deltaCum > 1+EPS){
                for (Map.Entry<State, Transition> succ : state.trans.entrySet()){
                    trans.put(succ.getKey(), new Transition(succ.getValue().delta/deltaCum, succ.getValue().output));
                }
                touched = true;
            }
        }
        return touched;
    }
        
    // check for sanity: state duplicates, normalized transition probs
    public boolean checkSanity(){
        // TODO: check for duplicates pred(A) != pred(A') => duplicated enum State
        
        // check for normalized transition probs
        State curr = this.start;
        if (this.curr = null) return true;
        Set<State> states = getStates();
        for (State state : states){
            double deltaCum = 0;
            for (double delta : state.trans.getValues())
                deltaCum += delta;
            if (deltaCum < 1-this.EPS || deltaCum > 1+EPS){
                return false;
            }
        }
    
    }
    
    /* Initialize model from its textual representation */
    private void initMachine(String path) throws FileNotFoundException;
    

    /* Produce output string by PRNG supported graph traversal */
    public String produce(){
    
    }

}