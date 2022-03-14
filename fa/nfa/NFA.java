package fa.nfa;

import fa.State;
import fa.dfa.DFA;

import java.util.*;

public class NFA implements fa.nfa.NFAInterface{

    private LinkedHashSet<Character> alphabet;
    private LinkedHashSet<NFAState> states;
    private LinkedHashSet<NFAState> finalStates;
    private NFAState startState;
    
    //Constants
	private final char EMPTY = 'e';

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
    	for(NFAState s: states) {
    		if(s.getName().equals(name)) {
    			break;
    		}
    	}
    	NFAState newState = new NFAState(name);
    	states.add(newState);
    }

    @Override
    public void addFinalState(String name) {
    	NFAState newState = new NFAState(name,true);
    	states.add(newState);
    	finalStates.add(newState);
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
    	NFAState current = getNFAObj(fromState);
    	NFAState next = getNFAObj(toState);
    	current.addTransition(onSymb, next);
    	
    	addAlphaChar(onSymb);
    }
    
    /**
     * Private utility method to get the NFAState object of a corresponding
     * NFAState string name
     * 
     * @param stateName - a string of the name to look for
     * @return an NFAState with the name of stateName
     */
    private NFAState getNFAObj(String stateName) {
    	for(NFAState s: this.states) {
    		if(s.getName().equals(stateName)) {
    			return s;
    		}
    	}
    	return null; //return something else if not found???
    }
    
    /**
     * Private method that adds characters to the alphabet as needed
     * 
     * @param onSymb - symbol checked to be added
     */
    private void addAlphaChar(char onSymb) {
    	if(!alphabet.contains(onSymb) && onSymb != EMPTY) {
    		alphabet.add(onSymb);
    	}
    }

    @Override
    public Set<? extends State> getStates() {
        return this.states;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        return this.finalStates;
    }

    @Override
    public State getStartState() {
        return this.startState;
    }

    @Override
    public Set<Character> getABC() {
        return this.alphabet;
    }

    @Override
    public DFA getDFA() {

        DFA dfa = new DFA();

        String dfaStartState = eClosure(startState).toString();
        dfa.addStartState(dfaStartState); //adds the eClosure of the startstate to DFA start state
        dfa.addState(dfaStartState);
        if(checkFinal(eClosure(startState))){ //adds start state to finalstate if contains NFA final
            dfa.addFinalState(dfaStartState);
        }
        HashSet<Set<NFAState>> checkedStates = new LinkedHashSet<>(); //hold all the states checked
        checkedStates.add(eClosure(startState));

        Queue<Set<NFAState>> queue = new LinkedList<Set<NFAState>>(); // states to check if can be added to dfa
        queue.add(eClosure(startState));

        while (!queue.isEmpty()){
            Set<NFAState> s = queue.poll();

            for(char a : alphabet){
                Set<NFAState> newState = new HashSet<NFAState>();
                for(NFAState ss : s) { //
                    newState.addAll(getToState(ss,a));
                }

                if(!checkedStates.contains(newState)) {
                    queue.add(newState);
                    checkedStates.add(newState);
                    if (checkFinal(newState)) { //adds start state to finalstate if contains NFA final
                        dfa.addFinalState(newState.toString());
                    }
                    else{
                        dfa.addState(newState.toString());
                    }
                }
                dfa.addTransition(s.toString(), a, newState.toString());

            }


        }

        return dfa;
    }
    private boolean checkFinal(Set<NFAState> s){

        for(NFAState state : s){
            if(state.isFinalState()){
                return true;
            }
        }
        return false;
    }
    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.getTo(onSymb);
    }


    @Override
    public Set<NFAState> eClosure(NFAState s) {
    	Set<NFAState> eclosure = new LinkedHashSet<>();
    	eclosure.add(s); //starting point is part of closure

    	if(s.getTo(EMPTY)!=null) {
    		for(NFAState state: s.getTo(EMPTY)) {
    			eclosure.addAll(eClosure(state)); //adds all of the eclosures of every state reached on initial empty
    		}
    	}

        return eclosure;
    }

}
