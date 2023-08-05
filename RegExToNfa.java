package csen1002.main.task1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Write your info here
 * 
 * @name Marwan Khalid Farag
 * @id 46-14780
 * @labNumber 23
 */

public class RegExToNfa {

	/**
	 * Constructs an NFA corresponding to a regular expression based on Thompson's
	 * construction
	 * 
	 * @param input The alphabet and the regular expression in postfix notation for
	 *              which the NFA is to be constructed
	 */
	public static Stack<NFA> stack = new Stack<>();
	public static int stateCounter = 0;

	public NFA finalNFA;

	public String alphabet;
	public static TreeSet<Transition> transitions = new TreeSet<Transition>();
	public static TreeSet<State> states = new TreeSet<State>();

	public RegExToNfa(String input) {
		// TODO Auto-generated constructor stub

		//reinitialize the instance variables
		transitions = new TreeSet<Transition>();
		states = new TreeSet<State>();
		stack = new Stack<>();
		stateCounter = 0;

		//split input into A#R
		String[] splitted = input.split("#");
		alphabet = splitted[0];//will be part of toString() output, so save it in instance variable
		HashSet<Character> characterSet = new HashSet<>();
		for (char c : alphabet.toCharArray())
		{
			if(c==';') continue; //; is not an alphabet letter
			characterSet.add(c);
		}


		String regex = splitted[1];
		for (int i = 0; i < regex.length(); i++) {
			NFA n;
			if (characterSet.contains(regex.charAt(i)) || regex.charAt(i)=='e') //if it is alphabet chat, create NFA and put it in the stack{
			{
				n = new NFA(regex.charAt(i));
				stack.push(n);
			}
			else //if operator, pop operands from stack and create new NFA
			{
				if(regex.charAt(i)=='*')
				{
					NFA operand = stack.pop();
					n = new NFA(operand, null, regex.charAt(i));
					stack.push(n);
				}
				else if(regex.charAt(i)=='.' || regex.charAt(i)=='|')
				{
					NFA operand2 = stack.pop();
					NFA operand1 = stack.pop();
					n = new NFA(operand1, operand2, regex.charAt(i));
					stack.push(n);
				}
			}

		}

			this.finalNFA = stack.pop();

	}
	/**
	 * @return Returns a formatted string representation of the NFA. The string
	 *         representation follows the one in the task description
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String output = "";
		int initialState = finalNFA.initialState.getCount();
		int finalState = finalNFA.finalState.getCount();

		//output = states#alphabet#transitions#initialState#finalState
		output += states.stream().map(Object::toString).collect(Collectors.joining(";")) + "#" + alphabet + "#"
			  + transitions.stream().map(Object::toString).collect(Collectors.joining(";")) + "#" + initialState
		      + "#" + finalState;

		return output;
	}

	public static void main(String[] args) {
		RegExToNfa regExToNfa= new RegExToNfa("a;o;z#za|*o.");
		System.out.println(regExToNfa.toString()
				.equals("0;1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;17;18;19#b;f;l;p#0,l,1;1,e,5;2,f,3;3,e,5;4,e,0;4,e,2;5,e,9;6,f,7;7,e,9;8,e,4;8,e,6;9,e,19;10,b,11;11,e,15;12,l,13;13,e,15;14,e,10;14,e,12;15,p,17;17,e,19;18,e,8;18,e,14#18#19"));
	}



}

class NFA {
	State initialState;
	State finalState;
	public NFA(NFA operand1, NFA operand2, char operation) //creating NFA for an operator
	{
		if(operation == '|') {
			//new states at the beginning and end of new nfa
			initialState = new State();
			finalState = new State();
			//initial state connected to both initial states of the old NFAs
			makeTransition(initialState, operand1.initialState, 'e');
			makeTransition(initialState, operand2.initialState, 'e');
			//final states of old NFAs connected to the new NFA
			makeTransition(operand1.finalState, finalState, 'e');
			makeTransition(operand2.finalState, finalState, 'e');
		}
		else if(operation == '.')
		{
			initialState = operand1.initialState;
			finalState = operand2.finalState;
			//replace initial state of NFA2 with final state of NFA1 and get all of its transitions
			replaceState(operand1.finalState, operand2.initialState);
		}
		else if(operation=='*') {
			//new states at the beginning and end of new nfa
			initialState = new State();
			finalState = new State();

			makeTransition(initialState, operand1.initialState, 'e');
			makeTransition(initialState, finalState, 'e');
			makeTransition(operand1.finalState, finalState, 'e');
			makeTransition(operand1.finalState, operand1.initialState, 'e');
		}
		else
			System.out.println("Entered false operation"+ operation);//for debugging purposes
	}

	public NFA(char input)
	{
		initialState = new State();
		finalState = new State();
		makeTransition(initialState, finalState, input);

	}
	public void makeTransition(State state1, State state2, char input)
	{
		state1.addTransitionTo(state2, input);
		RegExToNfa.transitions.add(new Transition(state1, state2, input));

	}
	public void removeTransition(State state1, State state2, char input)
	{
		RegExToNfa.transitions.remove(new Transition(state1, state2, input));
	}
	public void replaceState(State s1, State s2)
	{
		//goal is to remove s2, s1 should have all transitions of s2
		for (HashMap.Entry<State, Character> entry : s2.transitionsTo.entrySet()) {
			State s = entry.getKey();
			makeTransition(s1,s, entry.getValue());
			removeTransition(s2,s, entry.getValue());
			RegExToNfa.states.remove(s2);
		}
	}

}

class State implements Comparable{

	int stateCount;
	TreeMap<State, Character> transitionsTo = new TreeMap<State, Character>();
	TreeMap<State, Character> transitionsFrom = new TreeMap<State, Character>();
	public State()
	{
		this.stateCount = RegExToNfa.stateCounter++;
		RegExToNfa.states.add(this);
	}
	public void addTransitionTo(State s, char c)
	{
		transitionsTo.put(s, c);
	}

	public int getCount()
	{
		return this.stateCount;
	}


	@Override
	public int compareTo(Object o) {
		return this.getCount()-((State)o).getCount();
	}

	@Override
	public String toString() {
		return stateCount+"";
	}
}

class Transition implements Comparable{

	State from;
	State to;
	char input;

	public Transition(State from, State to, char input)
	{
		this.from=from;
		this.to=to;
		this.input=input;
	}
	@Override
	public int compareTo(Object o) {
		if(this.from.getCount()==((Transition)o).from.getCount())
			return this.to.getCount() - ((Transition)o).to.getCount();
		else
			return this.from.getCount() - ((Transition)o).from.getCount();

	}

	@Override
	public boolean equals(Object obj) {
		return this.from.getCount() == ((Transition)obj).from.getCount() && this.to.getCount() == ((Transition)obj).to.getCount();
	}

	@Override
	public String toString() {
		return this.from.getCount() + "," + this.input + "," + this.to.getCount();
	}
}


