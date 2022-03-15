package fa.nfa;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import fa.dfa.DFAState;

/**
 * Represents an NFAState object. ADD MORE HERE
 * 
 * @author Kincaid Schmitt, Alex Silva
 *
 */
public class NFAState extends fa.State{

    private LinkedHashMap<Character,Set<NFAState>> transitions;
    private boolean isFinal;
    
    /**
     * Constructor for the NFAState class
     * 
     * @param name - name of the state
     * @param f - is a final state
     */
    public NFAState(String name, boolean f) {
        this.name = name;
        this.isFinal = f;
        this.transitions = new LinkedHashMap<Character,Set<NFAState>>();
    }
    
    /**
     * 
     * 
     * @param name
     */
    public NFAState(String name) {
        this.name = name;
        this.isFinal = false;
        this.transitions = new LinkedHashMap<Character,Set<NFAState>>();
    }

    /**
     * Gets the set of next states given an input value
     * 
     * @param input - transition character
     * @return Set<NFAState> containing all next states given an input
     */
    public Set<NFAState> getNextState(Character input) {
    	 if( !(transitions.containsKey(input))) {
    		 return new LinkedHashSet<>();
    	 }
    	 return transitions.get(input);
    }
    
    /**
     * Adds a transition given an input and next state
     * 
     * @param input - the transition character
     * @param tranState - the next state given the transition character
     */
    public void addTransition(Character input, NFAState tranState) {
        if( !(transitions.containsKey(input))) { //checks if a transition exists and needs to a make a new spot
        	Set<NFAState> newState = new LinkedHashSet<>();
        	newState.add(tranState);
            transitions.put(input, newState);
        } else {
        	Set<NFAState> states = transitions.get(input); //get the current set of transitions for the input
        	states.add(tranState); //add the new state to the list
        }
    }
    
    /**
     * Method to check if the state is a final state 
     * 
     * @return true if the state is a final state
     */
    public boolean isFinalState() {
    	return this.isFinal;
    }
    
    /**
     * Updates the state to be a final state.
     */
    public void setToFinalState() {
    	this.isFinal = true;
    }
    
    /**
     * @return the name of the state
     */
    public String getName() {
    	return this.name;
    }
    
	/**
	 * Retrieves the states that <code>this</code> transitions to
	 * on the given symbol
	 * @param symb - the alphabet symbol
	 * @return the new states
	 */
	public Set<NFAState> getTo(char symb){
		Set<NFAState> ret = transitions.get(symb);
		/*
		 * if(ret == null){
		 * System.err.println("ERROR: NFAState.getTo(char symb) returns null on " + symb
		 * + " from " + name); System.exit(2); }
		 */
		return transitions.get(symb);
	}
}
