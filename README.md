# Project 2: NFA
* Author: Alex Silva, Kincaid Schmitt
* Class: CS361 Section 001
* Semester: Spring 2022

## Overview

This application we were tasked with taking in an input file and creating
a DFA(Deterministic Finite Automata) where we had to run through a couple
test string and make a swap.

## Reflection

In the beginning of this project, it was nice and not very many hurdles. 
It made us think and called for some creative solutions or just looking
deeper into how like the HashMaps work and such. Working with though 
through the Driver classs is how we went about it, where when the class 
called a method that wasn't implemented we would then create it.

The real struggle began when we started the Swap() method. At first
my partner and I assumed it would not be the big of a deal. The problem
arised when trying to create a deep copy of the DFA to manipulate 
instead of the original. We tried many different ideas but the solution
in the end was taking the long way and manually adiing each element
of the DFA to the copy. We got it to swap but we some how could not get
the final state to correct. As you can see in the image below it appears
the transitions were swapped but looking into it more we concluded that an 
error occured in the deep copy. First output is the original DFA and the
second is our copy and swapped one.

![image](https://user-images.githubusercontent.com/89565246/154004245-8a2bdebe-f9dc-4030-b958-e0c599f9fef8.png)

## Compiling and Using

To compile, execute the following command in the main project directory:
```
$ javac fa/dfa/DFADriver.java
```

Run the compiled class with the command:
```
$ java fa.dfa.DFADriver ./tests/p1tc1.txt
```

The program will then run the chosen test file and create the following
DFA. Then creates a copy of that DFA and swaps the transitions and then
it run a seires of inputs that will either fail or pass through both of
them. Final tells us if the swap DFA is correct.

## Sources used

https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
https://docs.oracle.com/javase/8/docs/api/java/util/Set.html

----------
