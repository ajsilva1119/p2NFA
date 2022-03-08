package fa.nfa;

import java.util.HashMap;

public class NFAState extends fa.State{

    private HashMap<Character,NFAState> transitions;
    public NFAState(String name) {
        this.name = name;
        transitions = new HashMap<Character,NFAState>();
    }

//    public void getNextState(Character input) {
//        transitions.get(input);
//    }
    public void addTransition(Character input, NFAState newState) {
        if( !(transitions.containsKey(input))) { //checks if a transition exsits and needs to a make a new spot
            transitions.put(input, newState);
        }
        // need to figure out how to map same key to different state.
    }
}
