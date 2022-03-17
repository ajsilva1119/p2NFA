package fa.nfa;

import fa.State;
import fa.dfa.DFA;

import java.util.*;

/**
 * A NFA object that represents a full NFA. Each DFA has a corresponding
 * alphabet, states, finalStates, and startState. There are many methods
 * that we implemented to add and change our NFA object. The NFA can take
 * in a string input and decide if the string is accepted or rejected.
 * 
 * @author Kincaid Schmitt, Alex Silva
 *
 */
public class NFA implements fa.nfa.NFAInterface{

	//Global variables
    private LinkedHashSet<Character> alphabet;
    private LinkedHashSet<NFAState> states;
    private LinkedHashSet<NFAState> finalStates;
    private NFAState startState;
    
    //Constants
	private final char EMPTY = 'e';

    /**
     * Constructor to create a new NFA object
     */
    public NFA(){
        alphabet = new LinkedHashSet<>();
        states = new LinkedHashSet<>();
        finalStates = new LinkedHashSet<>();
        startState = null;
    }
    
    @Override
    public void addStartState(String name) {
    	//check if the state already exists
    	NFAState start = getNFAObj(name);
    	if(start != null) {
    		//set the existing state to the start state
            startState = start;
            return;
    	}
    	//create a new state and set it to the start state
        start = new NFAState(name);
        startState = start;
        states.add(start);
    }

    @Override
    public void addState(String name) {
    	//check if the state already exists in the NFA
    	if(getNFAObj(name) != null) return;
    	
    	NFAState newState = new NFAState(name);
    	states.add(newState);
    }

    @Override
    public void addFinalState(String name) {
    	//creates a new state and adds it to the final state
    	NFAState newState = new NFAState(name,true);
    	states.add(newState);
    	finalStates.add(newState);
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
    	//get the two and from states NFAState object
    	NFAState current = getNFAObj(fromState);
    	NFAState next = getNFAObj(toState);
    	//update the transition table
    	current.addTransition(onSymb, next);
    	
    	//checks to add to alphabet each time we add a transition
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
    	//iterate over all existing states and compare names
    	for(NFAState s: this.states) {
    		if(s.getName().equals(stateName)) {
    			return s;
    		}
    	}
    	return null;
    }
    
    /**
     * Private method that adds characters to the alphabet as needed
     * 
     * @param onSymb - symbol checked to be added
     */
    private void addAlphaChar(char onSymb) {
    	//check if the symbol does not exist in the alphabet and it is not an epsilon
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
        Set<NFAState> eClose = eClosure(startState);
        if(checkContainsFinal(eClose)){ //adds start state to finalstate if contains NFA final
            dfa.addFinalState(dfaStartState);
        }
        dfa.addStartState(dfaStartState); //adds the eClosure of the startstate to DFA start state

        HashSet<Set<NFAState>> checkedStates = new LinkedHashSet<>(); //hold all the states checked
        checkedStates.add(eClose);

        Queue<Set<NFAState>> queue = new LinkedList<Set<NFAState>>(); // states to check if can be added to dfa
        queue.add(eClose);

        while (!queue.isEmpty()){
            Set<NFAState> s = queue.poll();

            for(char a : alphabet){
                Set<NFAState> newState = new HashSet<NFAState>();
                for(NFAState ss : s) { 
                	if(getToState(ss,a) == null) continue;
                    newState.addAll(getToState(ss,a));
                    for(NFAState z: getToState(ss,a)) {
                    	newState.addAll(eClosure(z));                    	
                    }
                }

                if(!checkedStates.contains(newState)) {
                    queue.add(newState);
                    checkedStates.add(newState);
                    if (checkContainsFinal(newState)) { //adds start state to finalstate if contains NFA final
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
    
    
    /**
     * Simple private utility method to check if a set of NFAStates
     * contains a state that is a final state
     * 
     * @param s - the set of states
     * @return boolean value of if the set contained a final state
     */
    private boolean checkContainsFinal(Set<NFAState> s){
    	//iterate over all states and return true if any state is a final
        for(NFAState state : s){
            if(state.isFinalState()) return true;
        }
        return false;
    }
 
    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.getTo(onSymb);
    }


    @Override
    public Set<NFAState> eClosure(NFAState s) {
    	//instantiate new list objects for seen states and eclosure states
    	LinkedHashSet<NFAState> seen = new LinkedHashSet<>(); //use to prevent evaluating multiple states infinite times
    	Set<NFAState> eclosure = new LinkedHashSet<>();
    	
    	eclosure.add(s); //starting point is part of closure
    	seen.add(s); //add to list of evaluated states
    	if(s.getTo(EMPTY) != null) {
    		for(NFAState state: s.getTo(EMPTY)) {
    			eclosure.addAll(eClosure(state, seen)); //adds all of the eclosures of every state reached on initial empty    				
    		}
    	}

        return eclosure;
    }
    
    /**
     * Overloaded eClosure method. Traverses all epsilon transitions and determine what states 
     * can be reached. Takes the additional parameter of seenEclosureStates. 
     * 
     * @param s - state to find eClosure of
     * @param seenEclosureStates - the list of states already seen by the eClosure.
     * @return - the set of states reachable from the epsilon transition
     */
    private Set<NFAState> eClosure(NFAState s,  LinkedHashSet<NFAState> seenEclosureStates) {
    	//instantiate new list to store eclosure states
    	Set<NFAState> eclosure = new LinkedHashSet<>();
    	eclosure.add(s); //starting point is part of closure
    	seenEclosureStates.add(s);
    	
    	if(s.getTo(EMPTY) != null) {
    		for(NFAState state: s.getTo(EMPTY)) {
    			if(!seenEclosureStates.contains(state)) { //only evaluate the next eclosure on states we have not seen before
    				eclosure.addAll(eClosure(state)); //adds all of the eclosures of every state reached on initial empty    				    				
    			}
    		}
    	}

        return eclosure;
    }

}
