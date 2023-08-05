package csen1002.main.task2;

import com.sun.source.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Write your info here
 * 
 * @name Marwan Khalid Farag
 * @id 46-14780
 * @labNumber 23
 */

public class NfaToDfa {

	TreeSet<DFAState> dfaStates;
	TreeSet<DFATransition> dfaTransitions;
	String[] alphabet;

	DFAState dfaInitialState;

	TreeSet<DFAState> dfaFinalStates;

	/**
	 * Constructs a DFA corresponding to an NFA
	 * 
	 * @param input A formatted string representation of the NFA for which an
	 *              equivalent DFA is to be constructed. The string representation
	 *              follows the one in the task description
	 */
	public NfaToDfa(String input) {
		// TODO Auto-generated constructor stub
		String[] splitted = input.split("#");
		String[] nfaStates = splitted[0].split(";");
		alphabet = splitted[1].split(";");
		String[] nfaTransitionsArray = splitted[2].split(";");
		String nfaInitialState = splitted[3];

		HashSet<String> nfaFinalStates =  new HashSet<>(Arrays.asList(splitted[4].split(";")));
		//map ("state,alphabet") ---> [state1, state2, state3 ,..]
		HashMap<String, ArrayList<Integer>> nfaTransitionsMap = getNFATransitionsMap(nfaTransitionsArray);
		//map "state" ---> epsilonClosureStates
		HashMap<String, HashSet<Integer>> nfaEpsilonClosure = getEpsilonClosure(nfaStates, nfaTransitionsMap);

		//dfa initial state will be the epsilon closure of NFA initial state
		TreeSet<Integer> dfaInitialStateList = new TreeSet<>();
		dfaInitialStateList.add(Integer.parseInt(nfaInitialState));
		//get epsilon closure of nfa initial state;
		ArrayList<Integer> nfaISEpsilonTransition = new ArrayList<>(nfaEpsilonClosure.get(nfaInitialState));
		if(nfaISEpsilonTransition != null) {//if initial state has epsilon transitions
			for (Integer to : nfaISEpsilonTransition)
				dfaInitialStateList.add(to);
		}

		dfaInitialState = new DFAState(dfaInitialStateList);
		dfaStates = new TreeSet<>();
		dfaStates.add(dfaInitialState);
		dfaTransitions = new TreeSet<>();
		Stack<DFAState> statesWithNoTransition = new Stack<>();	//states that we need to assign transitions to
		statesWithNoTransition.add(dfaInitialState);
		while(!statesWithNoTransition.isEmpty()){
			DFAState state = statesWithNoTransition.pop();
			//add new transitions to the current state based on DFA transtitions
			addTransitions(state, nfaTransitionsMap, nfaEpsilonClosure, statesWithNoTransition);
		}
		dfaFinalStates = new TreeSet<>();
		for(DFAState compoundState : dfaStates)//for every compound state ex: 1/2/3/4 stored in treeset
		{
			TreeSet<Integer> atomStatesArray = compoundState.atomStates;//get the treeset of atom states
			TreeSet<Integer> atomStatesSet = new TreeSet<>( atomStatesArray );
			for(Integer atomState: atomStatesSet)
			{
				//for every atom state, if it was a final state in nfa, add the compound state to final states
				if(nfaFinalStates.contains(atomState+"")) dfaFinalStates.add(compoundState);
			}

		}

	}
	public HashMap<String, HashSet<Integer>> getEpsilonClosure(String[] nfaStates, HashMap<String, ArrayList<Integer>> nfaTranstionsMap)
	{
		//map "state" ---> (state1, state2, state3)
		HashMap<String, HashSet<Integer>> nfaEpsilonClosure = new HashMap<>();
		//initialize epsilon closure so that evey state only has itself
		for(int i =0; i<nfaStates.length; i++)
		{
			HashSet<Integer> stateEpsilonClosure = new HashSet<>();
			stateEpsilonClosure.add(Integer.parseInt(nfaStates[i]));
			nfaEpsilonClosure.put(nfaStates[i], stateEpsilonClosure);
		}

		//loop while the epsilon closure changes
		boolean somethingChanged = true;
		while(somethingChanged)
		{
			somethingChanged = false;
			//deep copy the map to compare if anything changed in the closure table
			HashMap<String, HashSet<Integer>> OldNfaEpsilonClosure = deepCopy(nfaEpsilonClosure);
			for(int i =0; i<nfaStates.length; i++)
			{
				String currState= nfaStates[i];
				HashSet<Integer> stateEpsilonClosure = nfaEpsilonClosure.get(currState);
				ArrayList<Integer> stateEpsilonTransitions = nfaTranstionsMap.get(currState + "," +"e");
				//add all epsilon closures of states whom curr state has epsilon transitions to
				if(stateEpsilonTransitions!=null) {
					for (Integer state : stateEpsilonTransitions)
					{
						stateEpsilonClosure.addAll(OldNfaEpsilonClosure.get(state+""));
					}
					//put the new epsilon closure of the current state in the table
					nfaEpsilonClosure.put(currState, stateEpsilonClosure);
					//did something change from the old nfa eps closure?
					somethingChanged = somethingChanged || !OldNfaEpsilonClosure.get(currState)
							.equals(nfaEpsilonClosure.get(currState));
				}
			}
		}

		return nfaEpsilonClosure;
	}

	public HashMap<String, HashSet<Integer>> deepCopy(HashMap<String, HashSet<Integer>> m)
	{
		//deep clone a hashmap of epsilon closures
		HashMap<String, HashSet<Integer>> deepCopied = new HashMap<>();
		for (Map.Entry<String,HashSet<Integer>> entry : m.entrySet())
		{
			HashSet<Integer> hs = entry.getValue();
			deepCopied.put(entry.getKey(), (HashSet<Integer>) hs.clone());
		}
		return  deepCopied;
	}

	public void addTransitions(DFAState currState, HashMap<String, ArrayList<Integer>> nfaTransitionsMap, HashMap<String, HashSet<Integer>> nfaEpsilonClosure, Stack<DFAState> statesWithNoTransitions)
	{
		//if the current state is dead state return, its transitions are handled in the toString
		if(currState.atomStates.first()==-1) return;
		//for every alphabet, we will have a transition from this state
		for(int i=0; i<alphabet.length; i++)
		{
			TreeSet<Integer> toNewCompoundState = new TreeSet<>(); //store the to state in a treeset to be sorted
			TreeSet<Integer> splittedState = currState.atomStates; //split the current state to its atom states
			for(Integer j: splittedState)
			{
				//get arraylist of toStates from atom state and alphabet
				ArrayList<Integer> toNFAStates = nfaTransitionsMap.get(j+","+alphabet[i]);
				if(toNFAStates!=null)
					toNewCompoundState.addAll(toNFAStates);
			}
			TreeSet<Integer> toStateWithEpsilonTransition = new TreeSet<>();//toNewCompoundState with epsilon transiition
			for(Integer newAtomState: toNewCompoundState)
			{
				toStateWithEpsilonTransition.addAll(nfaEpsilonClosure.get(newAtomState.toString()));
			}
			//convert to string
			TreeSet<Integer> toCompoundStateArray = new TreeSet<>(toStateWithEpsilonTransition);
			if(!toCompoundStateArray.isEmpty()) {//if there is a transition
				DFAState newState = new DFAState(toCompoundStateArray);//create a new state that transition will go to
				if(!dfaStates.contains(newState)) statesWithNoTransitions.push(newState);//if new state did not exist before
				dfaStates.add(newState);
				dfaTransitions.add(new DFATransition(currState,alphabet[i], newState));
			}
			else { //if there is no transition found, add -1 dead state
				TreeSet<Integer> deadStateArray = new TreeSet<>();
				deadStateArray.add(-1);
				DFAState newState = new DFAState(deadStateArray);
				//add it to the stack if did not exist before
				if(!dfaStates.contains(newState)) statesWithNoTransitions.push(newState);
				dfaStates.add(newState);
				dfaTransitions.add(new DFATransition(currState,alphabet[i], newState));
			}
		}
	}
	public HashMap<String, ArrayList<Integer>> getNFATransitionsMap(String[] nfaTransitionsArray)
	{
		//converts transitions array to transitions map

		HashMap<String, ArrayList<Integer>> nfaTransitionsMap = new HashMap<>(); //("s1,a" --> s2)
		for(int i=0; i< nfaTransitionsArray.length; i++)
		{
			//get transition string
			String[] transition = nfaTransitionsArray[i].split(",");
			//split it (from, alphabet, to)
			String from = transition[0]; String alph = transition[1]; int to = Integer.parseInt(transition[2]);
			//update the toStates of the from state
			ArrayList<Integer> nfaToStates = nfaTransitionsMap.get(from+","+alph);
			if(nfaToStates == null)
			{
				nfaToStates = new ArrayList<>();nfaToStates.add(to);
			}
			else
				nfaToStates.add(to);
			nfaTransitionsMap.put(from+","+alph, nfaToStates);
		}
		return nfaTransitionsMap;
	}

	/**
	 * @return Returns a formatted string representation of the DFA. The string
	 *         representation follows the one in the task description
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String deadStateTransitions = getDeadStateTransitions();

		return dfaStates.stream().map(Object::toString).collect(Collectors.joining(";")) + "#" +
				Arrays. asList(alphabet).stream().map(Object::toString).collect(Collectors.joining(";")) + "#"+
				deadStateTransitions+
				dfaTransitions.stream().map(Object::toString).collect(Collectors.joining(";"))+ "#"+
				dfaInitialState + "#" +
				dfaFinalStates.stream().map(Object::toString).collect(Collectors.joining(";"));
	}
	public String getDeadStateTransitions()
	{
		String deadStateTransitions = "";
		TreeSet<Integer> deadStateArray = new TreeSet<>(); deadStateArray.add(-1);
		//if it has dead states, add them to the transitions
		if(dfaStates.contains(new DFAState(deadStateArray)))
		{
			for(int i=0; i<alphabet.length; i++)
				deadStateTransitions+="-1,"+alphabet[i]+ ",-1;";
		}
		return deadStateTransitions;
	}

	public static void main(String[] args) {
		NfaToDfa nfaToDfa= new NfaToDfa("0;1;2;3#a;b#0,a,0;0,b,0;0,b,1;1,a,2;1,e,2;2,b,3;3,a,3;3,b,3#0#3");
		System.out.println(nfaToDfa);


	}

}

class DFAState implements Comparable
{
	TreeSet<Integer> atomStates;
	public DFAState(TreeSet<Integer> atomStates)
	{
		this.atomStates = atomStates;
	}

	@Override
	public int compareTo(Object o) {
		DFAState toBeCompared = ((DFAState) o);
		int size = this.atomStates.size()<toBeCompared.atomStates.size()? this.atomStates.size(): toBeCompared.atomStates.size();
		ArrayList<Integer> toBeComparedArray = new ArrayList<>(toBeCompared.atomStates);
		ArrayList<Integer> thisArray = new ArrayList<>(this.atomStates);
		for(int i=0; i<size; i++)
		{
			if(thisArray.get(i)!=toBeComparedArray.get(i))
				return thisArray.get(i)-toBeComparedArray.get(i);
		}
		return this.atomStates.size()-toBeCompared.atomStates.size();
	}

	@Override
	public String toString() {
		return atomStates.stream().map(Object::toString).collect(Collectors.joining("/"));
	}

	@Override
	public boolean equals(Object obj) {
		return this.atomStates.equals(((DFAState)obj).atomStates);
	}

	@Override
	public int hashCode() {
		return Objects.hash(atomStates);
	}
}

class DFATransition implements Comparable
{
	DFAState from;
	String input;
	DFAState to;
	public DFATransition(	DFAState from, String input,  DFAState to)
	{
		this.from = from;
		this.input=input;
		this.to=to;
	}
	@Override
	public int compareTo(Object o) {
		if(from.equals(((DFATransition)o ).from))
		{
			if(input == ((DFATransition)o).input)
				return to.compareTo(((DFATransition)o).to);
			return input.compareTo(((DFATransition)o).input);
		}
		return from.compareTo(((DFATransition)o).from);

	}
	@Override
	public boolean equals(Object obj) {
		return this.from.equals(((DFATransition)obj).from) && this.to.equals(((DFATransition)obj).to) &&
				this.input.equals(((DFATransition)obj).input);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, input, to);
	}

	@Override
	public String toString() {
		return this.from + "," + this.input + ","+ this.to;
	}
}