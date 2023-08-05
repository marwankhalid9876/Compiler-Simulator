package csen1002.main.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Write your info here
 * 
 * @name Marwan Khalid Farag
 * @id 46-14780
 * @labNumber 23
 */


public class FallbackDfa {

	State initialState;
	HashMap<Integer, State> states; // map ex 3 --> state whose number is 3
	HashSet<State> finalStates;
	ArrayList<Character> alphabet;
	/**
	 * Constructs a Fallback DFA
	 * 
	 * @param fdfa A formatted string representation of the Fallback DFA. The string
	 *             representation follows the one in the task description
	 */
	public FallbackDfa(String fdfa) {
		// TODO Auto-generated constructor stub
		states = new HashMap<>();
		alphabet = new ArrayList<>();
		finalStates = new HashSet<>();


		String[] dfa = fdfa.split("#");
		String[] dfaStates = dfa[0].split(";");
		for(int i=0; i<dfaStates.length; i++)
			this.states.put(Integer.parseInt(dfaStates[i]) ,new State(Integer.parseInt(dfaStates[i]),this));
		String[] alphabetArray = dfa[1].split(",");
		for(int i=0; i<alphabetArray.length; i++)
			this.alphabet.add(alphabetArray[i].charAt(0));
		String[] transitions = dfa[2].split(";");
		for(int i=0; i<transitions.length; i++)
		{
			String[] transition = transitions[i].split(",");
			State stateFrom = states.get(Integer.parseInt(transition[0]));
			char inputAlphabet = transition[1].charAt(0);
			State stateTo = states.get(Integer.parseInt(transition[2]));
			stateFrom.addTransition(inputAlphabet, stateTo);
		}
		initialState = states.get(Integer.parseInt(dfa[3]));

		String[] finalStatesArray = dfa[4].split(";");
		for(int i=0; i<finalStatesArray.length; i++)
			finalStates.add(states.get(Integer.parseInt(finalStatesArray[i])));

//		System.out.println(states);
//		System.out.println(alphabet);
//		System.out.println(initialState);
//		System.out.println(finalStates);
//
//		for (Integer state : states.keySet()) {
//			System.out.println(state + "" + states.get(state).transitions);
//		}


	}

	/**
	 * @param input The string to simulate by the FDFA.
	 * 
	 * @return Returns a formatted string representation of the list of tokens. The
	 *         string representation follows the one in the task description
	 */
	public String run(String input) {
		// TODO Auto-generated method stub

		ArrayList<String> tokens = new ArrayList<>();

		int R = 0;
		int L;
		while(R<input.length()) {
			Stack<State> statesStack = new Stack<>();
			State currState = initialState;
			statesStack.push(currState);
			for (L = R; L < input.length(); L++) {
				char inputChar = input.charAt(L);
				currState = currState.getNextStateFromInput(inputChar);
				statesStack.push(currState);
			}
			State LastStateReached = currState;
			if (finalStates.contains(currState))
			{
				currState.performAction(input.substring(R, L), tokens);
				break;
			}
			else {
				while (!statesStack.isEmpty()) {
					L--;
					currState = statesStack.pop();
					if (finalStates.contains(currState)) {
						currState.performAction(input.substring(R, L+1), tokens);
						L++;
						R = L;
						break;
					}
				}
				if (statesStack.isEmpty()) {

					LastStateReached.performAction(input.substring(R), tokens);
					break;
				}
			}
		}

		return tokens.stream().map(Object::toString).collect(Collectors.joining(";"));	}

	public static void main(String[] args) {
		FallbackDfa fallbackDfa = new FallbackDfa("0;1;2;3#a,b#0,a,0;0,b,1;1,a,2;1,b,1;2,a,0;2,b,3;3,a,3;3,b,3#0#1;2");
		System.out.println(fallbackDfa.run("baababb"));
	}
	
}



class State{

	int number;
	FallbackDfa fallbackDfa;

	HashMap<Character, State> transitions;
	public State(int number, FallbackDfa fallbackDfa)
	{
		this.number=number;
		this.fallbackDfa = fallbackDfa;
		this.transitions = new HashMap<>();
	}
	public void performAction(String lex, ArrayList<String> tokens)
	{
		tokens.add(lex+","+number);
	}

	public void addTransition(char input, State stateTo)
	{
		transitions.put(input, stateTo);
	}
	public State getNextStateFromInput(char input)
	{
		return transitions.get(input);
	}

	@Override
	public String toString() {
		return number+"";
	}
}
