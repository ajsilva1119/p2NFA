package fa.nfa;

import fa.State;
import fa.dfa.DFA;

import java.util.LinkedHashSet;
import java.util.Set;

public class NFA implements fa.nfa.NFAInterface{

    private Set<Character> alphabet;
    private Set<NFAState> states;
    private Set<NFAState> finalStates;
    private NFAState startState;

    public NFA(){

        alphabet = new LinkedHashSet<>();
        states = new LinkedHashSet<>();
        finalStates = new LinkedHashSet<>();
        startState = null;

    }
    @Override
    public void addStartState(String name) {

        NFAState start = new NFAState(name);
        startState = start;
        states.add(start);
    }

    @Override
    public void addState(String name) {

    }

    @Override
    public void addFinalState(String name) {

    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {

    }

    @Override
    public Set<? extends State> getStates() {
        return null;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        return null;
    }

    @Override
    public State getStartState() {
        return null;
    }

    @Override
    public Set<Character> getABC() {
        return null;
    }

    @Override
    public DFA getDFA() {
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        return null;
    }
}
