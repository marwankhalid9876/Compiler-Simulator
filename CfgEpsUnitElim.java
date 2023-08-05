package csen1002.main.task4;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Write your info here
 * 
 * @name Marwan Khalid Farag
 * @id 46-14780
 * @labNumber 23
 */

public class CfgEpsUnitElim {

	/**
	 * Constructs a Context Free Grammar
	 * 
	 * @param cfg A formatted string representation of the CFG. The string
	 *            representation follows the one in the task description
	 */
	String variablesWithSemicolon;
	String terminalsWithSemicolon;
	HashMap<Character, Variable> variablesMap;
	public CfgEpsUnitElim(String cfg) {
		// TODO Auto-generated constructor stub
		String cfgArr[] = cfg.split("#");
		variablesWithSemicolon = cfgArr[0]; //A;B;C
		terminalsWithSemicolon = cfgArr[1];	//a;b;c
		String[] rules = cfgArr[2].split(";");
		variablesMap = new HashMap<>();
		for(int i=0; i<rules.length; i++)
		{
			char Variable = rules[i].charAt(0);
			String toSententials = rules[i].substring(2);
			variablesMap.put(Variable, new Variable(Variable));
			String[] sententials = toSententials.split(",");
			for(int j=0; j<sententials.length; j++)
			{

				variablesMap.get(Variable).addNewSentential(sententials[j]);
			}
		}

	}

	/**
	 * @return Returns a formatted string representation of the CFG. The string
	 *         representation follows the one in the task description
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String output =  variablesWithSemicolon + "#" +
				terminalsWithSemicolon + "#" +
				"S/" + variablesMap.get('S').to.stream().map(Object::toString).collect(Collectors.joining(",")) + ";";
		variablesMap.remove('S');

		String[] variables = variablesWithSemicolon.split(";");
		for(int i=1; i<variables.length; i++) {
			char variable = variables[i].charAt(0);
			output+=variable+"/";
			output+=variablesMap.get(variable).to.stream().map(Object::toString).collect(Collectors.joining(","));
			output+=";";
		}
		return output.substring(0,output.length()-1);//to avoid the last semicolon
	}

	/**
	 * Eliminates Epsilon Rules from the grammar
	 */
	public void eliminateEpsilonRules() {
		// TODO Auto-generated method stub
		while(epsilonExists())
		{
			for (char curr : variablesMap.keySet()) {

				Variable currVariable = variablesMap.get(curr);
				if (currVariable.goesToEpsilon()) {
					if(curr!='S')
						currVariable.removeEpsilon();
					for (char variableToUpdate : variablesMap.keySet()) { //variable to be removed
						variablesMap.get(variableToUpdate).extendVariable(curr);
					}
				}
			}
		}

	}
	/**
	 * Eliminates Unit Rules from the grammar
	 */
	public void eliminateUnitRules() {
		// TODO Auto-generated method stub
		HashSet<String> CFGvariablesSet = new HashSet<>();
		String[] CFGVariables = variablesWithSemicolon.split(";");
		for(int i=0; i<CFGVariables.length; i++)
			CFGvariablesSet.add(CFGVariables[i]);
		while(unitRulesExist(CFGvariablesSet)) {
			for (Character curr : variablesMap.keySet()) {
				Variable currVariable = variablesMap.get(curr);
				TreeSet<String> to = currVariable.to;

				ArrayList<Variable> toBeRemoved = new ArrayList<>();
				//store variables to be removed
				for (String sentential : to) {
					if (CFGvariablesSet.contains(sentential))//if the sentential is a unit variable
					{
						Variable replacedVariable = variablesMap.get(sentential.charAt(0));
						toBeRemoved.add(replacedVariable);
					}
				}
				for (Variable replacedVariable : toBeRemoved) {
					to.remove(replacedVariable.variable + "");//remove unit rule
					addRulesV2ToV1(currVariable, replacedVariable, CFGvariablesSet);
					currVariable.toVariablesRemoved.add(replacedVariable);
				}
			}
		}
	}
	public void addRulesV2ToV1(Variable curr, Variable replaced, HashSet<String> CFGVariableSet)
	{
		for(String sentential: replaced.to)
		{
			if(CFGVariableSet.contains(sentential))
			{
				if(!curr.toVariablesRemoved.contains(sentential))
				{
					curr.to.add(sentential);
				}
			}
			else {
				curr.to.add(sentential);
			}
		}
	}

	public boolean unitRulesExist(HashSet<String> CFGvariablesSet)
	{
		for (char curr : variablesMap.keySet()) {
			Variable v = variablesMap.get(curr);
			for(String sentential: v.to)
				if(CFGvariablesSet.contains(sentential))
					return true;
		}
		return false;
	}

	public boolean epsilonExists()
	{
		for (char curr : variablesMap.keySet()) {
			if(curr=='S') continue;
			if(variablesMap.get(curr).goesToEpsilon())
				return true;
		}
		return false;
	}




	public static void main(String[] args) {
		CfgEpsUnitElim cfgEpsUnitElim= new CfgEpsUnitElim("S;A;B;C#a;b;c;d;x#S/aAb,xB;A/Bc,C,c,d;B/CACA,e;C/A,b,e");
		cfgEpsUnitElim.eliminateUnitRules();
		System.out.println(cfgEpsUnitElim.toString());


	}

}

class Variable{

	char variable;
	TreeSet<String> to;
	HashSet<Variable> toVariablesRemoved;
	boolean epsilonRemoved;
	boolean containedEpsilon;//for tracing

	public Variable(char variable)
	{
		this.variable = variable;
		this.to = new TreeSet<>();
		epsilonRemoved = false;
		toVariablesRemoved = new HashSet<>();
	}
	public void addNewSentential(String toSentential)
	{
		to.add(toSentential);
	}

	@Override
	public String toString() {
		return variable + to.toString() + "contained epsilon: "+ containedEpsilon+ " epsilon removed: "+ epsilonRemoved;
	}

	public void removeEpsilon()
	{
		to.remove("e");
		epsilonRemoved=true;
	}
	public void extendVariable(char v)
	{
		ArrayList<String> toBeExtended = new ArrayList<>();
		for(String sentential: to)
		{
			if(sentential.contains(v+""))
				toBeExtended.add(sentential);
		}

		for(String sententialToBeExtended: toBeExtended)
		{
			ArrayList<Integer> variableIndices = getIndicesOf(v, sententialToBeExtended);
			addStringPermutations(sententialToBeExtended, variableIndices, v);
		}


	}
	public boolean goesToEpsilon()
	{
		return to.contains("e");
	}
	public ArrayList<Integer> getIndicesOf(char c, String s)
	{
		ArrayList<Integer> indices = new ArrayList<>();
		for(int i=0; i<s.length(); i++)
		{
			if(s.charAt(i)==c)
				indices.add(i);
		}
		return indices;
	}
	public static ArrayList<ArrayList<Boolean>> generateTruthTable(int n)
	{
		ArrayList<ArrayList<Boolean>> truthTable = new ArrayList<>();
		int rows = (int) Math.pow(2,n);
		for (int i=0; i<rows; i++) {
			ArrayList<Boolean> row = new ArrayList<>();
			for (int j=n-1; j>=0; j--) {
				if((i/(int) Math.pow(2, j))%2 == 0)
					row.add(false);
				else
					row.add(true);
			}
			truthTable.add(row);
		}

		return truthTable;
	}


	public void addStringPermutations(String s, ArrayList<Integer> indices, char v)
	{
		ArrayList<String> out = new ArrayList<>();
		ArrayList<ArrayList<Boolean>> truthTable = generateTruthTable(indices.size());
		for(int i=0; i<truthTable.size(); i++)
		{
			String toBeAdded = filterString(s,indices, truthTable.get(i));

			if(toBeAdded.equals(""))
			{
				if(!epsilonRemoved)
					to.add("e");
			}
			else
				to.add(toBeAdded);
		}

	}


	public static String filterString(String s, ArrayList<Integer> indices, ArrayList<Boolean> truthTableRow)
	{
		ArrayList<Integer> tmpIndices = (ArrayList<Integer>) indices.clone();
		String toBeAdded = s;
		for(int i=0; i<truthTableRow.size(); i++)
		{
			if(!truthTableRow.get(i))
			{
				toBeAdded = toBeAdded.substring(0,tmpIndices.get(i)) + toBeAdded.substring(tmpIndices.get(i)+1);
				decrementArrayList(tmpIndices, i+1);
			}
		}
		return toBeAdded;

	}
	public static void decrementArrayList(ArrayList<Integer> indices, int start)
	{
		for(int i=start; i<indices.size(); i++)
		{
			indices.set(i, indices.get(i)-1);
		}
	}
}
