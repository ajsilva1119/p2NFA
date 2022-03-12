package fa.nfa;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

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
}
