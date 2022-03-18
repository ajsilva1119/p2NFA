# Project 2: NFA
* Author: Alex Silva, Kincaid Schmitt
* Class: CS361 Section 001
* Semester: Spring 2022

## Overview

This application we were tasked with taking in an input file and creating
a NFA(Non-deterministic Finite Automata) and after creating the NFA, then
creating a DFA(Deterministic Finite Automata) using everything given to us
in the NFA java.

## Reflection

The start of the of the this project was straight forward and felt similar
to project one but just implementing the file as an NFA. As for going through
the rest of the project we had everything needed to succeed, as we know how
to create a DFA from a NFA so just converting it was the problem to solve.

The maethods that gave us the most troubles were eClosure which returns a Set
of NFAStates that a given state can get to a an empty transition. This method
comes in handy later not for just a start state. The other difficult method and were 
most the project was spent was getDFA(). In this method, we directly took all NFA
elements to give create the DFA. The eclosure was a little difficult because we needed
to create it using DFS and a stack implementation to acheive it. With getDFA() it was just taking
the time and going through all the steps to find each state for the dfa. All the
different tansition states possible had to be explored. We ran into the problem of not having the 
full closure after transitioning to the next state. We had to implment adding the
eClosure before and after the transition on the symbol. This method uses a queue to 
run the different options for possible states and uses BFS. After taking some time to think through
the steps of creating a DFA from an NFA, we were eventually able to succesfully generate the
accurate DFA given an NFA.

## Compiling and Using

To compile, execute the following command in the main project directory:
```
$ javac fa/nfa/NFADriver.java
```

Run the compiled class with the command:
```
$ java fa.nfa.NFADriver ./tests/p1tc1.txt
```

The program will then run the chosen test file and create the following
NFA and convert to the correct DFA. Then prints DFA and then
it run a seires of inputs that will either fail or pass through both of
them. With the input file, a user can enter a string. The output will
respond with a 'yes' or 'no' as to if the string is accepted by the
DFA.

## Sources used

https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
https://docs.oracle.com/javase/8/docs/api/java/util/Set.html
https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html

----------
