package fa.nfa;

import fa.State;
import fa.dfa.DFA;

import java.util.LinkedHashSet;
import java.util.Set;

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
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return null;
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
