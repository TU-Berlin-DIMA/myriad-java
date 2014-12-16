package com.myriad_toolkit.myriad.textgen;

import java.util.List;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

/**
 * 
 * @author Umar Maqsud (umar.maqsud@campus.tu-berlin.de)
 * 
 */
public class Regxlar {

    public static int DEFAULT_MAX_LENGTH = 4000;

    private RegExp regExp;
    private Automaton automaton;

    public Regxlar(String regularExpression) {
        regExp = new RegExp(regularExpression);
        automaton = regExp.toAutomaton();
    }

    public String generateNext(RandomGenerator randomGenerator, int maxLength) {

        String randomString = "";

        State currentState = automaton.getInitialState();

        for (int i = 0; i < maxLength; i++) {

            List<Transition> transitions = currentState.getSortedTransitions(false);

            if (transitions.size() == 0) {
                return randomString;
            }

            Transition randomTransition = transitions.get(randomGenerator.nextInt(transitions.size()));

            char randomChar = (char) (randomGenerator.nextInt((randomTransition.getMax() - randomTransition.getMin()) + 1) + randomTransition.getMin());

            randomString += randomChar;

            currentState = randomTransition.getDest();
        }

        return randomString;
    }

    public String generateNext(RandomGenerator randomGenerator) {
        return generateNext(randomGenerator, DEFAULT_MAX_LENGTH);
    }

    public String generateNext() {
        return generateNext(new SeedRandomGenerator(Math.random()), DEFAULT_MAX_LENGTH);
    }
}
